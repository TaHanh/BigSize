package com.example.bigsize.ui.home

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bigsize.R
import com.example.bigsize.modal.StyleSizeModal
import kotlinx.android.synthetic.main.item_font_size.view.*

class SizeAdapter(var list: List<StyleSizeModal>, var sizeDefault: Float, var selectedRatio:Float) :
    RecyclerView.Adapter<SizeAdapter.ItemSizeViewHolder>() {
    var onClickItem: OnClickItem? = null;

    class ItemSizeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    fun setData(data: List<StyleSizeModal>) {
        this.list = data
        notifyDataSetChanged()
    }

    fun setChecked(position: Int) {
        var value: Float = list.get(position).ratio / 100.toFloat()
        selectedRatio = value;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.ItemSizeViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_font_size, parent, false)
        return ItemSizeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemSizeViewHolder, position: Int) {
        var radioDes = ""
        var radio = list.get(position).ratio / 100.toFloat()

        if (list.get(position).ratio == 100) {
            radioDes = "Default (1x)"
        } else {
            radioDes = "(${radio}x)"
        }

        if (selectedRatio == list.get(position).ratio / 100.toFloat()) {
            holder.itemView.imgChecked.visibility = View.VISIBLE
            holder.itemView.btnApply.visibility = View.INVISIBLE
        } else {
            holder.itemView.imgChecked.visibility = View.GONE
            holder.itemView.btnApply.visibility = View.VISIBLE
        }

        holder.itemView.txtTitle.text = "${list.get(position).ratio}% - $radioDes"
        holder.itemView.txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, sizeDefault);
        holder.itemView.txtSizeHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, radio * sizeDefault);
        holder.itemView.txtSizeNormal.setTextSize(
            TypedValue.COMPLEX_UNIT_DIP,
            radio * (sizeDefault - 2)
        );
        holder.itemView.btnApply.setOnClickListener({
            setChecked(position);
            onClickItem?.onClick(position);
        })
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    interface OnClickItem {
        fun onClick(index: Int)
    }
}