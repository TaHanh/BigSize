package com.example.bigsize.ui.home

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigsize.R
import com.example.bigsize.modal.StyleSizeModal
import kotlinx.android.synthetic.main.activity_size_list.*

class SizeListActivity : AppCompatActivity() {
    var sizeList: ArrayList<StyleSizeModal> = ArrayList();
    var sizeChecked = 100;
    var sizeDefault: Float = 20F;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_size_list)
        var list: ArrayList<Int> = arrayListOf(100, 120, 140, 160, 180, 200, 300);
        for (item: Int in list) {
            sizeList.add(StyleSizeModal(item))
        }
        configGirdView(sizeDefault);
        var defaultTextView: TextView = TextView(this);
        var sourceTextSize = defaultTextView.getTextSize();
        sizeDefault =
            Math.ceil((sourceTextSize / getResources().getDisplayMetrics().density).toDouble())
                .toFloat();
        Log.e("111","123 $sizeDefault")
        configGirdView(sizeDefault);
    }

    private fun configGirdView(sizeDefault: Float) {
        var adapter = SizeAdapter(sizeList, sizeDefault);
        recyclerSizeList.setAdapter(adapter);
        recyclerSizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.onClickItem = object : SizeAdapter.OnClickItem {
            override fun onClick(index: Int) {
                applySize(sizeList[index].ratio)
            }

        }
    }


    private fun applySize(value: Int) {
        Log.e("applySize", "applySize $value")
    }
}