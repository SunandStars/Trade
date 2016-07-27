package com.example.dawn.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.manage.activity.HttpUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by XU_aa on 2015/11/10.
 */
public class ChangePhoneNumber1 extends Activity{
    private String checktemp="";
    private Button btn_checknum;
    SharedPreferences sp_ChangePN;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_phonenum1);
        TextView old_phone=(TextView)findViewById(R.id.old_phonenum1);
//        sharedPreferences=getSharedPreferences("userinf",MODE_PRIVATE);
        sp_ChangePN=getSharedPreferences("userinf", MODE_PRIVATE);

        String phone_temp=sp_ChangePN.getString("phoneNumber", null);
        String invisible_pn = phone_temp.substring(0,3)+"****"+phone_temp.substring(7,phone_temp.length());
        old_phone.setText(invisible_pn);

        final EditText ed_Check=(EditText)findViewById(R.id.check_num1);
        btn_checknum=(Button)findViewById(R.id.getcheck_num1);
        btn_checknum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChangePN()){
                    TimeCount time;
                    time = new TimeCount(60000, 1000);//构造CountDownTimer对象
                    time.start();//开始计时
                    btn_checknum.setClickable(false);
                    Toast.makeText(ChangePhoneNumber1.this,"验证码已发送到手机",Toast.LENGTH_SHORT).show();
                }
                else {
                    TimeCount time;
                    time = new TimeCount(3000, 1000);//构造CountDownTimer对象
                    time.start();//开始计时
                    btn_checknum.setClickable(false);
                    Toast.makeText(ChangePhoneNumber1.this,"稍后重新获取",Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button btn_next1=(Button)findViewById(R.id.manage_changenum_nextbtn1);
        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checknum=ed_Check.getText().toString();
                if(checktemp.equals("")){
                    Toast.makeText(ChangePhoneNumber1.this,"请获取验证码",Toast.LENGTH_SHORT).show();
                }
                else if(checktemp.equals(checknum)){
                    Intent intent=new Intent(ChangePhoneNumber1.this,ChangePhoneNumber3.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(ChangePhoneNumber1.this,"填写正确验证码",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean ChangePN(){


        String username = sp_ChangePN.getString("username", null);
        Toast.makeText(ChangePhoneNumber1.this,username,Toast.LENGTH_SHORT).show();
        JSONObject jsonObject;
        try {
            jsonObject=qurry(username);
            if (jsonObject.getString("reback").equals("成功发送")){
                checktemp=jsonObject.getString("identyNum");
                Toast.makeText(ChangePhoneNumber1.this,"请验证新手机号",Toast.LENGTH_SHORT).show();
                return true;
            }
            else if(jsonObject.getString("reback").equals("用户不存在")){
                Toast.makeText(ChangePhoneNumber1.this,"错误",Toast.LENGTH_SHORT).show();
                return false;
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private JSONObject qurry(String username)
    throws Exception{
        Map<String,String> map=new HashMap<String,String>();

        map.put("manageStep","验证码");
        map.put("username",username);

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
