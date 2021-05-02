package hu.moksony.lazyadapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass

class LazyItem<VDB : ViewDataBinding, IT : Any>(
    val viewDataBindingClass: KClass<VDB>,
    val itemClass: KClass<IT>
) {
    var onCreated: (VDB.() -> Unit)? = null
    var onViewHolderCreated: (LazyViewHolder<VDB, IT>.() -> Unit)? = null
    var onBind: (VDB.(IT?) -> Unit)? = null
    var onBindViewHolder: ((LazyViewHolder<VDB, IT>.(IT?) -> Unit))? = null
    var createViewHolder: ((VDB) -> LazyViewHolder<VDB, IT>)? = null
    var layoutId: Int? = null
    var id: (IT.() -> Any?)? = null


    fun id(call: IT.() -> Any) = apply { this.id = call }
    fun layoutId(@LayoutRes layoutRes: Int) = apply { this.layoutId = layoutRes }
    fun onCreated(call: VDB.() -> Unit) = apply { this.onCreated = call }
    fun onViewHolderCreated(call: (LazyViewHolder<VDB, IT>.() -> Unit)) = apply { this.onViewHolderCreated = call }
    fun onBind(call: VDB.(IT?) -> Unit) = apply { this.onBind = call }
    fun onBindViewHolder(call: (LazyViewHolder<VDB, IT>.(IT?) -> Unit)) =
        apply { this.onBindViewHolder = call }

    fun onCreateViewHolder(call: (VDB) -> LazyViewHolder<VDB, IT>) =
        apply { this.createViewHolder = call }

}