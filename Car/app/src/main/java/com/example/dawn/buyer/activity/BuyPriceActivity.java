package com.example.dawn.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.dawn.car.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanxiao on 2015/9/25.
 */
public class BuyPriceActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //this. requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏

        setContentView(R.layout.buyprice_offeredprice);
//        //        ImageView image = (ImageView)findViewById(R.id.order_picture);
//        TextView company=(TextView)findViewById(R.id.txt_baojia_distributor);
//        TextView model=(TextView)findViewById(R.id.txt_offer_carname);
//        TextView address =(TextView)findViewById(R.id.txt_offer_address2);
//        TextView orderNo =(TextView)findViewById(R.id.txt_offer_num2);
//        TextView orderDa =(TextView)findViewById(R.id.txt_offer_time2);
//        TextView accessory=(TextView)findViewById(R.id.txt_offer_accessories2);
//        TextView earnest=(TextView)findViewById(R.id.txt_offer_dowm2);
//        TextView demand=(TextView)findViewById(R.id.txt_offer_requre1);

//        Intent intent =getIntent();
//        final Bundle data=intent.getExtras();
//        //读照片的时候用注意用setImageResource()和getInt()，因为"Image"为int类型
//        image.setImageResource(data.getInt("Image"));
       // company.setText(data.getString("Company"));
//        model.setText(data.getString("Model"));
//        address.setText(data.getString("Address"));
//        orderNo.setText(data.getString("No"));
//        orderDa.setText(data.getString("Date"));
//        accessory.setText(data.getString("Accessory"));
//        earnest.setText(data.getString("Earnest"));
//        demand.setText(data.getString("Demand"));

//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        ListView listView = (ListView)findViewById(R.id.list_baojia_list1);
//        Map<String ,Object> ListItem=new HashMap<String, Object>();
//        ListItem.put("Company",data.getString("Company"));
//        list.add(ListItem);
//        SimpleAdapter adapter = new SimpleAdapter(BuyPriceActivity.this
//                ,list ,
//                R.layout.buy_offeredprice_item, new String[] { "Company"}
//                , new int[] {R.id.txt_baojia_distributor});
//        // 填充ListView
//        listView.setAdapter(adapter);
        Button btback=(Button)findViewById(R.id.bn_return_baojia);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
