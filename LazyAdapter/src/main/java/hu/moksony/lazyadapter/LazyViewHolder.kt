package hu.moksony.lazyadapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class LazyViewHolder<VDB : ViewDataBinding, T>(val binding: VDB) :
    RecyclerView.ViewHolder(binding.root) {
    var data: T? = null
}