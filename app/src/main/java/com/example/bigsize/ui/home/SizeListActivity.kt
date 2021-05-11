package com.example.bigsize.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigsize.R
import com.example.bigsize.modal.StyleSizeModal
import kotlinx.android.synthetic.main.activity_size_list.*

class SizeListActivity : AppCompatActivity() {
    var sizeList: ArrayList<StyleSizeModal> = ArrayList();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_size_list)
        sizeList.add(StyleSizeModal("7"))
        configGirdView();
    }

    private fun configGirdView() {
        var adapter = SizeAdapter(sizeList);
recyclerSizeList.setAdapter(adapter);
        recyclerSizeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}