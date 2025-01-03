package com.aking.reactivescaffold.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 数据源模拟
 * @author Ak
 * 2025/1/3  16:32
 */
class MocksDataSource {

    /**
     * 接口随机返回数据或报错
     */
    fun getText(timeMillis: Long) = runCatching {
        if (timeMillis % 2 == 0L) {
            throw Exception("模拟报错")
        }
        "Hello, Reactive Scaffold! $timeMillis"
    }

    /**
     * 模拟响应式数据源
     */
    fun getWorkspaces(): Flow<List<Workspace>> {
        return flow {
            val list = mutableListOf<Workspace>()
            while (true) {
                list.add(Workspace(list.size, "workspace${list.size + 1}"))
                emit(list)
                delay(5000)
            }
        }
    }

}