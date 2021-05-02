package hu.moksony.lazyadapter

import androidx.databinding.ViewDataBinding


fun <T> lazyTypedAdapter(
    builder: LazyAdapterConfig<T>.() -> Unit
): LazyAdapter<T> {
    val config = LazyAdapterConfig<T>()
    builder.invoke(config)
    return config.build()
}

fun lazyAdapter(
    builder: LazyAdapterConfig<Any>.() -> Unit
): LazyAdapter<Any> {
    val config = LazyAdapterConfig<Any>()
    builder.invoke(config)
    return config.build()
}

inline fun <reified VDB : ViewDataBinding, reified IT : Any> simpleLazyAdapter(
    call: LazyItem<VDB, IT>.() -> Unit
): LazyAdapter<IT> {
    val config = LazyAdapterConfig<IT>()
    config.type(call)
    return config.build()
}