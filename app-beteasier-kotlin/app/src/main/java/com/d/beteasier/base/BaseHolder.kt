package com.d.beteasier.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseHolder<ListItem>(itemView: View) : RecyclerView.ViewHolder(itemView),
    LayoutContainer {

    // Do not use this from subclasses, use onViewClick to deliver click actions
    internal var onViewClickListeners: (viewId: Int, data: Any?) -> Unit = { _, _ -> }
    internal var onViewLongClickListeners: (viewId: Int, data: Any?) -> Unit = { _, _ -> }

    override val containerView: View = itemView

    val parentRecyclerView: RecyclerView?
        get() {
            val parent = itemView.parent
            return if (parent is RecyclerView)
                parent
            else
                null
        }

    val context: Context
        get() = itemView.context

    constructor(parent: ViewGroup, @LayoutRes layoutRes: Int) : this(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    )

    abstract fun onBind(listItem: ListItem)

    fun onViewClick(@IdRes viewId: Int, isLongClick: Boolean = false) =
        onViewClick(viewId, Any(), isLongClick)

    fun <D : Any> onViewClick(@IdRes viewId: Int, data: D?, isLongClick: Boolean = false) {
        if (!isLongClick)
            onViewClickListeners.invoke(viewId, data)
        else
            onViewLongClickListeners.invoke(viewId, data)
    }

    fun <V : View> onViewClick(view: V, isLongClick: Boolean = false) {
        onViewClick(view, Any(), isLongClick)
    }

    fun <V : View, D : Any> onViewClick(view: V, data: D?, isLongClick: Boolean = false) {
        if (!isLongClick)
            onViewClickListeners.invoke(view.id, data)
        else
            onViewLongClickListeners.invoke(view.id, data)
    }

    fun <V : View> onViewClickWithPositionData(view: V, isLongClick: Boolean = false) {
        if (adapterPosition == -1) return
        onViewClick(view, adapterPosition, isLongClick)
    }

    fun onViewClickWithPositionData(viewId: Int, isLongClick: Boolean = false) {
        if (adapterPosition == -1) return
        onViewClick(viewId, adapterPosition, isLongClick)
    }
}