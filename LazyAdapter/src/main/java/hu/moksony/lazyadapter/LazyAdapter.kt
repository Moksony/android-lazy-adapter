package hu.moksony.lazyadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class LazyAdapter<IT : Any, VDB : ViewDataBinding>(
    val config: LazyAdapterBuilder<VDB, IT>
) :
    AbstractRecyclerView<LazyAdapterViewHolder<VDB>, IT>() {
    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LazyAdapterViewHolder<VDB> {
        val layoutId = config.layoutId
        val binding: ViewDataBinding
        if (layoutId == null) {

            val method = config._vdb::java.get().getDeclaredMethod(
                "inflate",
                android.view.LayoutInflater::class.java,
                android.view.ViewGroup::class.java,
                Boolean::class.java
            )
            binding = method.invoke(null, inflater, parent, false) as VDB
        } else {
            binding = DataBindingUtil.inflate(inflater, layoutId, parent, false)
        }


        val holderInflater = config.onCreateViewHolder
        val adapter: LazyAdapterViewHolder<VDB>
        if (holderInflater != null) {
            adapter = holderInflater.invoke(this)
        } else {
            adapter = LazyAdapterViewHolder<VDB>(binding as VDB)
        }
        config.onCreated?.invoke(binding as VDB)
        return adapter
    }

    override fun onBindViewHolder(holder: LazyAdapterViewHolder<VDB>, position: Int) {
        val item = getItemAt(position)
        config.onBind?.invoke(holder.binding, item)
    }
}