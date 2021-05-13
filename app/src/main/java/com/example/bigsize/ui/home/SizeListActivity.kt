package com.example.bigsize.ui.home

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigsize.R
import com.example.bigsize.modal.StyleSizeModal
import com.example.bigsize.ui.custom_font_size.CustomFontSizeActivity
import kotlinx.android.synthetic.main.activity_size_list.*
import kotlinx.android.synthetic.main.create_size_dialog.*
import kotlinx.android.synthetic.main.item_size_list.view.*

class SizeListActivity : AppCompatActivity(), View.OnClickListener {
    var sizeList: ArrayList<StyleSizeModal> = ArrayList();
    var sizeChecked = 100;
    var sizeDefault: Float = 18F;
    private val REQUEST_CODE = 101
    private var isPermission = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_size_list)
        setupPermissions()

        var list: ArrayList<Int> = arrayListOf(100, 120, 140, 160, 180, 200, 300);
        for (item: Int in list) {
            sizeList.add(StyleSizeModal(item))
        }
//        var defaultTextView: TextView = TextView(this);
//        var sourceTextSize = defaultTextView.getTextSize();
//        sizeDefault =
//            Math.ceil((sourceTextSize / getResources().getDisplayMetrics().density).toDouble())
//                .toFloat();
//
//
//        Log.e("111","123 $sourceTextSize")
        init()
        configGirdView(sizeDefault);
    }
    fun init(){
        imgBtnAdd.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.imgBtnAdd->{
                val intent = Intent(this, CustomFontSizeActivity::class.java)
                startActivity(intent)
                startActivityForResult(intent, 1)
//                showDialog();
            }
        }
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
        var result: Float = value / 100.0.toFloat();
        Settings.System.putFloat(
            getBaseContext().getContentResolver(),
            Settings.System.FONT_SCALE, result
        );
//        adjustFontScale(getResources().getConfiguration(), result);
    }

    public fun adjustFontScale(configuration: Configuration, scale: Float) {
        configuration.fontScale = scale;
        var metrics: DisplayMetrics = getResources().getDisplayMetrics();
        var wm: WindowManager = getSystemService(WINDOW_SERVICE) as WindowManager;
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_size_dialog)
        val seekBar = dialog.findViewById(R.id.seekBar) as SeekBar
        val txtChangeSize = dialog.findViewById(R.id.txtChangeSize) as TextView


        val btnCreate = dialog.findViewById(R.id.btnCreate) as Button
        btnCreate.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("1234556677", "$requestCode $resultCode$data ")
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Log.e("1234556677", "$data")
            }

        }
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

            } catch (e: Exception) {
            }
            if (!Settings.System.canWrite(getApplicationContext())) {
                var intent: Intent = Intent(
                    Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName())
                );
                startActivityForResult(intent, 200);
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