import sys

from mrjob.job import MRJob
from mrjob.protocol import RawValueProtocol
from mrjob.step import SparkStep

# TODO: Adjust the iteration number;
# If too big, every node will be black;
# If too small, a great deal of nodes will be gray;
# 29 nodes are gray for big0.txt after 3 iterations
Nlayer=5
#Format is ID|EDGES|DISTANCE|COLOR
def fromLine(line):
    fields = line.split('|')
    if (len(fields) == 4):
        characterID = fields[0]

        connections = []
        connections = fields[1].split(',')
        if len(connections) == 1 and connections[0] == '':
            connections = []

        distance = int(fields[2])
        color = fields[3].rstrip('\t')

    return (characterID, (connections, distance, color))


class MRSparkBFS(MRJob):
    INPUT_PROTOCOL = RawValueProtocol
    OUTPUT_PROTOCOL = RawValueProtocol
    
    def spark(self, input_path, output_path):
        # Spark may not be available where script is launched
        from pyspark import SparkContext

        sc = SparkContext(appName='mrjob Spark BFS script')

        lines = sc.textFile(input_path)
        lines.collect()
        lines = lines.map(fromLine)

        for iteration in range(Nlayer):
            # print("Running BFS iteration: %s", str(iteration+1))
            mapped = lines.flatMap(self.f_map)
            lines = mapped.reduceByKey(self.f_reduce)
        
        lines.saveAsTextFile(output_path)
        sc.stop()

    def f_map(self, node):
        characterID = node[0]
        data = node[1]
        connections = data[0]
        distance = data[1]
        color = data[2]

        results = []

        if (color == 'WHITE'): 
            counterName = ("Number of remaining nodes")
            self.increment_counter('remained', counterName, 1)
        #If this node needs to be expanded...
        if (color == 'GRAY'):
            for connection in connections:
                newCharacterID = connection
                newDistance = int(distance) + 1
                newColor = 'GRAY'

                newEntry = (newCharacterID, ([],newDistance,newColor))
                results.append(newEntry)

            #We've processed this node, so color it black
            color = 'BLACK'        

        #Emit the input node so we don't lose it.
        results.append((characterID, (connections, distance, color)))
        return results

    def f_reduce(self, data1, data2):
        edges1 = data1[0]
        edges2 = data2[0]
        distance1 = data1[1]
        distance2 = data2[1]
        color1 = data1[2]
        color2 = data2[2]

        distance = 9999
        color = color1
        edges = []

        # See if one is the original node with its connections.
        # If so preserve them.
        if (len(edges1) > 0):
            edges.extend(edges1)
        if (len(edges2) > 0):
            edges.extend(edges2)

        # Preserve minimum distance
        if (distance1 < distance):
            distance = distance1

        if (distance2 < distance):
            distance = distance2

        # Preserve darkest color
        if (color1 == 'WHITE' and (color2 == 'GRAY' or color2 == 'BLACK')):
            color = color2

        if (color1 == 'GRAY' and color2 == 'BLACK'):
            color = color2

        if (color2 == 'WHITE' and (color1 == 'GRAY' or color1 == 'BLACK')):
            color = color1

        if (color2 == 'GRAY' and color1 == 'BLACK'):
            color = color1

        return (edges,distance,color)


if __name__ == '__main__':
    MRSparkBFS.run()
