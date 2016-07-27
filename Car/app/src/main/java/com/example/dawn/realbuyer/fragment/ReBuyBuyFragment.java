package com.example.dawn.realbuyer.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.dawn.buyer.activity.BuyGoodsInfActivity;
import com.example.dawn.buyer.activity.BuyOrderActivity;
import com.example.dawn.buyer.activity.WriteOrderActivity;
import com.example.dawn.car.R;
import com.example.dawn.car.domain.MyOpenHelper;
import com.example.dawn.car.domain.RefreshableView;
import com.example.dawn.manage.activity.HttpUtil;
import com.example.dawn.realbuyer.activity.ReBuyEnquiryInfActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanxiao on 2015/11/6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ReBuyBuyFragment extends Fragment
{
//    //将外部数据库插入apk时定义常量值
//    private final static String DATABASE_PATH = android.os.Environment
//            .getExternalStorageDirectory().getAbsolutePath() + "/buyerlist";
//
//    private final static String DATABASE_FILENAME = "buyer.db";
//    private StringBuilder sb;
    SharedPreferences preferencesuserinf;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button enquiry;
    Button trading;
    Button checked;
    Button rejected;
    String UserName;
    String Company;
    String Page;
    String PageSize;
    String OrderState;
    String CarType;
    String OrderTime;
    String QuoteNum;
    String Low;
    String Detail;
    String OrderNum;
    JSONArray jsonArray;
    String[] strings_all={};
    private String[] strings1;
    private String[] strings2;
    private String[] strings3;
    String Goods;

    RefreshableView refreshableView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.buy_list, container, false);
        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
        preferencesuserinf = getActivity().getSharedPreferences("userinf", Context.MODE_PRIVATE);
        UserName = preferencesuserinf.getString("username", null);
        Company=preferencesuserinf.getString("company",null);
        Page = "1";
        OrderState = "询价中";
        final List<Map<String,Object>> ListItems = new ArrayList<Map<String,Object>>();

        //向服务器发送请求

        try
        {
            jsonArray = query(UserName, Page, PageSize, OrderState);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




            //从JSONArray获取JSON后放到ListView
            for (int numsize=0;numsize<jsonArray.length();numsize++)
         {
            try
            {
                JSONObject jsonObject= jsonArray.getJSONObject(numsize);
                CarType=jsonObject.getString("cartype");
                OrderTime=jsonObject.getString("ordertime");
                QuoteNum=jsonObject.getString("quotenum");
                Low=jsonObject.getString("low");
                Detail=jsonObject.getString("detail");
                OrderNum=jsonObject.getString("ordernum");
                UserName = preferencesuserinf.getString("username", null);
                Company=preferencesuserinf.getString("company",null);

                strings_all=Detail.split("/");
                int num=strings_all.length;
                strings1=new String[num];
                strings2=new String[num];
                strings3=new String[num];
                for(int i=0;i<num;i++)
                {
                    String[] a=strings_all[i].split("<");
                    String[] b=a[1].split(">");

                    strings1[i]=a[0];
                    strings2[i]=b[0];
                    strings3[i]=b[1];
                    Goods=strings1[i]+"*"+strings2[i]+"/";
                }

                Map<String,Object> listItem=new HashMap<String, Object>();

                listItem.put("company",Company);
                listItem.put("cartype", CarType);
                listItem.put("ordertime",OrderTime);
                listItem.put("quotenum",QuoteNum);
                listItem.put("low",Low);
                listItem.put("goods",Goods);
                listItem.put("ordernum",OrderNum);
                ListItems.add(listItem);

            }

            catch (Exception e)
            {
            e.printStackTrace();
            }
//                numsize--;
         }


        //将读取出的数据利用SimpleAdapter加载到list里面
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                ListItems,
                R.layout.fragment_buy,
                new String[]{"company","cartype","ordertime","quotenum", "low","goods"},
                new int[]{R.id.company_tv,R.id.model_tv,R.id.orderDate_tv,R.id.model_number,
                        R.id.orderDate_lowest,R.id.accessory_tv}
        );
        final ListView list =(ListView) view.findViewById(R.id.mylist);//注意加view!!!!!!!!!!!!!
        list.setAdapter(simpleAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) list.getItemAtPosition(position);
                String OrderNum=map.get("ordernum");


                Intent intent = new Intent(getActivity(), ReBuyEnquiryInfActivity.class);

                intent.putExtra("UserName",UserName);
                intent.putExtra("OrderNum",OrderNum);


                startActivity(intent);


            }
        });

        //设置刷新功能
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);


        Button btfillorder=(Button)view.findViewById(R.id.bt_fillorder);
        btfillorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),WriteOrderActivity.class);
                startActivity(intent);
            }
        });


        enquiry=(Button)view.findViewById(R.id.buyer_enquiry);
        trading=(Button)view.findViewById(R.id.buyer_trading);
        checked=(Button)view.findViewById(R.id.buyer_Checked);
        rejected=(Button)view.findViewById(R.id.buyer_Rejected);

        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), BuyGoodsInfActivity.class);
                intent.putExtra("fragid",1);
                startActivity(intent);

            }
        });
        trading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), BuyGoodsInfActivity.class);
                intent.putExtra("fragid",2);
                startActivity(intent);
            }
        });
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BuyGoodsInfActivity.class);
                intent.putExtra("fragid", 3);
                startActivity(intent);
            }
        });
        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BuyGoodsInfActivity.class);
                intent.putExtra("fragid", 4);
                startActivity(intent);
            }
        });



