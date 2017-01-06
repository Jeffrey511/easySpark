package com.ml.one.TransformerAndEstimaterAndPipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.sql.Row
/**
 * Created by lichangyue on 2017/1/3.
 */
object MyTransformer {


  def main(args: Array[String]) {


    // Prepare training data from a list of (label, features) tuples.
    //准备带标签和特征的数据
    val training = sqlContext.createDataFrame(Seq(
      (1.0, Vectors.dense(0.0, 1.1, 0.1)),
      (0.0, Vectors.dense(2.0, 1.0, -1.0)),
      (0.0, Vectors.dense(2.0, 1.3, 1.0)),
      (1.0, Vectors.dense(0.0, 1.2, -0.5))
    )).toDF("label", "features")

    // Create a LogisticRegression instance.  This instance is an Estimator.
    //创建一个逻辑回归事例，这个实例是评估器
    val lr = new LogisticRegression()
    // Print out the parameters, documentation, and any default values.
    //输出参数等默认值
    println("LogisticRegression parameters:\n" + lr.explainParams() + "\n")

    // We may set parameters using setter methods.
    //使用setter方法设置参数
    lr.setMaxIter(10)
      .setRegParam(0.01)

    // Learn a LogisticRegression model.  This uses the parameters stored in lr.
    //使用存储在lr中的参数来，学习一个模型，
    val model1 = lr.fit(training)
    // Since model1 is a Model (i.e., a Transformer produced by an Estimator),
    // we can view the parameters it used during fit().
    // This prints the parameter (name: value) pairs, where names are unique IDs for this
    // LogisticRegression instance.
    //由于model1是一个模型，（也就是，一个评估器产生一个转换器），
    // 我们可以看lr在fit()上使用的参数。
    //输出这些参数对，参数里的names是逻辑回归实例的唯一id
    println("Model 1 was fit using parameters: " + model1.parent.extractParamMap)

    // We may alternatively specify parameters using a ParamMap,
    // which supports several methods for specifying parameters.
    //我们可以使用paramMap选择指定的参数，并且提供了很多方法来设置参数
    val paramMap = ParamMap(lr.maxIter -> 20)
      .put(lr.maxIter, 30) // Specify 1 Param.  This overwrites the original maxIter. 指定一个参数。
      .put(lr.regParam -> 0.1, lr.threshold -> 0.55) // Specify multiple Params. 指定多个参数

    // One can also combine ParamMaps.
    val paramMap2 = ParamMap(lr.probabilityCol -> "myProbability") // Change output column name 改变输出列的名称
    val paramMapCombined = paramMap ++ paramMap2

    // Now learn a new model using the paramMapCombined parameters.
    // paramMapCombined overrides all parameters set earlier via lr.set* methods.
    //使用新的参数学习模型。
    val model2 = lr.fit(training, paramMapCombined)
    println("Model 2 was fit using parameters: " + model2.parent.extractParamMap)

    // Prepare test data.
    //准备测试数据
    val test = sqlContext.createDataFrame(Seq(
      (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
      (0.0, Vectors.dense(3.0, 2.0, -0.1)),
      (1.0, Vectors.dense(0.0, 2.2, -1.5))
    )).toDF("label", "features")

    // Make predictions on test data using the Transformer.transform() method.
    // LogisticRegression.transform will only use the 'features' column.
    // Note that model2.transform() outputs a 'myProbability' column instead of the usual
    // 'probability' column since we renamed the lr.probabilityCol parameter previously.
    //使用转换器的transform()方法在测试数据上作出预测.
    // 逻辑回归的transform方法只使用“特征”列.
    // 注意model2.transform()方法输出的是myProbability列而不是probability列，因为在上面重命名了lr.probabilityCol 参数。
    model2.transform(test)
      .select("features", "label", "myProbability", "prediction")
      .collect()
      .foreach { case Row(features: Vector, label: Double, prob: Vector, prediction: Double) =>
      println(s"($features, $label) -> prob=$prob, prediction=$prediction")
    }


  }

}


