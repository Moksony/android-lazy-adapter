package hu.moksony.lazyadapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class LazyAdapterViewHolder<VDB: ViewDataBinding>(val binding: VDB) : RecyclerView.ViewHolder(binding.root) {
}