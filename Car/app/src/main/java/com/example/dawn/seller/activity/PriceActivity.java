package com.example.dawn.seller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.car.activity.MainActivity;

/**
 * Created by WANXIAO on 2015/9/11.
 */
public class PriceActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.sell_price);

        //接收从OrderActivity传送过来的数据并读取和显示数据
        TextView company=(TextView)findViewById(R.id.txt_price_storename);
        TextView model=(TextView)findViewById(R.id.txt_price_carname);
        TextView address =(TextView)findViewById(R.id.txt_price_address2);
        TextView orderNo =(TextView)findViewById(R.id.txt_price_num2);
        TextView orderDa =(TextView)findViewById(R.id.txt_price_time2);
        TextView accessory1=(TextView)findViewById(R.id.table1_2);
        TextView accessory2=(TextView)findViewById(R.id.table2_2);
        TextView accessory3=(TextView)findViewById(R.id.table3_2);
        TextView accessory4=(TextView)findViewById(R.id.table4_2);
        TextView  accessoryNum1 = (TextView) findViewById(R.id.table1_3);
        TextView  accessoryNum2 = (TextView) findViewById(R.id.table2_3);
        TextView  accessoryNum3 = (TextView) findViewById(R.id.table3_3);
        TextView  accessoryNum4 = (TextView) findViewById(R.id.table4_3);
        TextView  accessoryRemark1 = (TextView) findViewById(R.id.table1_4);
        TextView  accessoryRemark2= (TextView) findViewById(R.id.table2_4);
        TextView  accessoryRemark3 = (TextView) findViewById(R.id.table3_4);
        TextView  accessoryRemark4 = (TextView) findViewById(R.id.table4_4);
        TextView demand =(TextView)findViewById(R.id.txt_price_requre2);
        TextView earnest =(TextView)findViewById(R.id.txt_price_dowm2);
        TextView modelyear =(TextView)findViewById(R.id.price_modelyear);

        Intent intent =getIntent();
        final Bundle data=intent.getExtras();
//        //读照片的时候用注意用setImageResource()和getInt()，因为"Image"为int类型
//        image.setImageResource(data.getInt("Image"));
        company.setText(data.getString("CompanyP"));
        model.setText(data.getString("ModelP"));
        address.setText(data.getString("AddressP"));
        orderNo.setText(data.getString("NoP"));
        orderDa.setText(data.getString("DateP"));
        accessory1.setText(data.getString("AccessoryP1"));
        accessory2.setText(data.getString("AccessoryP2"));
        accessory3.setText(data.getString("AccessoryP3"));
        accessory4.setText(data.getString("AccessoryP4"));
        accessoryNum1.setText(data.getString("AccessoryPNum1"));
        accessoryNum2.setText(data.getString("AccessoryPNum2"));
        accessoryNum3.setText(data.getString("AccessoryPNum3"));
        accessoryNum4.setText(data.getString("AccessoryPNum4"));
        accessoryRemark1.setText(data.getString("AccessoryPRemark1"));
        accessoryRemark2.setText(data.getString("AccessoryPRemark2"));
        accessoryRemark3.setText(data.getString("AccessoryPRemark3"));
        accessoryRemark4.setText(data.getString("AccessoryPRemark4"));
        demand.setText(data.getString("DemandP"));
        earnest.setText(data.getString("EarnestP"));
        modelyear.setText(data.getString("ModelYearP"));


        Button btBack=(Button)findViewById(R.id.bn_price_return);
        btBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        Button btprice=(Button)findViewById(R.id.bn_price_offer);
        btprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PriceActivity.this,"上传报价，描述，级别，质量保证等信息（功能待完善）",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(PriceActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
