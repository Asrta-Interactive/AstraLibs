package com.astrainteractive.astralibs

/**
 * Allows you to estimate time of task
 */
object AstraEstimator {
    /**
     * Estimator for task
     * @return time estimated to execute [block]
     */
    fun estimateBlock(block: () -> Unit): Long {
        val start = System.currentTimeMillis()
        block.invoke()
        return System.currentTimeMillis() - start
    }
}