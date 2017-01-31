/**
  * Created by bharath on 1/29/17.
  */
import org.apache.spark.{SparkConf, SparkContext}

object lab2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark").setMaster("local[2]")
    val sc = new SparkContext(sparkConf)
    val string=sc.textFile("input")
    println("Enter the word you want to search:")
    val word=readLine()
    val line=string.flatMap(x=>(x.split("\n"))).filter(line=>(line.contains(" "+word+" ")))
    val pattern=string.flatMap(x=>x.split(" ")).filter(line=>line.contains(word)).map(temp=>(temp,1)).reduceByKey(_+_).sortBy(_._2,false)
    println("The lines which contains the word you entered:")
    line.foreach(println)
    line.saveAsTextFile("lines")
    println(("Count of words which contains the word you entered:"))
    pattern.foreach(println)
    pattern.saveAsTextFile("word count")

  }
}

