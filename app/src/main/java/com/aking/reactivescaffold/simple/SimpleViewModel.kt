package com.aking.reactivescaffold.simple

import androidx.lifecycle.viewModelScope
import com.aking.reactive.base.BaseViewModel
import com.aking.reactive.base.Intent
import com.aking.reactive.base.Reducer
import com.aking.reactive.widget.logI
import com.aking.reactivescaffold.data.Workspace
import kotlinx.coroutines.launch

data class SimpleState(
    val name: String = "Simple",
    val workspaces: List<Workspace> = emptyList(),
    val bgUrl: String? = "https://picsum.photos/200/300",
    val showMsg: String? = null
)

sealed class SimpleAction : Intent {
    data class FetchText(val timeMillis: Long) : SimpleAction()
    data object MsgShown : SimpleAction()
}

/**
 * @author Ak
 * 2025/1/3  16:26
 */
class SimpleViewModel : BaseViewModel<SimpleState>(SimpleState()), Reducer<SimpleAction> {

    // inject repository
    private val repository = SimpleRepository()

    override fun onInitialize() {
        viewModelScope.launch {
            repository.getWorkspaces().collect {
                logI("$it")
                update { copy(workspaces = it) }
            }
        }
    }

    override fun reducer(intent: SimpleAction) {
        when (intent) {
            is SimpleAction.FetchText -> fetchText(intent.timeMillis)
            is SimpleAction.MsgShown -> msgShown()
        }
    }

    private fun fetchText(timeMillis: Long) = viewModelScope.launch {
        repository.getText(timeMillis).onSuccess {
            update { copy(name = it) }
        }.onFailure {
            update { copy(showMsg = it.message) }
        }
    }

    /**
     * Clears the error message to dismiss the current one.
     */
    private fun msgShown() {
        update { copy(showMsg = null) }
    }
}