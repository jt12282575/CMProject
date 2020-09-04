package dada.com.cmproject.ui.second_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dada.com.cmproject.R
import dada.com.cmproject.const.BundleKeyConst.Companion.ARG_DATA
import dada.com.cmproject.const.ViewConst.Companion.EACH_ROM
import dada.com.cmproject.model.Data
import dada.com.cmproject.ui.third_page.ThirdPageActivity
import dada.com.cmproject.util.*
import kotlinx.android.synthetic.main.activity_second_page.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondPageActivity : AppCompatActivity() {
    private val model: SecondPageViewModel by viewModels { SecondPageVMFactory }
    private val dataList = mutableListOf<Data>()
    private var screenWidth = 0
    private val dataAdapter by lazy {
        DataAdapter(
            dataList = dataList,
            loadImage = { urlString, imageView ->
                lifecycleScope.launch(Dispatchers.IO) {
                    val targetImageWidth = screenWidth/ EACH_ROM
                    loadImage(urlString, targetImageWidth).let { bitmap ->
                        launch(Dispatchers.Main) {
                            imageView.setImageBitmap(bitmap)
                        }
                    }
                }
            },
            onItemClick = {
                val intent = Intent(this,ThirdPageActivity::class.java)
                intent.putExtra(ARG_DATA,it)
                startActivity(intent)
            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)
        init()

        asp_retry_button.setOnClickListener {
            asp_retry_button.isVisible = false
            model.fetchData()
        }

        model.spinner.observe(this, Observer {
            asp_progress_bar.isVisible = it
        })

        model.data.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    asp_retry_button.isVisible = false
                    if (it.data != null) {
                        initRecyclerView(it.data)
                    }

                }
                Status.ERROR -> {
                    asp_retry_button.isVisible = true
                }
            }
        })

    }

    private fun init() {
        screenWidth = getScreenWidth(this.windowManager)
    }

    private fun initRecyclerView(list: List<Data>) {
        dataList.clear()
        dataList.addAll(list)
        asp_rcv_list.apply {
            layoutManager = GridLayoutManager(applicationContext, EACH_ROM)
            adapter = dataAdapter
        }
    }


}