import sys

from mrjob.job import MRJob
from mrjob.step import MRStep
from mrjob.protocol import RawValueProtocol

Nlayer = 5

class Node:
    def __init__(self):
        self.characterID = ''
        self.connections = []
        self.distance = 9999
        self.color = 'WHITE'

    #Format is ID|EDGES|DISTANCE|COLOR
    def fromLine(self, line):
        fields = line.split('|')
        if (len(fields) == 4):
            self.characterID = fields[0]
            self.connections = fields[1].split(',')
            if len(self.connections) == 1 and self.connections[0] == '':
                self.connections = []
            self.distance = int(fields[2])
            self.color = fields[3].rstrip('\t')

    def getLine(self):
        connections = ','.join(self.connections)
        return '|'.join( (self.characterID, connections, str(self.distance), self.color) )

class MRBFSMultiStep(MRJob):

    INPUT_PROTOCOL = RawValueProtocol
    OUTPUT_PROTOCOL = RawValueProtocol

    # def configure_args(self):
    #     super(MRBFSIteration, self).configure_args()
    #     self.add_passthru_arg(
    #         '--target', help="ID of character we are searching for")

    def single_mapper(self, _, line):
        node = Node()
        node.fromLine(line)

        # print('d', 'line', line)
        # print('d', 'getline', node.getLine())
        # sys.stderr.write("MAPPER INPUT: ({0}, {1})\n".format(_, line))

        if (node.color == 'WHITE'): 
            counterName = ("Number of remaining nodes")
            self.increment_counter('remained', counterName, 1)
        #If this node needs to be expanded...
        if (node.color == 'GRAY'):
            # print('d', 'gray', node.characterID)
            for connection in node.connections:
                vnode = Node()
                vnode.characterID = connection
                vnode.distance = int(node.distance) + 1
                vnode.color = 'GRAY'
                # if (self.options.target == connection):
                #     counterName = ("Target ID " + connection +
                #         " was hit with distance " + str(vnode.distance))
                #     self.increment_counter('Degrees of Separation',
                #         counterName, 1)
                yield connection, vnode.getLine()

        #We've processed this node, so color it black
            node.color = 'BLACK'        

        #Emit the input node so we don't lose it.
        yield node.characterID, node.getLine()

    def single_reducer(self, key, values):
        edges = []
        distance = 9999
        color = 'WHITE'
        
        # sys.stderr.write("REDUCER INPUT: ({0}, {1})\n".format(key, values))
        
        for value in values:
            # print('d', key, value)
            node = Node()
            node.fromLine(value)

            if (len(node.connections) > 0):
                #edges = node.connections
                edges.extend(node.connections)

            if (node.distance < distance):
                distance = node.distance

            if ( node.color == 'BLACK' ):
                color = 'BLACK'

            if ( node.color == 'GRAY' and color == 'WHITE' ):
                color = 'GRAY'

        node = Node()
        node.characterID = key
        node.distance = distance
        node.color = color
        #There's a bug in mrjob for Windows where sorting fails
        #with too much data. As a workaround, we're limiting the
        #number of edges to 500 here. You'd remove the [:500] if you
        #were running this for real on a Linux cluster.
        node.connections = edges#[:500]

        yield key, node.getLine()

    def steps(self):
        ret = []
        for i in range(Nlayer):
            ret.append(MRStep(mapper=self.single_mapper,
                                     reducer=self.single_reducer))
        
        return ret


if __name__ == '__main__':
    MRBFSMultiStep.run()