////
//        //读取数据库里的内容
//        MyOpenHelper oh=new MyOpenHelper(getActivity(),"buyerlist.db",null,1);
//
//        SQLiteDatabase db=oh.getWritableDatabase();
//        //查询获得Cursor
//        Cursor cursor=db.query("BuyerDetails", null, null, null, null, null, null);
//
//        cursor.moveToLast();
//        while ( cursor.moveToPrevious())
//        {
//            cursor.moveToNext();
//
////            address[i]=cursor.getString(1);
////            model[i]=cursor.getString(2);
////            company[i] =cursor.getString(3);
////            orderNo[i]=cursor.getString(4);
////            orderDate[i]=cursor.getString(5);
////            accessory[i]=cursor.getString(6);
//            String address=cursor.getString(1);
//            String model=cursor.getString(2);
//            String company =cursor.getString(3);
//            String orderNo=cursor.getString(4);
//            String orderDate=cursor.getString(5);
//            String accessory1=cursor.getString(6);
//            String accessory2=cursor.getString(7);
//            String accessory3=cursor.getString(8);
//            String accessory4=cursor.getString(9);
//            String accessorynum1=cursor.getString(10);
//            String accessorynum2=cursor.getString(11);
//            String accessorynum3=cursor.getString(12);
//            String accessorynum4=cursor.getString(13);
//            String accessoryremark1=cursor.getString(14);
//            String accessoryremark2=cursor.getString(15);
//            String accessoryremark3=cursor.getString(16);
//            String accessoryremark4=cursor.getString(17);
//            String earnest=cursor.getString(18);
//            String demand=cursor.getString(19);
//            String modelyear=cursor.getString(20);
//
//
//
//            Map<String,Object> listItem1=new HashMap<String, Object>();
//
//            listItem1.put("buyerAddress",address);
//            listItem1.put("buyerModel", model);
//            listItem1.put("buyerCompany",company);
//            listItem1.put("buyerOrderNo",orderNo);
//            listItem1.put("buyerOrderDate", orderDate);
//            listItem1.put("buyerAccessory1", accessory1);
//            listItem1.put("buyerAccessory2", accessory2);
//            listItem1.put("buyerAccessory3", accessory3);
//            listItem1.put("buyerAccessory4", accessory4);
//            listItem1.put("buyerAccessoryNum1", accessorynum1);
//            listItem1.put("buyerAccessoryNum2", accessorynum2);
//            listItem1.put("buyerAccessoryNum3", accessorynum3);
//            listItem1.put("buyerAccessoryNum4", accessorynum4);
//            listItem1.put("buyerAccessoryRemark1", accessoryremark1);
//            listItem1.put("buyerAccessoryRemark2", accessoryremark2);
//            listItem1.put("buyerAccessoryRemark3", accessoryremark3);
//            listItem1.put("buyerAccessoryRemark4", accessoryremark4);
//            listItem1.put("buyerDemand", demand);
//            listItem1.put("buyerEarnest", earnest);
//            listItem1.put("buyerModelYear",modelyear);
//
//
//
////            listItem.put("buyerAddress",address[i]);
////            listItem.put("buyerModel",model[i]);
////            listItem.put("buyerCompany",company[i]);
////            listItem.put("buyerOrderNo",orderNo[i]);
////            listItem.put("buyerOrderDate", orderDate[i]);
////            listItem.put("buyerAccessory", accessory[i]);
////            i++;
//
//            listItems1.add(listItem1);
//            cursor.moveToPrevious();
//        }
//        cursor.close();
//
//
//        //查询读取外部sqlitie的.db文件
//        sb=new StringBuilder();
////        MyOpenHelper oh=new MyOpenHelper(getActivity(),"buyerlist.db",null,1);
//
//        SQLiteDatabase db1= openDatabase();
//        //查询获得Cursor
//        Cursor cursor1=db1.query("BuyerDetails", null, null, null, null, null, null);
//
////        int i=0;
//        cursor1.moveToLast();
//        while ( cursor1.moveToPrevious())
//        {
//            cursor1.moveToNext();
//
//            String address=cursor1.getString(1);
//            String model=cursor1.getString(2);
//            String company =cursor1.getString(3);
//            String orderNo=cursor1.getString(4);
//            String orderDate=cursor1.getString(5);
//            String accessory1=cursor1.getString(6);
//            String accessory2=cursor1.getString(7);
//            String accessory3=cursor1.getString(8);
//            String accessory4=cursor1.getString(9);
//            String accessorynum1=cursor1.getString(10);
//            String accessorynum2=cursor1.getString(11);
//            String accessorynum3=cursor1.getString(12);
//            String accessorynum4=cursor1.getString(13);
//            String accessoryremark1=cursor1.getString(14);
//            String accessoryremark2=cursor1.getString(15);
//            String accessoryremark3=cursor1.getString(16);
//            String accessoryremark4=cursor1.getString(17);
//            String earnest=cursor1.getString(18);
//            String demand=cursor1.getString(19);
//            String modelyear=cursor1.getString(20);
//
//            //将外部的.db文件中的数据（表）拼接在内部输入的数据（表）后面
//            Map<String,Object> listItem1 =new HashMap<String, Object>();
//
//            listItem1.put("buyerAddress",address);
//            listItem1.put("buyerModel", model);
//            listItem1.put("buyerCompany",company);
//            listItem1.put("buyerOrderNo",orderNo);
//            listItem1.put("buyerOrderDate", orderDate);
//            listItem1.put("buyerAccessory1", accessory1);
//            listItem1.put("buyerAccessory2", accessory2);
//            listItem1.put("buyerAccessory3", accessory3);
//            listItem1.put("buyerAccessory4", accessory4);
//            listItem1.put("buyerAccessoryNum1", accessorynum1);
//            listItem1.put("buyerAccessoryNum2", accessorynum2);
//            listItem1.put("buyerAccessoryNum3", accessorynum3);
//            listItem1.put("buyerAccessoryNum4", accessorynum4);
//            listItem1.put("buyerAccessoryRemark1", accessoryremark1);
//            listItem1.put("buyerAccessoryRemark2", accessoryremark2);
//            listItem1.put("buyerAccessoryRemark3", accessoryremark3);
//            listItem1.put("buyerAccessoryRemark4", accessoryremark4);
//            listItem1.put("buyerDemand", demand);
//            listItem1.put("buyerEarnest", earnest);
//            listItem1.put("buyerModelYear",modelyear);
//
//
//
////            listItem.put("buyerAddress",address[i]);
////            listItem.put("buyerModel",model[i]);
////            listItem.put("buyerCompany",company[i]);
////            listItem.put("buyerOrderNo",orderNo[i]);
////            listItem.put("buyerOrderDate", orderDate[i]);
////            listItem.put("buyerAccessory", accessory[i]);
////            i++;
//
//            listItems1.add(listItem1);
//            cursor1.moveToPrevious();
//        }
//        cursor1.close();





