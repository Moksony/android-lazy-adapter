package hu.moksony.lazyadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractAdapter<VH : RecyclerView.ViewHolder, IT> : RecyclerView.Adapter<VH>() {
    var items: MutableList<IT>? = null
    var originalItems: Iterable<IT>? = null
    private val idHashMap = HashMap<Any, Long>()
    protected var isStringId: Boolean = false

    init {
        setHasStableStringId(true)
    }

    private var nextId: Long = 0
        get() {
            field++
            return field
        }

    fun setHasStableStringId(boolean: Boolean) {
        this.isStringId = boolean
        this.setHasStableIds(boolean)
    }

    override fun getItemId(position: Int): Long {
        if (this.isStringId) {
            val id = getId(position) ?: return RecyclerView.NO_ID
            if (!this.idHashMap.containsKey(id)) {
                this.idHashMap[id] = nextId
            }
            return this.idHashMap[id]!!
        } else {
            return super.getItemId(position)
        }
    }

    protected open fun getId(position: Int): Any? {
        return null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return this.createViewHolder(LayoutInflater.from(parent.context), parent, viewType)
    }

    fun getItemAt(position: Int): IT? {
        return this.items?.getOrNull(position)
    }

    abstract fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VH

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    open fun submit(items: Iterable<IT>) {
        this.originalItems = items
        this.items = items.toMutableList()
        this.notifyDataSetChanged()
    }

    fun filter(predicate: (IT) -> Boolean) {
        val itms = this.originalItems
        if (itms != null) {
            val filtered = itms.filter(predicate)
            this.items = filtered.toMutableList()
            this.notifyDataSetChanged()
        }
    }
}