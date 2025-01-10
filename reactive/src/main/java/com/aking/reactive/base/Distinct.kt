package com.aking.reactive.base

/**
 * 重复值过滤器
 * @author Ak
 * 2025/1/10  9:43
 */
class Distinct<S>(initState: S) {
    var each: S = initState
        internal set
    var cache: S? = null
        internal set

    /**
     * 根据当前状态与缓存状态的比较结果来决定是否渲染新值。
     * 如果值发生变化，则调用`render`函数渲染当前值。
     *
     * @param key 从状态中提取用于比较的键值的函数。
     * @param render 当状态与缓存状态不同步时，用于渲染新值的函数。
     */
    inline operator fun <T> invoke(key: (S) -> T, render: (value: T) -> Unit) {
        val value = key(each)
        val cache = cache
        cache ?: return render(value)

        val cacheValue = key(cache)
        if (value === cacheValue) return
        if (value == cacheValue) return
        render(value)
    }

    internal inline fun onEach(each: S, block: () -> Unit) {
        this.each = each
        block()
        cache = each
    }
}