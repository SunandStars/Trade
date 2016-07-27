package com.example.dawn.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;

/**
 * Created by XU_aa on 2015/11/10.
 */
public class ChangePhoneNumber2 extends Activity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_phonenum2);
        sharedPreferences=getSharedPreferences("userinf",MODE_PRIVATE);
        final String phonenum= sharedPreferences.getString("phoneNumber", null);
        TextView tv_invisible=(TextView)findViewById(R.id.old_phonenum2);
        String invisible = phonenum.substring(0,3)+"****"+phonenum.substring(7,phonenum.length());
        tv_invisible.setText(invisible);
        Button btn_next=(Button)findViewById(R.id.manage_changenum_nextbtn2);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tv_visible=(EditText)findViewById(R.id.new_phonenum2);
                if(tv_visible.getText().toString().equals(phonenum)){
                    Intent intent=new Intent(ChangePhoneNumber2.this,ChangePhoneNumber3.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(ChangePhoneNumber2.this,"填写完整号码",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
