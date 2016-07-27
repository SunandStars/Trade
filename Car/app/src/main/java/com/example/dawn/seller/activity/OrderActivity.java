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

/**
 * Created by WANXIAO on 2015/9/10.
 */
public class OrderActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
      // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.sell_order);



        //接收从SellFragment传送过来的数据并读取和显示数据
//        ImageView image = (ImageView)findViewById(R.id.order_picture);
        TextView company=(TextView)findViewById(R.id.txt_offer_storename);
        TextView model=(TextView)findViewById(R.id.txt_offer_carname);
//        TextView address =(TextView)findViewById(R.id.txt_offer_address2);
//        TextView orderNo =(TextView)findViewById(R.id.txt_offer_num2);
//        TextView orderDa =(TextView)findViewById(R.id.txt_offer_time2);
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
        TextView earnest=(TextView)findViewById(R.id.orderMoney_tv2);
//        TextView demand=(TextView)findViewById(R.id.txt_offer_requre2);


        Intent intent =getIntent();
        final Bundle data=intent.getExtras();
//        //读照片的时候用注意用setImageResource()和getInt()，因为"Image"为int类型
//        image.setImageResource(data.getInt("Image"));
        company.setText(data.getString("Company"));
        model.setText(data.getString("Model"));
//        address.setText(data.getString("Address"));
//        orderNo.setText(data.getString("No"));
//        orderDa.setText(data.getString("Date"));
        accessory1.setText(data.getString("Accessory1"));
        accessory2.setText(data.getString("Accessory2"));
        accessory3.setText(data.getString("Accessory3"));
        accessory4.setText(data.getString("Accessory4"));
        accessoryNum1.setText(data.getString("AccessoryNum1"));
        accessoryNum2.setText(data.getString("AccessoryNum2"));
        accessoryNum3.setText(data.getString("AccessoryNum3"));
        accessoryNum4.setText(data.getString("AccessoryNum4"));
        accessoryRemark1.setText(data.getString("AccessoryRemark1"));
        accessoryRemark2.setText(data.getString("AccessoryRemark2"));
        accessoryRemark3.setText(data.getString("AccessoryRemark3"));
        accessoryRemark4.setText(data.getString("AccessoryRemark4"));
        String ordermoney =data.getString("Earnest");

        int i=Integer.parseInt(ordermoney);
        if (i>0)
        {
            earnest.setText("是");
        }
        else
        {
            earnest.setText("否");
        }
//        demand.setText(data.getString("Demand"));



        //将从SellFragment接收到的数据传递到PriceActivity中
        final Button btorder=(Button)findViewById(R.id.bn_offer_offer);
        btorder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(OrderActivity.this, PriceActivity.class);
//

//               // intent.putExtra("Image", address);
                intent.putExtra("AddressP",data.getString("Address"));
                intent.putExtra("CompanyP",data.getString("Company"));
                intent.putExtra("ModelP",data.getString("Model"));
                intent.putExtra("NoP",data.getString("No"));
                intent.putExtra("DateP",data.getString("Date"));
                intent.putExtra("AccessoryP1",data.getString("Accessory1"));
                intent.putExtra("AccessoryP2",data.getString("Accessory2"));
                intent.putExtra("AccessoryP3",data.getString("Accessory3"));
                intent.putExtra("AccessoryP4",data.getString("Accessory4"));
                intent.putExtra("AccessoryPNum1",data.getString("AccessoryNum1"));
                intent.putExtra("AccessoryPNum2",data.getString("AccessoryNum2"));
                intent.putExtra("AccessoryPNum3",data.getString("AccessoryNum3"));
                intent.putExtra("AccessoryPNum4",data.getString("AccessoryNum4"));
                intent.putExtra("AccessoryPRemark1",data.getString("AccessoryRemark1"));
                intent.putExtra("AccessoryPRemark2",data.getString("AccessoryRemark2"));
                intent.putExtra("AccessoryPRemark3",data.getString("AccessoryRemark3"));
                intent.putExtra("AccessoryPRemark4",data.getString("AccessoryRemark4"));
                intent.putExtra("EarnestP",data.getString("Earnest"));
                intent.putExtra("DemandP",data.getString("Demand"));
                intent.putExtra("ModelYearP",data.getString("ModelYear"));


                startActivity(intent);


            }
        });

        final Button btback=(Button)findViewById(R.id.bn_offer_return);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
