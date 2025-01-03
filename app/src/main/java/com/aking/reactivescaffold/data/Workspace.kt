package com.aking.reactivescaffold.data

import androidx.recyclerview.widget.DiffUtil

/**
 * @author Ak
 * 2025/1/3  17:17
 */
data class Workspace(
    val id: Int,
    val name: String
) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Workspace>() {
            override fun areItemsTheSame(oldItem: Workspace, newItem: Workspace) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Workspace, newItem: Workspace) = oldItem == newItem
        }
    }

}