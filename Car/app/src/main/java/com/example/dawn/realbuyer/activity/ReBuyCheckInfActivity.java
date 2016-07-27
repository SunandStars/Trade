package com.example.dawn.realbuyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
/**
 * Created by wanxiao on 2015/11/6.
 */
public class ReBuyCheckInfActivity extends Activity
{
    SharedPreferences preferencesbuycheckinf;

    @Override
   protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realbuyer_manage_check_inf);

    ////////////////////////读取本地显示

        Button btn_back=(Button) findViewById(R.id.manage_checkinf_bnback);
        Button btn_edit=(Button) findViewById(R.id.manage_checkinf_bnedit);

        TextView usertype=(TextView)findViewById(R.id.manage_checkinf_tv_type);
        TextView username=(TextView)findViewById(R.id.manage_checkinf_tv_username);
        TextView phone=(TextView)findViewById(R.id.manage_checkinf_tv_phone);
        TextView company=(TextView)findViewById(R.id.manage_checkinf_tv_company);
        TextView legalperson=(TextView)findViewById(R.id.manage_checkinf_tv_legalperson);
        TextView address=(TextView)findViewById(R.id.manage_checkinf_tv_address);
        TextView post=(TextView)findViewById(R.id.manage_checkinf_tv_post);
        TextView zhifubao=(TextView)findViewById(R.id.manage_checkinf_tv_zhifubao);
        TextView wechat=(TextView)findViewById(R.id.manage_checkinf_tv_wechat);
        TextView qq=(TextView)findViewById(R.id.manage_checkinf_tv_qq);
        TextView email=(TextView)findViewById(R.id.manage_checkinf_tv_email);
        ImageView iv1=(ImageView)findViewById(R.id.manage_checkinf_tv_image1);
        ImageView iv2=(ImageView)findViewById(R.id.manage_checkinf_tv_image2);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"---------",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ReBuyCheckInfActivity.this,ReBuyEditInfActivity.class);
                startActivity(intent);
                finish();
            }
        });


        preferencesbuycheckinf=this.getSharedPreferences("userinf", MODE_PRIVATE);

        //读取 SharedPreferences中的数据
        usertype.setText(preferencesbuycheckinf.getString("usertype", null));
        username.setText(preferencesbuycheckinf.getString("username", null));
        phone.setText(preferencesbuycheckinf.getString("phoneNumber", null));
        company.setText(preferencesbuycheckinf.getString("company", null));
        legalperson.setText(preferencesbuycheckinf.getString("legalpersonname", null));
        address.setText(preferencesbuycheckinf.getString("address", null));
        post.setText(preferencesbuycheckinf.getString("postnum", null));
        zhifubao.setText(preferencesbuycheckinf.getString("alipaynum", null));
        wechat.setText(preferencesbuycheckinf.getString("weixinnum", null));
        qq.setText(preferencesbuycheckinf.getString("qq", null));
        email.setText(preferencesbuycheckinf.getString("email", null));
        String pic1=preferencesbuycheckinf.getString("lisencepic",null);
        String pic2=preferencesbuycheckinf.getString("companypic", null);

        //将被转换成String类型的图片文件解析成以前的ImageView类型
        byte[] bitmapArray1= Base64.decode(pic1, Base64.DEFAULT);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapArray1, 0,
                bitmapArray1.length);
        iv1.setImageBitmap(bitmap1);

        byte[] bitmapArray2= Base64.decode(pic2, Base64.DEFAULT);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(bitmapArray2, 0,
                bitmapArray2.length);
        iv2.setImageBitmap(bitmap2);

    }
//    @Override
//    public void onResume(){
//        super.onResume();
//        setContentView(R.layout.realbuyer_manage_check_inf);
//        //重新加载对应项，onResume是指重新打开该页面执行
//        ////////////////////////读取本地显示
//
//    }


}
