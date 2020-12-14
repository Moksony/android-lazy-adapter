package hu.moksony.lazyadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass

data class ViewHolderHelper<VDB : ViewDataBinding, IT : Any>(
    @LayoutRes val layoutId: Int,
    val managedClass: Any?,
    val onBind: VDB.(IT?) -> Unit
) {
    fun createBinding(inflater: LayoutInflater, parent: ViewGroup): VDB {
        return DataBindingUtil.inflate(inflater, layoutId, parent, false)
    }
}