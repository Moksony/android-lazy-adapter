package hu.moksony.lazyviewholderexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import hu.moksony.lazyadapter.*
import hu.moksony.lazyviewholderexample.databinding.ActivityMainBinding
import hu.moksony.lazyviewholderexample.databinding.RomTextBodyItemBinding
import hu.moksony.lazyviewholderexample.databinding.RowTextItemBinding
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val recyclerView = binding.recyclerView


        val adapter = lazyTypedAdapter<UIModel> {
            type<UIModel.BodyItem, RomTextBodyItemBinding> {
                onBind {
                    this.text = it?.headerItem?.text
                }
                id {
                    this.headerItem.text
                }
            }
        }

        recyclerView.adapter = adapter

        val items = listOf(
            UIModel.BodyItem(Body("Body 1")),
            UIModel.BodyItem(Body("Body 2")),
            UIModel.BodyItem(Body("Body 3")),
        )
        adapter.submit(items)
    }
}