package hu.moksony.lazyadapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass

class LazyAdapterBuilder {
    private val rowTypes = mutableListOf<ViewHolderHelper<ViewDataBinding, Any>>()

    fun <VDB : ViewDataBinding, IT : Any> addRowType(
        @LayoutRes layoutRes: Int,
        managedClass: KClass<Any>,
        onBind: VDB.(IT?) -> Unit
    ) {

        val helper = ViewHolderHelper<VDB, IT>(layoutRes, managedClass, onBind)
        rowTypes.add(helper as ViewHolderHelper<ViewDataBinding, Any>)
    }

    fun buildAdapter(): LazyAdapter {
        return LazyAdapter(rowTypes)
    }
}