# Databricks notebook source
# Authenticate to Azure data lake using Azure key vault
spark.conf.set(
  "fs.azure.account.key.tescohackathon.dfs.core.windows.net",
  dbutils.secrets.get(scope = "AzureKeyVault", key = "StorageKey")
)

# COMMAND ----------

# Read data from data lake into Spark dataframe, 
inputdata = spark.read.csv("abfss://input@tescohackathon.dfs.core.windows.net/test.csv")

# COMMAND ----------

# Convert to delta lake parquet format for efficient SQL table storage
inputdata.write.format("delta").mode("overwrite").save("/delta/inputdata")

# COMMAND ----------

# MAGIC %sql
# MAGIC -- Write delta lake parquet to a table
# MAGIC DROP TABLE IF EXISTS inputdata;
# MAGIC 
# MAGIC CREATE TABLE inputdata USING DELTA LOCATION '/delta/inputdata/'

# COMMAND ----------

# Write Spark dataframe to data lake
inputdata.write.csv("abfss://output@tescohackathon.dfs.core.windows.net/data", "overwrite")

# COMMAND ----------

# Import Python libraries
import numpy as np
import pandas as pd
import seaborn as sb

# COMMAND ----------

# Enable Arrow-based columnar data transfers
spark.conf.set("spark.sql.execution.arrow.enabled", "true")

# COMMAND ----------

# Generate a Pandas DataFrame
pdf = pd.DataFrame(np.random.rand(100, 3))

# Create a Spark DataFrame from a Pandas DataFrame using Arrow
df = spark.createDataFrame(pdf, ['a', 'b', 'c'])

# Convert the Spark DataFrame back to a Pandas DataFrame using Arrow
result_pdf = df.select("*").toPandas()

# COMMAND ----------

pdf.head()

# COMMAND ----------

df.head()

# COMMAND ----------

result_pdf.head()

# COMMAND ----------

df.write.csv("abfss://output@tescohackathon.dfs.core.windows.net/pandasdata", "overwrite")

# COMMAND ----------

print(result_pdf['a'].value_counts)
print(result_pdf['a'].mean())

# COMMAND ----------

ax = sb.countplot(x='a', data=result_pdf)

# COMMAND ----------

ax
display()

# COMMAND ----------

pdf

# COMMAND ----------

df

# COMMAND ----------

pandasinputdata = inputdata.select("*").toPandas()

# COMMAND ----------

pandasinputdata

# COMMAND ----------

pandasinputdata['Col3']=1

# COMMAND ----------

pandasinputdata

# COMMAND ----------

# Coalesce partitioned output fragments into single file and save
df.coalesce(1) \
    .write.format("com.databricks.spark.csv") \
    .option("header", "true") \
    .mode("overwrite") \
    .save("abfss://output@tescohackathon.dfs.core.windows.net/tempdata")

# COMMAND ----------

# MAGIC %scala
# MAGIC // Find the name of the coalesced file
# MAGIC val mergedfile = dbutils.fs.ls("abfss://output@tescohackathon.dfs.core.windows.net/tempdata").map(_.name).filter(file=>file.endsWith(".csv"))(0)

# COMMAND ----------

# MAGIC %scala
# MAGIC // Move the file to the route output path and rename
# MAGIC dbutils.fs.mv("abfss://output@tescohackathon.dfs.core.windows.net/tempdata/" + mergedfile, "abfss://output@tescohackathon.dfs.core.windows.net/data.csv")
