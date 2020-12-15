package hu.moksony.lazyadapter

import android.app.Activity
import androidx.databinding.ViewDataBinding

inline fun <reified VDB : ViewDataBinding, reified IT : Any>
        Activity.lazyAdapter(
    noinline builder: LazyAdapterBuilder<VDB, IT>.() -> Unit
): LazyAdapter<IT, VDB> {
    val config = LazyAdapterBuilder(VDB::class, IT::class)
    builder.invoke(config)
    return config.build()
}