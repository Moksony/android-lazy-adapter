package hu.moksony.lazyviewholderexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val l = List<UIModel>()
        l.set(UIModel.HeaderItem(Header(1)))

        val adapter = LazzyAdapter<UIModel>()

        recyclerView.adapter = adapter

        val items = listOf<UIModel>(
            UIModel.BodyItem(Body("Body 1")),
            UIModel.BodyItem(Body("Body 2")),
            UIModel.BodyItem(Body("Body 3")),
        )
        adapter.submit(items)
    }
}