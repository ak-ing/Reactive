package com.aking.reactivescaffold.simple

import com.aking.reactive.memoization.memoize

/**
 * 函数记忆化示例
 * @author Ak
 * 2025/1/7  17:10
 */
fun main() {
    val memoizedSumFactors = { x: Int -> sumOfFactors(x) }.memoize()
    memoizedSumFactors(3) // this would be the only one calculated
    memoizedSumFactors(3)
    memoizedSumFactors(3)
    memoizedSumFactors(3)
    memoizedSumFactors(3)
}

fun sumOfFactors(number: Int) = factorsOf(number).sum()

fun factorsOf(number: Int) = (1 to number).toList().filter { isFactor(number, it) }

fun isFactor(number: Int, potential: Int) = number % potential == 0