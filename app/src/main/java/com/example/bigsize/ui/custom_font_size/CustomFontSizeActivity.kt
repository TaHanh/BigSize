package com.example.bigsize.ui.custom_font_size

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.SeekBar
import com.example.bigsize.R
import com.example.bigsize.common.Common
import com.example.bigsize.common.Constant
import com.example.bigsize.modal.StyleSizeModal
import kotlinx.android.synthetic.main.activity_custom_font_size.*
import kotlinx.android.synthetic.main.activity_size_list.*

class CustomFontSizeActivity : AppCompatActivity(), View.OnClickListener {
    var sizeDefault: Float = 18F;
    var data = 100;
    val Constant = Constant()
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_font_size)
        sharedPreferences = this.getSharedPreferences(
            Constant.sharedPref,
            Context.MODE_PRIVATE
        )
        initData()
    }

    fun initData() {
        btnBack.setOnClickListener(this)
        btnCreate.setOnClickListener(this)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                txtChangeSize.setTextSize(
                    TypedValue.COMPLEX_UNIT_DIP,
                    i / 10.toFloat() * sizeDefault
                );
                txtChangeSize.text = "Ab ${i * 10}%"
                data = i * 10;
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
//                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
//                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnBack -> {
                onBackPressed()
            }
            R.id.btnCreate -> {
                if (Common.fontSizeList.contains(data)) {
                    txtNotifyCreateSize.text = "This font size already exists"
                } else {
                    txtNotifyCreateSize.text = ""
                    // Put the String to pass back into an Intent and close this activity
                    val intent = Intent()
                    intent.putExtra("keyName", data)
                    setResult(RESULT_OK, intent)
                    finish()
                }

            }
        }
    }

}