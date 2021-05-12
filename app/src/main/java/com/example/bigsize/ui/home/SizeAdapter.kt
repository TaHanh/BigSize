package com.example.bigsize.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bigsize.R
import com.example.bigsize.modal.StyleSizeModal
import kotlinx.android.synthetic.main.item_size_list.view.*

class SizeAdapter(var list: List<StyleSizeModal>, var sizeDefault: Float) :
    RecyclerView.Adapter<SizeAdapter.ItemViewHolder>() {
    var sizeHeader: Float = 22F
    var sizeNormal: Float = 18F
    var sizeChecked = 100;
    var onClickItem: OnClickItem? = null;


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    fun setData(data: List<StyleSizeModal>) {
        this.list = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_size_list, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var radioDes = ""
        var radio = list.get(position).ratio / 100.toFloat()

        if (list.get(position).ratio == 100) {
            radioDes = "Default (1x)"
        } else {
            radioDes = "(${list.get(position).ratio / 100.toDouble()}x)"
        }

        if (sizeChecked == list.get(position).ratio) {
            holder.itemView.imgChecked.visibility = View.VISIBLE
        } else {
            holder.itemView.imgChecked.visibility = View.GONE
        }

        holder.itemView.txtTitle.text = "${list.get(position).ratio}% - $radioDes"
        holder.itemView.txtSizeHeader.textSize = radio * sizeDefault
        holder.itemView.txtSizeNormal.textSize = radio * (sizeDefault - 2)
        holder.itemView.btnApply.setOnClickListener({
            onClickItem?.onClick(position)
            sizeChecked = list.get(position).ratio
            setData(list)
        })
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    interface OnClickItem {
        fun onClick(index: Int)
    }
}