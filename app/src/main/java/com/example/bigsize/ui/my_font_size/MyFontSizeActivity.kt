package com.example.bigsize.ui.home

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigsize.R
import com.example.bigsize.common.Common
import com.example.bigsize.common.Constant
import com.example.bigsize.modal.StyleSizeModal
import com.example.bigsize.ui.custom_font_size.CustomFontSizeActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_font_size_list.*
import kotlinx.android.synthetic.main.activity_size_list.*
import kotlinx.android.synthetic.main.activity_size_list.recyclerSizeList

class MyFontSizeActivity : AppCompatActivity(), View.OnClickListener {
    var mySizeList: ArrayList<StyleSizeModal> = ArrayList();
    private lateinit var sharedPreferences: SharedPreferences
    val Constant = Constant()
    private lateinit var adapter: MyFontSizeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_font_size_list)
        sharedPreferences = this.getSharedPreferences(
            Constant.sharedPref,
            Context.MODE_PRIVATE
        )
        btnMyBtnAdd.setOnClickListener(this)
        checkEmptyList()
        initData()
    }
fun checkEmptyList(){
    if(Common.fontSizeSharedPref.size > 0) {
        linearEmpty.visibility = View.GONE
    } else {
        linearEmpty.visibility = View.VISIBLE
    }
}
    fun initData() {
        for (item: Int in Common.fontSizeSharedPref) {
            mySizeList.add(StyleSizeModal(item))
        }
        configGirdView(mySizeList, Common.sizeDefault);
        mySizeProgressBar.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnMyBtnAdd -> {
                onBackPressed()
            }
        }
    }

    private fun setSharedPref() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        var sizeListString: String =
            Common.fontSizeSharedPref.joinToString(separator = ",")
        editor.putString(Constant.sharedPrefSize, sizeListString)
        editor.apply()
        editor.commit()
    }

    private fun configGirdView(
        sizeList: List<StyleSizeModal>,
        sizeDefault: Float
    ) {
        adapter = MyFontSizeAdapter(sizeList, sizeDefault);
        recyclerMyFontSizeList.setAdapter(adapter);
        recyclerMyFontSizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.onClickItem = object : MyFontSizeAdapter.OnClickItem {
            override fun onClick(index: Int) {
                mySizeProgressBar.visibility = View.VISIBLE
                Observable.just(applySize(sizeList[index].ratio)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.e("TAGGGGG", "onClick: done")
                        mySizeProgressBar.visibility = View.GONE
                    };
            }

            override fun onClickDelete(index: Int) {
                mySizeList.removeAt(index)
                Common.fontSizeSharedPref.removeAt(index)
                setSharedPref()
                adapter.setData(mySizeList)
                checkEmptyList()
            }

        }
    }

    private fun applySize(value: Int) {
        var result: Float = value / 100.0.toFloat();
        Settings.System.putFloat(
            getBaseContext().getContentResolver(),
            Settings.System.FONT_SCALE, result
        );
    }


}