package dada.com.cmproject.ui.second_page

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import dada.com.cmproject.R
import dada.com.cmproject.model.Data

class DataAdapter(
    private val dataList:List<Data>,
    private val loadImage:(urlString:String,imageView:ImageView)->Unit,
    private val onItemClick:(data:Data)->Unit
): RecyclerView.Adapter<DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return  DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick.invoke(dataList[position])
        }
        holder.bindView(dataList[position],loadImage)
    }
}