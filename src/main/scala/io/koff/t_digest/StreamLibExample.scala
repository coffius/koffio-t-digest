package io.koff.t_digest

import com.clearspring.analytics.stream.quantile.TDigest
import com.madhukaraphatak.sizeof.SizeEstimator

/**
 * Example for stream-lib
 */
object StreamLibExample {
  def main(args: Array[String]) {
    val oneSecond = 1000
    val twoMinutes = 2 * 60 * 1000
    val tenMinutes = 10 * 60 * 1000
    val twoHours = 2 * 60 * 60 * 1000
    val mainValues = 10000000
    val badValues = 100000

    val mainData = Generator.generate(count = mainValues, from = oneSecond, to = twoMinutes)
    val badData = Generator.generate(count = badValues, from = tenMinutes, to = twoHours)
    val totalData = mainData ++ badData

    val stats = new TDigest(100)
    val startTime = System.currentTimeMillis()

    totalData.foreach(value => stats.add(value))

    val calcTime = System.currentTimeMillis() - startTime
    println("calcTime: " + calcTime)

    val median = stats.quantile(0.5)
    println("median: " + median)

    println("byte size:    " + stats.byteSize())

    val objectSize = SizeEstimator.estimate(stats)
    println(s"size: $objectSize bytes")
  }
}
