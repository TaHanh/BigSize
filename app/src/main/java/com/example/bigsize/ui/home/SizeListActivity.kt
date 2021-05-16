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
import android.os.Handler
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
import kotlinx.android.synthetic.main.activity_size_list.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class SizeListActivity : AppCompatActivity(), View.OnClickListener {
    var sizeList: ArrayList<StyleSizeModal> = ArrayList();
    var sizeDefault: Float = 18F;
    private val REQUEST_CODE = 101
    private var isPermission = false;
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    private val THIRD_ACTIVITY_REQUEST_CODE = 1
    lateinit var sharedPreferences: SharedPreferences
    val Constant = Constant()
    lateinit var adapter: SizeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_size_list)
        setupPermissions()
        sharedPreferences = this.getSharedPreferences(
            Constant.sharedPref,
            Context.MODE_PRIVATE
        )
        imgBtnAdd.setOnClickListener(this)
        btnMyFontSize.setOnClickListener(this)
        init()
    }

    fun init() {
        llProgressBar.visibility = View.GONE
        var list: ArrayList<Int> = arrayListOf(100, 120, 140, 160, 180, 200);
        Common.fontSizeSharedPref = getSharedPref()
        list.addAll(0, Common.fontSizeSharedPref)
        for (item: Int in list) {
            sizeList.add(StyleSizeModal(item))
        }
        Common.fontSizeList.addAll(list)
        Common.selectedRatio = getResources().getConfiguration().fontScale;
        configGirdView(sizeList, sizeDefault, Common.selectedRatio);
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgBtnAdd -> {
                val intent = Intent(this, CustomFontSizeActivity::class.java)
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
            }
            R.id.btnMyFontSize -> {

                val intent = Intent(this, MyFontSizeActivity::class.java)
                startActivityForResult(intent, THIRD_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val value = data!!.getIntExtra("keyName", 0)
                if (value != 0) {
                    sizeList.add(0, StyleSizeModal(value));
                    Common.fontSizeList.add(0, value)
                    Common.fontSizeSharedPref.add(0, value)
                    setSharedPref()
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private fun configGirdView(
        sizeList: List<StyleSizeModal>,
        sizeDefault: Float,
        selectedRatio: Float
    ) {
        adapter = SizeAdapter(sizeList, sizeDefault, selectedRatio);
        recyclerSizeList.setAdapter(adapter);
        recyclerSizeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.onClickItem = object : SizeAdapter.OnClickItem {
            override fun onClick(index: Int) {
                llProgressBar.visibility = View.VISIBLE

                Observable.just(applySize(sizeList[index].ratio)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
//                        Log.e("TAGGGGG", "onClick: done")
                        val handler = Handler()
                        handler.postDelayed({
                            llProgressBar.visibility = View.GONE
                            showDialog()
                        }, 1000)

                    };

            }

        }
    }

    private fun getSharedPref(): ArrayList<Int> {
        val sharedIdValue = sharedPreferences.getString(Constant.sharedPrefSize, "defaultname")
        if (sharedIdValue.equals("defaultname")) {
            return arrayListOf()
        } else {
            val result: ArrayList<Int> = arrayListOf()
            sharedIdValue?.split(",")?.map {
                if (it.isNotEmpty()) {

                    result.add(it.toInt())
                }
            }
            if (result.size > 0) {
                return result
            }
            return arrayListOf()

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

    private fun applySize(value: Int) {
        var result: Float = value / 100.0.toFloat();
        try {
            Settings.System.putFloat(
                getBaseContext().getContentResolver(),
                Settings.System.FONT_SCALE, result
            );
        } catch (e: Exception){
            Log.e("TAG", "onClickException: $e")
        }

    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.notify_change_size_dialog)

        val btnOK = dialog.findViewById(R.id.btnOK) as Button
        btnOK.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    //PERMISSION
    private fun checkSinglePermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupPermissions() {
        if (!checkSinglePermission(Manifest.permission.WRITE_SETTINGS)) {
            requestPdfPermissions();
        } else {
            isPermission = true
        }
    }

    fun requestPdfPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            var permList = arrayOf(
                Manifest.permission.WRITE_SETTINGS
            )
            try {
                requestPermissions(permList, REQUEST_CODE)
                if (!Settings.System.canWrite(getApplicationContext())) {
                    var intent: Intent = Intent(
                        Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName())
                    );
                    startActivityForResult(intent, 200);
                }

            } catch (e: Exception) {

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    Log.e("12345", "12345 grantResults")
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("12345", "12345 true")
                        isPermission = true //Permission Granted
                    } else {
                        Log.e("12345", "12345 false")
                        isPermission = false //Permission Denied
                    }

                }
            }
        }
    }
}