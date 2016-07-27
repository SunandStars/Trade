package com.example.dawn.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.manage.activity.HttpUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XU_aa on 2015/11/10.
 */
public class ChangePhoneNumber3 extends Activity {
    private  Button btn_checknum;
    private  String checktemp="";
    SharedPreferences sp_ChangePN;
    EditText ed_Check,new_phonenum;
    private String username="";
    private String newphone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_phonenum3);
        new_phonenum=(EditText)findViewById(R.id.new_phonenum3);
        sp_ChangePN=getSharedPreferences("userinf", MODE_PRIVATE);
        ed_Check=(EditText)findViewById(R.id.check_num3);
        btn_checknum=(Button)findViewById(R.id.getcheck_num3);
        btn_checknum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查新手机格式
                if(GetCheckNum()){
                    TimeCount time;
                    time = new TimeCount(60000, 1000);//构造CountDownTimer对象
                    time.start();//开始计时
                    btn_checknum.setClickable(false);
                    new_phonenum.setClickable(false);
                    Toast.makeText(ChangePhoneNumber3.this, "验证码已发送到手机", Toast.LENGTH_SHORT).show();
                }
                else {
                    TimeCount time;
                    time = new TimeCount(30000, 1000);//构造CountDownTimer对象
                    time.start();//开始计时
                    btn_checknum.setClickable(false);
                    Toast.makeText(ChangePhoneNumber3.this,"稍后重新获取",Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button btn_next1=(Button)findViewById(R.id.manage_changenum_submitbtn);
        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checknum=ed_Check.getText().toString();
                if(checktemp.equals("")){
                    Toast.makeText(ChangePhoneNumber3.this,"请获取验证码",Toast.LENGTH_SHORT).show();
                }
                else if(checktemp.equals(checknum)){

                    if(new_phonenum.getText().toString().equals(newphone)){
                        if(ChangePhoneNumber()){
                            finish();
                        } else{
                            Toast.makeText(ChangePhoneNumber3.this,"请重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ChangePhoneNumber3.this,"填写验证码对应手机号",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }



    private boolean ChangePhoneNumber(){
        JSONObject jsonObject;
        username= sp_ChangePN.getString("username", null);
        newphone=new_phonenum.getText().toString();
        try {
            jsonObject=qurry(username,newphone);

            if (jsonObject.getString("reback").equals("修改成功")){

                ////保存新号码到本地SP

                Toast.makeText(ChangePhoneNumber3.this,"修改成功",Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(jsonObject.getString("reback").equals("修改失败")){
                Toast.makeText(ChangePhoneNumber3.this,"请重试",Toast.LENGTH_SHORT).show();
                return false;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private JSONObject qurry(String username,String phonenum)
            throws Exception{
        Map<String,String> map=new HashMap<String,String>();

        map.put("manageStep","修改手机号");
        map.put("username", username);
        map.put("phoneNumber", phonenum);

        String url = HttpUtil.BASE_URL + "manage.jsp";

        return new JSONObject(HttpUtil.postRequest(url, map));
    }



    private boolean GetCheckNum() {
        JSONObject jsonObject;
        username= sp_ChangePN.getString("username", null);
        newphone=new_phonenum.getText().toString();
        try {
            jsonObject=qurry2(username,newphone);
            if (jsonObject.getString("reback").equals("成功发送")){
                checktemp=jsonObject.getString("identyNum");
                ////保存新号码到本地SP
                Toast.makeText(ChangePhoneNumber3.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                return true;
            }
            else
//            if(jsonObject.getString("reback").equals("修改失败"))
            {
//                Toast.makeText(ChangePhoneNumber3.this,"请重试",Toast.LENGTH_SHORT).show();
                return false;
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private JSONObject qurry2(String username,String phonenum)
            throws Exception{
        Map<String,String> map=new HashMap<String,String>();

        map.put("manageStep","修改手机号验证");
        map.put("username", username);
        map.put("phoneNumber", phonenum);

        String url = HttpUtil.BASE_URL + "manage.jsp";

        return new JSONObject(HttpUtil.postRequest(url, map));
    }





    //延迟
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn_checknum.setText("重新验证");
            btn_checknum.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn_checknum.setClickable(false);
            btn_checknum.setText(millisUntilFinished / 1000 + "秒后再尝试");
        }
    }
}
