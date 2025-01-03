package com.aking.reactivescaffold

import com.aking.reactive.base.BaseViewModel

data class MainState(
    val someList: List<String>? = null
)

/**
 * @author Created by Ak on 2025-01-03 19:51.
 */
class MainViewModel : BaseViewModel<MainState>(MainState()) {
    override fun onInitialize() {
        // do something
    }
}