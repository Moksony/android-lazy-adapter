package hu.moksony.lazyviewholderexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import hu.moksony.lazyadapter.*
import hu.moksony.lazyviewholderexample.databinding.ActivityMainBinding
import hu.moksony.lazyviewholderexample.databinding.RowTextBodyItemBinding
import hu.moksony.lazyviewholderexample.databinding.RowTextItemBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val recyclerView = binding.recyclerView


        val adapter = lazyTypedAdapter<UIModel> {
            type<UIModel.BodyItem, RowTextBodyItemBinding> {
                onBind {
                    this.text = it?.headerItem?.text
                }
                id {
                    this.headerItem.text
                }
            }
            type<UIModel.HeaderItem, RowTextItemBinding> {
                onBind {
                    this.position = it?.headerItem?.position
                }

                onViewHolderCreated {
                    this.binding.root.setOnClickListener {
                        Log.d(TAG, "onCreate: $data")
                    }
                }
            }
        }

        recyclerView.adapter = adapter

        val items = listOf(
            UIModel.HeaderItem(Header(1)),
            UIModel.BodyItem(Body("Body 1")),
            UIModel.BodyItem(Body("Body 2")),
            UIModel.HeaderItem(Header(2)),
            UIModel.BodyItem(Body("Body 3")),
        )
        adapter.submit(items)
    }

    companion object
    {
        private const val TAG = "MainActivity"
    }
}