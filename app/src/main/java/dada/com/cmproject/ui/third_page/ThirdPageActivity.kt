package dada.com.cmproject.ui.third_page

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dada.com.cmproject.R
import dada.com.cmproject.const.BundleKeyConst.Companion.ARG_DATA
import dada.com.cmproject.model.Data
import dada.com.cmproject.util.getScreenWidth
import dada.com.cmproject.util.loadImage
import kotlinx.android.synthetic.main.activity_third_page.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ThirdPageActivity : AppCompatActivity() {
    private var data: Data? = null
    private var screenWidth = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_page)
        init()
        data?.let {
            lifecycleScope.launch(Dispatchers.IO) {
                atp_tv_id.text = "id: ${it.id}"
                atp_tv_title.text = "title: ${it.title}"
                val targetImageSize =
                    screenWidth
                loadImage(it.url, targetImageSize).let { bitmap ->
                    launch(Dispatchers.Main) {
                        atp_iv_image.setImageBitmap(bitmap)
                    }
                }
            }
        }

    }

    private fun init() {
        data = intent.getSerializableExtra(ARG_DATA) as? Data
        screenWidth = getScreenWidth(this.windowManager)

    }
}