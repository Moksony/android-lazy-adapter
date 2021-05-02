package hu.moksony.lazyadapter

import androidx.databinding.ViewDataBinding

class LazyAdapterConfig<IT>() {
    val typeList = mutableListOf<LazyItem<ViewDataBinding, Any>>()

    inline fun <reified IT : Any, reified VDB : ViewDataBinding> type(
        call: LazyItem<VDB, IT>.() -> Unit
    ) {
        val row = LazyItem(VDB::class, IT::class)
        call.invoke(row)
        this.typeList.add(row as LazyItem<ViewDataBinding, Any>)
    }

    fun build(): LazyAdapter<IT> {
        return LazyAdapter(this)
    }

    fun <K : IT> findType(item: K): LazyItem<ViewDataBinding, Any> {
        val index = typeList.indexOfFirst {
            item!!::class.isInstance(item)
        }
        return typeList[index]
    }
}