//        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            // 第position项被单击时激发该方法
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Intent intent = new Intent(getActivity(), WriteOrderActivity.class);
//
//
//                startActivity(intent);
//
//
//            }
//        });



//        //将读取到的数据传递给OrderActivity
//        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            // 第position项被单击时激发该方法
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                HashMap<String, String> map = (HashMap<String, String>) list1.getItemAtPosition(position);
//                String address=map.get("buyerAddress");
//                String company =map.get("buyerCompany");
//                String model=map.get("buyerModel");
//                String orderNo=map.get("buyerOrderNo");
//                String orderDate=map.get("buyerOrderDate");
//                String accessory1=map.get("buyerAccessory1");
//                String accessory2=map.get("buyerAccessory2");
//                String accessory3=map.get("buyerAccessory3");
//                String accessory4=map.get("buyerAccessory4");
//                String accessorynum1=map.get("buyerAccessoryNum1");
//                String accessorynum2=map.get("buyerAccessoryNum2");
//                String accessorynum3=map.get("buyerAccessoryNum3");
//                String accessorynum4=map.get("buyerAccessoryNum4");
//                String accessoryremark1=map.get("buyerAccessoryRemark1");
//                String accessoryremark2=map.get("buyerAccessoryRemark2");
//                String accessoryremark3=map.get("buyerAccessoryRemark3");
//                String accessoryremark4=map.get("buyerAccessoryRemark4");
//                String earnest=map.get("buyerEarnest");
//                String demand =map.get("buyerDemand");
//                String modelYear=map.get("buyerModelYear");
//
//                Intent intent = new Intent(getActivity(), BuyOrderActivity.class);
//
//
////               intent.putExtra("Image", address);
//                intent.putExtra("Company", company);
//                intent.putExtra("Date", orderDate);
//                intent.putExtra("Model", model);
//                intent.putExtra("Address", address);
//                intent.putExtra("No", orderNo);
//                intent.putExtra("Accessory1",accessory1);
//                intent.putExtra("Accessory2",accessory2);
//                intent.putExtra("Accessory3",accessory3);
//                intent.putExtra("Accessory4",accessory4);
//                intent.putExtra("AccessoryNum1",accessorynum1);
//                intent.putExtra("AccessoryNum2",accessorynum2);
//                intent.putExtra("AccessoryNum3",accessorynum3);
//                intent.putExtra("AccessoryNum4",accessorynum4);
//                intent.putExtra("AccessoryRemark1",accessoryremark1);
//                intent.putExtra("AccessoryRemark2",accessoryremark2);
//                intent.putExtra("AccessoryRemark3",accessoryremark3);
//                intent.putExtra("AccessoryRemark4",accessoryremark4);
//                intent.putExtra("Earnest", earnest);
//                intent.putExtra("Demand", demand);
//                intent.putExtra("ModelYear", modelYear);
//
//                startActivity(intent);
//
//
//            }
//        });



        return view;
    }

    private JSONArray query(String UserName,String Page,String PageSize,String OrderState)
            throws Exception
    {

        PageSize="8";
        Map<String, String> map = new HashMap<>();
        map.put("username",  UserName);
        map.put("page", Page);
        map.put("pagesize",PageSize);
        map.put("orderstate", OrderState);


        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "buyerorderlist.jsp";
        // 发送请求

        return new JSONArray(HttpUtil.postRequest(url, map));
    }



//    private SQLiteDatabase openDatabase() {
//        try {
//            Log.e("loacat", "DATABASE_PATH=" + DATABASE_PATH);
//            sb.append("DATABASE_PATH=" + DATABASE_PATH+"\n");
//            // 获得dictionary.db文件的绝对路径
//            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
//            File dir = new File(DATABASE_PATH);
//
//            if (!dir.exists())
//                dir.mkdir();
//
//            if (!(new File(databaseFilename)).exists()) {
//                // 获得封装dictionary.db文件的InputStream对象
//                InputStream is = getResources().openRawResource(R.raw.buyer);
//                FileOutputStream fos = new FileOutputStream(databaseFilename);
//                byte[] buffer = new byte[8192];
//                int count = 0;
//
//                while ((count = is.read(buffer)) > 0) {
//                    fos.write(buffer, 0, count);
//                }
//
//                fos.close();
//                is.close();
//            }
//
//            // 打开/sdcard/dictionary目录中的db文件
//            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
//                    databaseFilename, null);
//            return database;
//
//        } catch (Exception e) {
////            showToast("出错" + e.getMessage());
//            Log.e("loacat", "出错" + e.getMessage());
//        }
//
//        return null;
//    }

}

