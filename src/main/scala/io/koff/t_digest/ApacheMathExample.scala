package io.koff.t_digest

import com.madhukaraphatak.sizeof.SizeEstimator
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

object ApacheMathExample {
  def main(args: Array[String]) {
    val stats = new DescriptiveStatistics
    val oneSecond = 1000
    val twoMinutes = 2 * 60 * 1000
    val tenMinutes = 10 * 60 * 1000
    val twoHours = 2 * 60 * 60 * 1000
    val mainValues = 10000000
    val badValues = 100000
    
    val mainData = Generator.generate(count = mainValues, from = oneSecond, to = twoMinutes)
    val badData = Generator.generate(count = badValues, from = tenMinutes, to = twoHours)
    val totalData = mainData ++ badData

    val startTime = System.currentTimeMillis()

    totalData.foreach(value => stats.addValue(value))

    val calcTime = System.currentTimeMillis() - startTime
    println("calcTime: " + calcTime)

    val median = stats.getPercentile(50)
    println("median: " + median)

    val mean = stats.getMean
    println("mean: " + mean)

    val objectSize = SizeEstimator.estimate(stats)
    println(s"size: $objectSize bytes")
  }
}
