package dada.com.cmproject.ui.first_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dada.com.cmproject.R
import dada.com.cmproject.ui.second_page.SecondPageActivity
import kotlinx.android.synthetic.main.activity_first_page.*

class FirstPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        fp_mb_next.setOnClickListener {
            val intent = Intent(this,SecondPageActivity::class.java)
            startActivity(intent)
        }
    }
}