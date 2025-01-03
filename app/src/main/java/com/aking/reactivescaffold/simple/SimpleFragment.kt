package com.aking.reactivescaffold.simple

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.aking.reactive.base.BaseFragment
import com.aking.reactive.base.Reactive
import com.aking.reactive.dsl.render
import com.aking.reactive.dsl.renderColumn
import com.aking.reactive.extended.collectWithLifecycle
import com.aking.reactive.widget.logI
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
//        viewModel.stateFlow.run {
//            mapNotNull { it.bgUrl }
//                .collectWithLifecycle(viewLifecycleOwner) {
//                    logI("bgUrl: $it")
//                }
//
//            mapNotNull { it.showMsg }
//                .collectWithLifecycle(viewLifecycleOwner) {
//                    showMsg(it)
//                }
//        }
    }

    override suspend fun render(state: SimpleState) {
        logI("render: ${Thread.currentThread().name} $state")
        binding.textView.text = state.name
        binding.recyclerView.render(state.workspaces)
    }

    /**
     * 显示 toast 消息并清除消息状态
     */
    private fun showMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        viewModel.reducer(SimpleAction.MsgShown)
    }
}