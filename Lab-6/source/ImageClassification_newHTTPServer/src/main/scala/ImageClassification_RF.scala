
import java.net.InetSocketAddress

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.{DecisionTree, RandomForest}
import org.apache.spark.{SparkConf, SparkContext}

object ImageClassification_RF {
  var acc=""
  var cm=""
  def main(args: Array[String]) {
    val IMAGE_CATEGORIES = Array("monkey", "lizard", "hippo")
    System.setProperty("hadoop.home.dir", "C:\\Users\\Manikanta\\Documents\\UMKC Subjects\\PB\\hadoopforspark")
    // Turn off Info Logger for Consolexxx
    Logger.getLogger("org").setLevel(Level.OFF);
    Logger.getLogger("akka").setLevel(Level.OFF);
    val sparkConf = new SparkConf().setAppName("ImageClassification").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    val train = sc.textFile("data/train")
    val test = sc.textFile("data/test")
    val parsedData = train.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }
    val testData1 = test.map(line => {
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    })


    val trainingData = parsedData


    val numClasses = 3
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    val maxDepth = 5
    val maxBins = 32
    val featureSubsetStrategy = "auto"
    val numTrees = 5

    val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

    val classify1 = testData1.map { line =>
      val prediction = model.predict(line.features)
      (line.label, prediction)
    }

    val prediction1 = classify1.groupBy(_._1).map(f => {
      var fuzzy_Pred = Array(0, 0, 0)
      f._2.foreach(ff => {
        fuzzy_Pred(ff._2.toInt) += 1
      })
      var count = 0.0
      fuzzy_Pred.foreach(f => {
        count += f
      })
      var i = -1
      var maxIndex = 3
      val max = fuzzy_Pred.max
      val pp = fuzzy_Pred.map(f => {
        val p = f * 100 / count
        i = i + 1
        if(f == max)
          maxIndex=i
        (i, p)
      })
      (f._1, pp, maxIndex)
    })
    prediction1.foreach(f => {
      println("\n\n\n" + f._1 + " : " + f._2.mkString(";\n"))
    })
    val y = prediction1.map(f => {
      (f._3.toDouble,f._1 )
    })

    y.collect().foreach(println(_))

    val metrics = new MulticlassMetrics(y)

    println("Accuracy:" + metrics.accuracy)
    acc=metrics.accuracy.toString
    println("Confusion Matrix:")
    println(metrics.confusionMatrix)
    cm=metrics.confusionMatrix.toString()
    val server = HttpServer.create(new InetSocketAddress("192.168.1.146",8081), 0)
    server.createContext("/get_rf", new RootHandler())
    server.setExecutor(null)
    server.start()
    println("------ waiting for Request ------")
    class RootHandler extends HttpHandler {
      def handle(httpExchange: HttpExchange) {

        val response="Image Classification Random Forest\n\nAccuracy:"+acc+"\n\nConfusion matrix:\n"+cm
        httpExchange.sendResponseHeaders(200, response.length())
        val outStream = httpExchange.getResponseBody
        outStream.write(response.getBytes)
        outStream.close()

      }
    }
  }
}
