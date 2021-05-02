package hu.moksony.lazyadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import java.lang.Exception

class LazyAdapter<T>(
    val config: LazyAdapterConfig<T>
) :
    ListAdapter<T, LazyViewHolder<ViewDataBinding, T>>(LazyDiffUtil(config)) {


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: throw Exception("item can not be null")
        val index = config.typeList.indexOfFirst {
            it.itemClass == item::class
        }

        //fix ex: Uri
        if (index == -1) {
            return config.typeList.indexOfFirst {
                item::class.isInstance(item)
            }
        }
        return index
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LazyViewHolder<ViewDataBinding, T> {
        val inflater = LayoutInflater.from(parent.context)
        val typeHelper = config.typeList[viewType]
        val layoutId = typeHelper.layoutId
        val binding: ViewDataBinding
        if (layoutId == null) {
            val method = typeHelper.viewDataBindingClass.java.getDeclaredMethod(
                "inflate",
                android.view.LayoutInflater::class.java,
                android.view.ViewGroup::class.java,
                Boolean::class.java
            )
            binding = method.invoke(null, inflater, parent, false) as ViewDataBinding
        } else {
            binding = DataBindingUtil.inflate(inflater, layoutId, parent, false)
        }

        typeHelper.onCreated?.invoke(binding)

        val customViewHolderFn = typeHelper.createViewHolder
        val viewHolder: LazyViewHolder<ViewDataBinding, T>
        if (customViewHolderFn != null) {
            viewHolder = customViewHolderFn.invoke(binding) as LazyViewHolder<ViewDataBinding, T>
        } else {
            viewHolder = LazyViewHolder(binding)
        }
        typeHelper.onViewHolderCreated?.invoke(viewHolder as LazyViewHolder<ViewDataBinding, Any>)
        return viewHolder
    }

    @Deprecated("Deprecated call",
        ReplaceWith("submitList(list)", "hu.moksony.lazyadapter.LazyAdapter")
    )
    fun submit(list: List<T>) {
        this.submitList(list)
    }

    override fun onBindViewHolder(holder: LazyViewHolder<ViewDataBinding, T>, position: Int) {
        val item = getItem(position)
        holder.data = item
        config.typeList[holder.itemViewType].onBindViewHolder?.invoke(
            holder as LazyViewHolder<ViewDataBinding, Any>,
            item
        )
        config.typeList[holder.itemViewType].onBind?.invoke(holder.binding, item)
    }

    companion object {}
}