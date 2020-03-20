package com.big.font.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.big.font.R;

public class AddSizeActivity extends AppCompatActivity implements View.OnClickListener {
TextView seekbarText;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_size);getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_font));


        init();
        changeSeekBar();
        }

        public void init() {
         seekBar = findViewById(R.id.seekbar);
         seekbarText = findViewById(R.id.seekbarText);

        }
    public void changeSeekBar() {
        int progre = seekBar.getProgress();
        seekbarText.setText(Integer.toString(progre * 5) + "% (" + String.format("%.1f", progre*1.0/10) +"x)" );
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
            seekbarText.setText(Integer.toString(progress * 5) + "% (" + String.format("%.1f", progress*1.0/10) +"x)" );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
