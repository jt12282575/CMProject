package dada.com.cmproject.ui.second_page

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import dada.com.cmproject.model.Data
import kotlinx.android.synthetic.main.item_data.view.*

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // 圖片 Load 進來之後 ， 先拿到原圖 size ， 再去用比例 resize
    fun bindView(
        data: Data, loadImage: (urlString: String, imageView: ImageView) -> Unit
    ) {
        itemView.item_iv_image.setImageResource(android.R.color.transparent)
        itemView.item_tv_id.text = data.id.toString()
        itemView.item_tv_title.text = data.title
        loadImage.invoke(data.thumbnailUrl,itemView.item_iv_image)
    }

}