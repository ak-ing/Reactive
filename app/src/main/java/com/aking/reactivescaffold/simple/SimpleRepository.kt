package com.aking.reactivescaffold.simple

import com.aking.reactive.base.BaseRepository
import com.aking.reactivescaffold.data.MocksDataSource

/**
 * @author Ak
 * 2025/1/3  16:29
 */
class SimpleRepository(private val dataSource: MocksDataSource = MocksDataSource() /* inject */) : BaseRepository() {

    /**
     * 从网络获取最新数据并返回结果，
     * 在IO优化的线程池中执行，函数是主线程安全的。
     */
    suspend fun getText(timeMillis: Long) = request {
        dataSource.getText(timeMillis)
    }

    /**
     * 从网络获取最新数据并返回结果，
     * 在IO优化的线程池中执行，函数是主线程安全的。
     */
    suspend fun getWorkspaces() = request {
        dataSource.getWorkspaces()
    }

}