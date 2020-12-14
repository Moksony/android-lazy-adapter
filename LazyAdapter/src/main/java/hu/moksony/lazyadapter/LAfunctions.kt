@file:Suppress("UNCHECKED_CAST")

package hu.moksony.lazyadapter

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

inline fun Activity.lazyAdapter(onBind: LazyAdapterBuilder.() -> Unit): LazyAdapter {
    val builder = LazyAdapterBuilder()
    onBind.invoke(builder)
    return builder.buildAdapter()
}

inline fun <reified IT : Any, VDB : ViewDataBinding> Activity.lazyAdapter(
    @LayoutRes layoutRes: Int,
    noinline onBind: VDB.(IT?) -> Unit
): LazyAdapter {
    val builder = LazyAdapterBuilder()
    builder.row<IT, VDB>(layoutRes, onBind)
    return builder.buildAdapter()
}

inline fun <reified IT : Any, VDB : ViewDataBinding> LazyAdapterBuilder.row(
    @LayoutRes layoutRes: Int,
    noinline onBind: VDB.(IT?) -> Unit
) {
    addRowType(
        layoutRes,
        IT::class as KClass<Any>,
        onBind
    )
}