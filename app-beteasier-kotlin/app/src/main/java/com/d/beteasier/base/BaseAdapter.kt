package com.d.beteasier.base

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.d.beteasier.util.indexOfFirstOrNull

@Suppress("UNCHECKED_CAST")
abstract class BaseAdapter<ListItem> : RecyclerView.Adapter<BaseHolder<ListItem>>() {

    private var onViewClickListeners = mutableMapOf<Enum<*>, (viewId: Int, data: Any?) -> Unit>()
    private var onViewLongClickListeners = mutableMapOf<Enum<*>, (viewId: Int, data: Any?) -> Unit>()

    var items: MutableList<ListItem> = mutableListOf()
        set(value) {
            field = sort(value).toMutableList()
            notifyDataSetChanged()
        }

    /**
     * Use it for auto attach/detach RecyclerView ItemDecorations.
     * If you update after adapter is set to the RecyclerView - changes will not show
     */
    open val itemAnimator: RecyclerView.ItemAnimator = DefaultItemAnimator()
    open val itemDecorations = mutableListOf<RecyclerView.ItemDecoration>()
    open var baseRecyclerView: RecyclerView? = null

    abstract fun <VH : BaseHolder<ListItem>> getViewHolder(parent: ViewGroup, viewType: Enum<*>): VH

    open fun getViewTypeEnum(ordinal: Int): Enum<*> = BaseViewType.SINGLE

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ListItem> {
        val viewTypeEnum = getViewTypeEnum(viewType)
        val viewHolder = getViewHolder<BaseHolder<ListItem>>(parent, viewTypeEnum)
        val clickListenerPos = onViewClickListeners.keys.firstOrNull { it == BaseViewType.KEY_ALL_TYPES_ON_CLICK || it == viewTypeEnum }
        val longClickListenerPos = onViewLongClickListeners.keys.firstOrNull { it == BaseViewType.KEY_ALL_TYPES_ON_CLICK || it == viewTypeEnum }
        clickListenerPos?.let {
            viewHolder.onViewClickListeners = { int, any ->
                onViewClickListeners[clickListenerPos]?.invoke(int, any)
            }
        }
        longClickListenerPos?.let {
            viewHolder.onViewLongClickListeners = { int, any ->
                onViewLongClickListeners[clickListenerPos]?.invoke(int, any)
            }
        }

        try {
            return viewHolder
        } finally {
            onAfterCreateViewHolder(viewHolder)
        }
    }

    open fun onAfterCreateViewHolder(holder: BaseHolder<ListItem>) = Unit

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item is BaseItem<*>)
            item.viewType.ordinal
        else
            super.getItemViewType(position)
    }

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.baseRecyclerView = recyclerView
        recyclerView.itemAnimator = itemAnimator
        itemDecorations.forEach { recyclerView.addItemDecoration(it) }
    }

    @CallSuper
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.baseRecyclerView = null
        itemDecorations.forEach { recyclerView.removeItemDecoration(it) }
    }

    override fun onBindViewHolder(holder: BaseHolder<ListItem>, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    open fun sort(items: List<ListItem>): List<ListItem> = items

    fun <D> setOnViewClickListener(callback: (viewId: Int, data: D) -> Unit) {
        // This type is used for global click listeners
        onViewClickListeners[BaseViewType.KEY_ALL_TYPES_ON_CLICK] = { viewId, any ->
            callback(viewId, any as D)
        }
    }

    fun <D> addOnViewTypeViewClickListener(viewType: Enum<*>, callback: (viewId: Int, data: D) -> Unit) {
        onViewClickListeners[viewType] = { int, any ->
            callback.invoke(int, any as D)
        }
    }

    fun <D> setOnViewLongClickListener(callback: (viewId: Int, data: D) -> Unit) {
        // This type is used for global click listeners
        onViewLongClickListeners[BaseViewType.KEY_ALL_TYPES_ON_CLICK] = { viewId, any ->
            callback(viewId, any as D)
        }
    }

    fun <D> addOnViewTypeViewLongClickListener(viewType: Enum<*>, callback: (viewId: Int, data: D) -> Unit) {
        onViewLongClickListeners[viewType] = { int, any ->
            callback.invoke(int, any as D)
        }
    }

    fun clearViewClickListeners() = onViewClickListeners.clear()

    fun clearViewLongClickListeners() = onViewLongClickListeners.clear()

    fun <BH : BaseHolder<*>> findViewHolderByType(viewType: Enum<*>): BH? {
        val index = items.indexOfFirstOrNull { (it as? BaseItem<*>)?.viewType == viewType }
            ?: return null
        return baseRecyclerView?.findViewHolderForLayoutPosition(index) as? BH
    }

    fun <BH : BaseHolder<*>> findViewHolderByIndex(index: Int): BH? =
        baseRecyclerView?.findViewHolderForLayoutPosition(index) as? BH

    fun removeAt(position: Int, notify: Boolean = true) {
        items.removeAt(position)
        if (notify)
            notifyItemRemoved(position)
    }

    fun <ListItem> updateAt(
        position: Int,
        notify: Boolean = true,
        update: ListItem.() -> Unit
    ) {
        (items[position] as ListItem).update()
        if (notify)
            notifyItemChanged(position)
    }

    fun <T> notifyItemChanged(viewType: Enum<*>) =
        notifyItemChanged(items.map { it as? BaseItem<T> }.indexOfFirst { it?.viewType == viewType })

    fun <T> getItemModel(viewType: Enum<*>) =
        items.map { it as? BaseItem<T> }.firstOrNull { it?.viewType == viewType }?.item

    fun <T> getItemModel(position: Int) = (items[position] as? BaseItem<T>)?.item

    open class BaseItem<ListItem>(val viewType: Enum<*>, var item: ListItem)

    open class SingleTypeItem<ListItem>(item: ListItem) : BaseItem<ListItem>(BaseViewType.SINGLE, item)

    enum class BaseViewType {
        KEY_ALL_TYPES_ON_CLICK,
        SINGLE
    }
}
