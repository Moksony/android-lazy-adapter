package hu.moksony.lazyadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class LazyAdapter(
    private val viewHolderHelper: MutableList<ViewHolderHelper<ViewDataBinding, Any>>
) : AbstractRecyclerView<LazyViewHolder<ViewDataBinding>, Any>() {

    override fun getItemViewType(position: Int): Int {
        val item = getItemAt(position)!!
        var index = 0
        for (lazyItem in viewHolderHelper) {
            if (lazyItem.managedClass == item::class) {
                return index
            }
            index++
        }
        throw Exception("View type not found")
    }

    override fun createViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int //key of
    ): LazyViewHolder<ViewDataBinding> {
        val helper = viewHolderHelper[viewType].createBinding(inflater, parent)
        return LazyViewHolder(helper)
    }

    override fun onBindViewHolder(holder: LazyViewHolder<ViewDataBinding>, position: Int) {
        viewHolderHelper[holder.itemViewType].onBind(holder.binding, getItemAt(position))
    }
}