package hu.moksony.lazyadapter

import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass

class LazyAdapterBuilder<IT>() {
    val typeList = mutableListOf<LazyRowType<ViewDataBinding, Any>>()

    inline fun <reified IT: Any, reified VDB : ViewDataBinding> type(
        call: LazyRowType<VDB, IT>.() -> Unit
    ) {
        val row = LazyRowType(VDB::class, IT::class)
        call.invoke(row)
        this.typeList.add(row as LazyRowType<ViewDataBinding, Any>)
    }

    fun build(): LazyAdapter<IT> {
        return LazyAdapter(this)
    }
}