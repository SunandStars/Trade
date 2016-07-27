package com.example.dawn.realbuyer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.manage.activity.HttpUtil;
import com.example.dawn.manage.bean.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanxiao on 2015/12/10.
 */
public class ReBuyEnquiryInfActivity extends Activity
{
    String UserName;
    String OrderNum;
    String State;
    String Reback;
    String BuyerUserName;
    String LowMoney;
    String BuyerCompany;
    String CarType;
    String OrderState;
    String OrderTime;
    String Address;
    String RecPerson;
    String RecPersonNum;
    String PostNum;
    String OrderDetail;
    String Demand;
    String CarYear;
    String QuoteNum;
    String BuyerCompanyPic;
    String CarDisPic;
    String NormalPicNum;
    String NormalPic0;
    String NormalPic1;
    TextView BuyerCompany_tv;
    TextView UserName_tv;
    TextView CarType_tv;
    TextView QuoteNum_tv;
    TextView LowMoney_tv;
    TextView OrderState_tv;
    TextView OrderNum_tv;
    TextView OrderTime_tv;
    TextView CarYear_tv;
    TextView RecPerson_tv;
    TextView RecPersonNum_tv;
    TextView PostNum_tv;
    TextView Address_tv;
    TextView Demand_tv;
    ImageView Company_iv;
    ImageView iv1;
    ImageView iv2;
    ImageView iv3;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_xunjiazhong);
        Intent intent=getIntent();
        final Bundle data=intent.getExtras();
        UserName=data.getString("Username");
        OrderNum=data.getString("OrderNum");
        State="询价中";
        BuyerCompany_tv=(TextView)findViewById(R.id.txt_offer_storename);
        UserName_tv=(TextView)findViewById(R.id.txt_offer_username);
        OrderState_tv=(TextView)findViewById(R.id.txt_price_orderstatus2);
        OrderNum_tv=(TextView)findViewById(R.id.contatc_number);
        OrderTime_tv=(TextView)findViewById(R.id.contatc_time);
        CarType_tv=(TextView)findViewById(R.id.txt_offer_carname);
        CarYear_tv=(TextView)findViewById(R.id.txt_offer_caryear);
        QuoteNum_tv=(TextView)findViewById(R.id.contatc_hum);
        LowMoney_tv=(TextView)findViewById(R.id.contatc_lowmoney);
        RecPerson_tv=(TextView)findViewById(R.id.contact_name);
        RecPersonNum_tv=(TextView)findViewById(R.id.contatc_phone);
        PostNum_tv=(TextView)findViewById(R.id.contact_post);
        Address_tv=(TextView)findViewById(R.id.contatc_address);
        Demand_tv=(TextView)findViewById(R.id.contact_postscript);



        JSONObject jsonObj;
        try
        {
            jsonObj = query(UserName,OrderNum,State);
            Reback=jsonObj.getString("reback");

            switch (Reback){

                case "成功":
                {Toast.makeText(ReBuyEnquiryInfActivity.this, "请求发送数据成功", Toast.LENGTH_SHORT).show();
                    BuyerUserName=jsonObj.getString("buyerusername");
                    BuyerCompany=jsonObj.getString("buyercompany");
                    CarType=jsonObj.getString("cartype");
                    LowMoney=jsonObj.getString("lowmoney");
                    OrderState=jsonObj.getString("orderstate");
                    OrderTime=jsonObj.getString("ordertime");
                    OrderNum=jsonObj.getString("ordernum");
                    Address=jsonObj.getString("address");
                    RecPerson=jsonObj.getString("recperson");
                    RecPersonNum=jsonObj.getString("recpersonnum");
                    PostNum=jsonObj.getString("postnum");
                    OrderDetail=jsonObj.getString("orderdetail");
                    Demand=jsonObj.getString("demand");
                    CarYear=jsonObj.getString("caryear");
                    QuoteNum=jsonObj.getString("quotenum");
                    BuyerCompanyPic=jsonObj.getString("buyercompanypic");
                    CarDisPic=jsonObj.getString("carDisPic");
                    NormalPicNum=jsonObj.getString("normalPicNum");
                    NormalPic0=jsonObj.getString("normalpic0");
                    NormalPic1=jsonObj.getString("normalpic1");}
                break;



                case "指令为空":
                    Toast.makeText(ReBuyEnquiryInfActivity.this,"接收服务器数据失败",Toast.LENGTH_LONG).show();
                    break;

                default:

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }


    private JSONObject query(String UserName,String OrderNum,String State)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("username",UserName);
        map.put("ordernum",OrderNum);
        map.put("state",  State);



        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "buyerorderdetail.jsp";
        // 发送请求

        return new JSONObject(HttpUtil.postRequest(url, map));
    }

}
