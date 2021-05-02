package hu.moksony.lazyadapter

import androidx.recyclerview.widget.DiffUtil

class LazyDiffUtil<T>(private val conf: LazyAdapterConfig<T>) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        val item1 = conf.findType(oldItem).id?.invoke(oldItem as Any)
        val item2 = conf.findType(newItem).id?.invoke(newItem as Any)
        return item1 == item2
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }
}