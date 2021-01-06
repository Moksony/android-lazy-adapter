package hu.moksony.lazyadapter

import android.app.Activity
import androidx.databinding.ViewDataBinding


fun <T> lazyTypedAdapter(
    builder: LazyAdapterBuilder<T>.() -> Unit
): LazyAdapter<T> {
    val config = LazyAdapterBuilder<T>()
    builder.invoke(config)
    return config.build()
}

fun lazyAdapter(
    builder: LazyAdapterBuilder<Any>.() -> Unit
): LazyAdapter<Any> {
    val config = LazyAdapterBuilder<Any>()
    builder.invoke(config)
    return config.build()
}

inline fun <reified VDB : ViewDataBinding, reified IT : Any> simpleLazyAdapter(
    call: LazyRowType<VDB, IT>.() -> Unit
): LazyAdapter<IT> {
    val config = LazyAdapterBuilder<IT>()
    config.type(call)
    return config.build()
}