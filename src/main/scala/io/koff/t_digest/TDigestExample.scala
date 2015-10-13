package io.koff.t_digest

object TDigestExample {
  import com.madhukaraphatak.sizeof.SizeEstimator
  import com.tdunning.math.stats.TDigest

  def main(args: Array[String]) {
    //define constants for experiments
    val oneSecond = 1000
    val twoMinutes = 2 * 60 * 1000
    val tenMinutes = 10 * 60 * 1000
    val twoHours = 2 * 60 * 60 * 1000

    val mainValues = 10000000
    val badValues = 100000

    //generate 10.000.000 pseudo-random values for normal user session durations
    val mainData = Generator.generate(count = mainValues, from = oneSecond, to = twoMinutes)

    //generate 100.000(1%) pseudo-random values for invalid user session durations
    val badData = Generator.generate(count = badValues, from = tenMinutes, to = twoHours)

    //generate one united collection. For further details see below
    val totalData = {
      Generator.generate(count = mainValues, from = oneSecond, to = twoMinutes) ++
        Generator.generate(count = badValues, from = tenMinutes, to = twoHours)
    }

    // Experiment #1
    // All data in one digest object
    // all values from one collection(totalData) are added to one digest object

    // recommend default value of compression = 100
    val totalDigest = TDigest.createArrayDigest(100)

    //the timing of the median calculation
    val startTime = System.currentTimeMillis()
    totalData.foreach(value => totalDigest.add(value))
    val median = totalDigest.quantile(0.5d)               //the median is a 0.5 quantile
    val calcTime = System.currentTimeMillis() - startTime
    println("calcTime: " + calcTime)

    // Experiment #2
    // Separate objects for normal(mainData) and bad data(badData) to check accuracy of t-digest merging
    val mainDigest = TDigest.createArrayDigest(100)
    mainData.foreach(value => mainDigest.add(value))

    val badDigest = TDigest.createArrayDigest(100)
    badData.foreach(value => badDigest.add(value))

    //to check accuracy of merging several digests
    val mergedDigest = TDigest.createArrayDigest(100)
    mergedDigest.add(mainDigest)
    mergedDigest.add(badDigest)

    val mergedMedian = mergedDigest.quantile(0.5d)
    println("median:       " + median)
    println("mergedMedian: " + mergedMedian)

    //how many bytes is needed to serialize t-digest object
    println("byte size:    " + totalDigest.byteSize())

    //how much memory is needed for totalDigest object
    val sizeInMem = SizeEstimator.estimate(totalDigest)
    println(s"size in mem:  $sizeInMem bytes")
  }
}
