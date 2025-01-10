package com.aking.reactivescaffold.simple

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.aking.reactive.base.BaseFragment
import com.aking.reactive.base.Distinct
import com.aking.reactive.base.Reactive
import com.aking.reactive.dsl.render
import com.aking.reactive.dsl.renderColumn
import com.aking.reactive.extended.collectWithLifecycle
import com.aking.reactive.widget.logI
import com.aking.reactivescaffold.MainViewModel
import com.aking.reactivescaffold.R
import com.aking.reactivescaffold.data.Workspace
import com.aking.reactivescaffold.databinding.FragmentSimpleBinding
import com.aking.reactivescaffold.databinding.ItemWorkspaceBinding
import kotlinx.coroutines.flow.mapNotNull

/**
 * @author Ak
 * 2025/1/3  16:24
 */
class SimpleFragment : BaseFragment<FragmentSimpleBinding>(R.layout.fragment_simple), Reactive<SimpleState> {
    private val viewModel: SimpleViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun FragmentSimpleBinding.initView() {
        recyclerView.renderColumn<Workspace, ItemWorkspaceBinding>(Workspace.diffCallback) {
            content { parent, viewType ->
                ItemWorkspaceBinding.inflate(layoutInflater, parent, false)
            }
            render { item, position, holder ->
                tvTitle.text = item.name
            }
        }
        button.setOnClickListener {
            viewModel.reducer(SimpleAction.FetchText(System.currentTimeMillis()))
        }
    }

    override fun initData() {
        viewModel.initialize(this)
        mainViewModel.stateFlow // 分流处理
            .mapNotNull { it.someList }
            .collectWithLifecycle(this) {
                // do something
            }
    }


    override suspend fun render(state: SimpleState, distinct: Distinct<SimpleState>) {
        logI("render: $state")
        distinct(SimpleState::name) {
            binding.textView.text = it
        }
        distinct({ it.workspaces }) {
            binding.recyclerView.render(it)
        }
        // 消息状态不为空时显示消息，显示后置空清楚消息状态
        state.showMsg?.let { showMsg(it) }
    }

    /**
     * 显示 toast 消息并清除消息状态
     */
    private fun showMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        viewModel.reducer(SimpleAction.MsgShown)
    }
}