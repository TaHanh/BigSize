package com.example.bigsize.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bigsize.R
import com.example.bigsize.modal.StyleSizeModal
import kotlinx.android.synthetic.main.item_size_list.view.*

class SizeAdapter(var list: List<StyleSizeModal>): RecyclerView.Adapter<SizeAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_size_list,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
     holder.itemView.txtTitle.text = list.get(position).ratio
    }

    override fun getItemCount(): Int {
        return list.size;
    }
}