package hu.moksony.lazyadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.Exception

class LazyAdapter<T>(
    val config: LazyAdapterBuilder<T>
) :
    AbstractAdapter<LazyAdapterViewHolder<ViewDataBinding>, T>() {


    override fun getItemViewType(position: Int): Int {
        val item = getItemAt(position) ?: throw Exception("item can not be null")
        val index =  config.typeList.indexOfFirst {
            it.itemClass == item::class
        }

        //fix ex: Uri
        if(index == -1){
            return config.typeList.indexOfFirst {
                item::class.isInstance(item)
            }
        }
        return index
    }

    override fun getId(position: Int): Any? {
        val item = getItemAt(position) ?: return null
        return config.typeList[getItemViewType(position)].id?.invoke(item)
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LazyAdapterViewHolder<ViewDataBinding> {
        val typeHelper = config.typeList[viewType]!!
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
        val viewHolder: LazyAdapterViewHolder<ViewDataBinding>
        if (customViewHolderFn != null) {
            viewHolder = customViewHolderFn.invoke(binding)
        } else {
            viewHolder = LazyAdapterViewHolder(binding)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: LazyAdapterViewHolder<ViewDataBinding>, position: Int) {
        val item = getItemAt(position)
        config.typeList[holder.itemViewType].onBindViewHolder?.invoke(holder, item)
        config.typeList[holder.itemViewType].onBind?.invoke(holder.binding, item)
    }

    //    override fun createViewHolder(
//        inflater: LayoutInflater,
//        parent: ViewGroup,
//        viewType: Int
//    ): LazyAdapterViewHolder<VDB> {
//        val layoutId = config.layoutId
//        val binding: ViewDataBinding
//        if (layoutId == null) {
//
//            val method = config._vdb::java.get().getDeclaredMethod(
//                "inflate",
//                android.view.LayoutInflater::class.java,
//                android.view.ViewGroup::class.java,
//                Boolean::class.java
//            )
//            binding = method.invoke(null, inflater, parent, false) as VDB
//        } else {
//            binding = DataBindingUtil.inflate(inflater, layoutId, parent, false)
//        }
//
//
//        val holderInflater = config.onCreateViewHolder
//        val adapter: LazyAdapterViewHolder<VDB>
//        if (holderInflater != null) {
//            adapter = holderInflater.invoke(this)
//        } else {
//            adapter = LazyAdapterViewHolder<VDB>(binding as VDB)
//        }
//        config.onCreated?.invoke(binding as VDB)
//        return adapter
//    }
//
//    override fun onBindViewHolder(holder: LazyAdapterViewHolder<VDB>, position: Int) {
//        val item = getItemAt(position)
//        config.onBind?.invoke(holder.binding, item)
//    }
}