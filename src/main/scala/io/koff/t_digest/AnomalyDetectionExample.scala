package io.koff.t_digest

/**
 * Example of anomaly detection
 */
object AnomalyDetectionExample {
  import com.tdunning.math.stats.TDigest
  def main(args: Array[String]) {
    //define constants for experiments
    val oneSecond = 1000
    val twoMinutes = 2 * 60 * 1000
    val tenMinutes = 10 * 60 * 1000
    val twoHours = 2 * 60 * 60 * 1000

    val mainValues = 10000000
    val badValues = 10000

    //generate 10.000.000 pseudo-random values for normal user session durations
    val mainData = Generator.generate(count = mainValues, from = oneSecond, to = twoMinutes)

    //generate 100.000(1%) pseudo-random values for invalid user session durations
    val badData = Generator.generate(count = badValues, from = tenMinutes, to = twoHours)

    val totalData = mainData ++ badData

    val totalDigest = TDigest.createArrayDigest(100)

    totalData.foreach(value => totalDigest.add(value))
    //this threshold means that we expect ~0.1% of data is anomalies
    val threshold = totalDigest.quantile(0.999d).toInt

    println(s"threshold: $threshold msec")
  }
}
