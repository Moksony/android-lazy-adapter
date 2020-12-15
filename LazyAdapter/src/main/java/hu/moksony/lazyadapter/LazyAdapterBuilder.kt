package hu.moksony.lazyadapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import java.lang.Exception
import kotlin.reflect.KClass

class LazyAdapterBuilder<VDB : ViewDataBinding, IT : Any>(
    internal val _vdb: KClass<VDB>,
    internal val _it: KClass<IT>
) {

    internal var onBind: (VDB.(IT?) -> Unit)? = null
    internal var onCreated: (VDB.() -> Unit)? = null
    internal var layoutId: Int? = null
    internal var onCreateViewHolder: ((adapter: LazyAdapter<IT, VDB>) -> LazyAdapterViewHolder<VDB>)? =
        null

    fun build(): LazyAdapter<IT, VDB> {
        val vdb = this._vdb ?: throw Exception("ViewDataBinding Required")
        return LazyAdapter<IT, VDB>(this)
    }

    fun onBind(callback: (VDB.(IT?) -> Unit)) {
        this.onBind = callback
    }

    fun onCreated(callback: VDB.() -> Unit) {
        this.onCreated = callback
    }

    fun onCreateViewHolder(callback: (adapter: LazyAdapter<IT, VDB>) -> LazyAdapterViewHolder<VDB>) {
        this.onCreateViewHolder = callback
    }

    fun layoutId(@LayoutRes layoutRes: Int) {
        this.layoutId = layoutRes
    }

}