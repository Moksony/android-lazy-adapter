package hu.moksony.lazyviewholderexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import hu.moksony.lazyviewholderexample.databinding.RomTextBodyItemBinding

class LazzyAdapter<T> : RecyclerView.Adapter<LazzyAdapter.LazzyViewHolder>() {

    var items: MutableList<T>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LazzyViewHolder {
        return LazzyViewHolder(
            RomTextBodyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LazzyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items?.size ?: -1
    }

    class LazzyViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}