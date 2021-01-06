package hu.moksony.lazyadapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass

class LazyRowType<VDB : ViewDataBinding, IT : Any>(
    val viewDataBindingClass: KClass<VDB>,
    val itemClass: KClass<IT>
) {
    var onCreated: (VDB.() -> Unit)? = null
    var onBind: (VDB.(IT?) -> Unit)? = null
    var onBindViewHolder: ((LazyAdapterViewHolder<VDB>.(IT?) -> Unit))? = null
    var createViewHolder: ((VDB) -> LazyAdapterViewHolder<VDB>)? = null
    var layoutId: Int? = null
    var id: (IT.() -> Any?)? = null


    fun id(call: IT.() -> Any) = apply { this.id = call }
    fun layoutId(@LayoutRes layoutRes: Int) = apply { this.layoutId = layoutRes }
    fun onCreated(call: VDB.() -> Unit) = apply { this.onCreated = call }
    fun onBind(call: VDB.(IT?) -> Unit) = apply { this.onBind = call }
    fun onBindViewHolder(call: (LazyAdapterViewHolder<VDB>.(IT?) -> Unit)) =
        apply { this.onBindViewHolder = call }

    fun onCreateViewHolder(call: (VDB) -> LazyAdapterViewHolder<VDB>) =
        apply { this.createViewHolder = call }

}