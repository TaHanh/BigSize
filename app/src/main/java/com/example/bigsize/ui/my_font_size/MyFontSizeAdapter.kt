package com.example.bigsize.ui.home

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bigsize.R
import com.example.bigsize.common.Common
import com.example.bigsize.modal.StyleSizeModal
import kotlinx.android.synthetic.main.item_my_font_size.view.*

class MyFontSizeAdapter(var list: List<StyleSizeModal>, var sizeDefault: Float) :
    RecyclerView.Adapter<MyFontSizeAdapter.ItemMyFontViewHolder>() {

    var onClickItem: OnClickItem? = null;


    class ItemMyFontViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    fun setData(data: List<StyleSizeModal>) {
        this.list = data
        notifyDataSetChanged()
    }

    fun setChecked(position: Int) {
        var value: Float = list.get(position).ratio / 100.toFloat()
        Common.selectedRatio = value;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMyFontViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_font_size, parent, false)
        return ItemMyFontViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemMyFontViewHolder, position: Int) {

        var radio = list.get(position).ratio / 100.toFloat()
        var radioDes = "(${radio}x)"

        if (Common.selectedRatio == list.get(position).ratio / 100.toFloat()) {
            holder.itemView.imgMyChecked.visibility = View.VISIBLE
            holder.itemView.btnMyApply.visibility = View.GONE
        } else {
            holder.itemView.imgMyChecked.visibility = View.GONE
            holder.itemView.btnMyApply.visibility = View.VISIBLE
        }

        holder.itemView.txtMyTitle.text = "${list.get(position).ratio}% - $radioDes"
        holder.itemView.txtMyTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, sizeDefault);
        holder.itemView.txtMySizeHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, radio * sizeDefault);
        holder.itemView.txtMySizeNormal.setTextSize(
            TypedValue.COMPLEX_UNIT_DIP,
            radio * (sizeDefault - 2)
        );
        holder.itemView.btnMyApply.setOnClickListener({
            setChecked(position);
            onClickItem?.onClick(position);
        })
        holder.itemView.btnMyDelete.setOnClickListener({
            onClickItem?.onClickDelete(position);

        })
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    interface OnClickItem {
        fun onClick(index: Int)
        fun onClickDelete(index: Int)
    }
}