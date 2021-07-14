from pyspark import SparkContext
from pyspark.sql import SQLContext
from pyspark.sql.types import IntegerType
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.regression import LinearRegression
from pyspark.ml.evaluation import RegressionEvaluator
from glob import glob

sc= SparkContext()
sqlContext = SQLContext(sc)
files = glob("./flight_data/*.csv.bz2")

print("Start loading dataset...")
weather_df = sqlContext.read.format('csv').options(header='true',compression='bzip2').load(files)
print("Finish loading dataset...")

cols = weather_df.columns
for col in cols:
    weather_df = weather_df.withColumn(col, weather_df[col].cast(IntegerType()))    
weather_df = weather_df.fillna(0)

vectorAssembler = VectorAssembler(inputCols = ['WeatherDelay'], outputCol = 'features')
vweather_df = vectorAssembler.transform(weather_df)
vweather_df = vweather_df.select(['features','Year'])

splits = vweather_df.randomSplit([0.7, 0.3])
train_df = splits[0]
test_df = splits[1]

# Linear Regression
# Train
print("Start Training...")
lr = LinearRegression(featuresCol = 'features', labelCol='Year', maxIter=10, regParam=0.3, elasticNetParam=0.8)
lr_model = lr.fit(train_df)
print("Coefficients: " + str(lr_model.coefficients))
print("Intercept: " + str(lr_model.intercept))
print("Finish Training...\nPrint the result.")
trainingSummary = lr_model.summary
print("RMSE: %f" % trainingSummary.rootMeanSquaredError)
print("r2: %f" % trainingSummary.r2)

# Test
print("Start Testing...")
lr_predictions = lr_model.transform(test_df)
lr_evaluator = RegressionEvaluator(predictionCol="prediction", \
                 labelCol="UniqueCarrier",metricName="r2")
                 
print("R Squared (R2) on test data = %g" % lr_evaluator.evaluate(lr_predictions))

test_result = lr_model.evaluate(test_df)
print("Root Mean Squared Error (RMSE) on test data = %g" % test_result.rootMeanSquaredError)
print("Finish Testing...")