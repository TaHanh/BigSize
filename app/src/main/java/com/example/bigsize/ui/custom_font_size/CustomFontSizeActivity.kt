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
import com.example.bigsize.common.Constant
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
        Context.MODE_PRIVATE)
        initData()
    }
    fun initData() {
        btnBack.setOnClickListener(this)
        btnCreate.setOnClickListener(this)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                txtChangeSize.setTextSize(TypedValue.COMPLEX_UNIT_DIP, i/10.toFloat() * sizeDefault);
                txtChangeSize.text = "Ab ${i*10}%"
                data = i*10;
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
        when(v?.id) {
            R.id.btnBack -> {
                val sharedIdValue = sharedPreferences.getString(Constant.sharedPrefSize,"defaultname")
                if(sharedIdValue.equals("defaultname")){
                    Log.e("123", "default name: ${sharedIdValue}")

                }else{
                    Log.e("123", "default : ${sharedIdValue}")

                }
//                onBackPressed()
            }
            R.id.btnCreate -> {
                Log.e("123", "$data")


                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                var dataaa:String = arrayOf<Int>(data).joinToString(separator = ",")
                editor.putString(Constant.sharedPrefSize,dataaa)
                editor.apply()
                editor.commit()
            }
        }
    }
    override fun onBackPressed() {
        var intent = Intent();
        intent.putExtra("New Name", data);
        setResult(RESULT_OK, intent);
        super.onBackPressed()
    }
}