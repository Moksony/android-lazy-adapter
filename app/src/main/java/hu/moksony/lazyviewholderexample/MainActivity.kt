package hu.moksony.lazyviewholderexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import hu.moksony.lazyadapter.*
import hu.moksony.lazyviewholderexample.databinding.ActivityMainBinding
import hu.moksony.lazyviewholderexample.databinding.RomTextBodyItemBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val recyclerView = binding.recyclerView

        val adapter = lazyAdapter<RomTextBodyItemBinding, UIModel.BodyItem> {
            onBind {
                this.text = it?.headerItem?.text
            }
            onCreated {
                this.lifecycleOwner = this@MainActivity
            }
            layoutId(R.layout.rom_text_body_item)
        }

        recyclerView.adapter = adapter

        val items = listOf<UIModel.BodyItem>(
            UIModel.BodyItem(Body("Body 1")),
            UIModel.BodyItem(Body("Body 2")),
            UIModel.BodyItem(Body("Body 3")),
        )
        adapter.submit(items)
    }
}