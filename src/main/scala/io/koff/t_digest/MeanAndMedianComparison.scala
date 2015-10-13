package io.koff.t_digest

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

/**
 * Compare mean and median values
 */
object MeanAndMedianComparison {
  def main(args: Array[String]) {
    val normalStats = new DescriptiveStatistics
    val normalData = Seq(3000, 2000, 3000, 5000, 3000, 4000, 4500, 3200, 2700, 3380)
    normalData.foreach(value => normalStats.addValue(value))

    val mean = normalStats.getMean
    println("normal mean: " + mean)

    val median = normalStats.getPercentile(50)
    println("normal median: " + median)

    val badStats = new DescriptiveStatistics
    val badData = Seq(3000, 2000, 3000, 5000, 3000, 4000, 4500, 3200, 2700, 3600000)
    badData.foreach(value => badStats.addValue(value))

    val badMean = badStats.getMean
    println("badMean: " + badMean)

    val badMedian = badStats.getPercentile(50)
    println("badMedian: " + badMedian)
  }
}
