package com.example.dawn.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.common.MapCodeCovert;
import com.example.dawn.manage.activity.HttpUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanxiao on 2015/11/10.
 */
public class ChangePassword extends Activity
{

    EditText oldpassword;
    EditText newpassword;
    EditText checkpassword;
    String OldPassword;
    String NewPassword;
    String CheckPassword;
    Button ChangeFinishBtn;
    SharedPreferences preferenceschangepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        oldpassword=(EditText)findViewById(R.id.old_password);
        newpassword=(EditText)findViewById(R.id.new_password);
        checkpassword=(EditText)findViewById(R.id.check_password);
        ChangeFinishBtn=(Button)findViewById(R.id.change_finish_btn);

//        OldPassword=oldpassword.getText().toString();
//        NewPassword=newpassword.getText().toString();
//        CheckPassword=checkpassword.getText().toString();

        ChangeFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OldPassword=oldpassword.getText().toString();
                NewPassword=newpassword.getText().toString();
                CheckPassword=checkpassword.getText().toString();

                if(NewPassword.equals(OldPassword))
                {

                   Toast.makeText(getApplicationContext(),"新密码不能与旧密码相同，请重新更改密码",Toast.LENGTH_LONG).show();
                }
                else if(!NewPassword.equals(CheckPassword))
                {
                    Toast.makeText(getApplicationContext(),"两次输入的密码不一致",Toast.LENGTH_LONG).show();
                }
                else if(ChangePassword())
                {
                    finish();
                }
            }
        });



    }

    private Boolean  ChangePassword()
    {
        // 获取用户修改后的信息
        String manageStep="修改密码";
        String OldPassword=oldpassword.getText().toString();
        String NewPassword=newpassword.getText().toString();
        //读取userinf文件中的数据
        preferenceschangepassword=this.getSharedPreferences("userinf", Context.MODE_PRIVATE);
        String UserName=preferenceschangepassword.getString("username", null);

        JSONObject jsonObj;
        try
        {
            jsonObj = query(manageStep, OldPassword,NewPassword,CheckPassword,UserName);

            Toast.makeText(getApplicationContext(),"收的的回调码："+jsonObj.getString("reback"),Toast.LENGTH_LONG).show();

            if (jsonObj.getString("reback") .equals("旧密码错误"))
            {
                Toast.makeText(getApplicationContext(),"旧密码错误",Toast.LENGTH_LONG).show();
                return false;
            }
            else if(jsonObj.getString("reback").equals("用户不存在"))
            {
                Toast.makeText(getApplicationContext(),"用户不存在",Toast.LENGTH_LONG).show();
                return false;
            }
            else
            {
                Toast.makeText(getApplicationContext(),"密码修改成功",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    // 对用户输入的用户名、密码进行校验
    // 定义发送请求的方法
    private JSONObject query(String manageStep,String OldPassword,String NewPassword,String CheckPassword,String UserName)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();

        map.put("manageStep", manageStep);
        map.put("username",UserName);
        map.put("oldpass",OldPassword);
        map.put("newpass",NewPassword);



        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "manage.jsp";
        // 发送请求
//        Toast.makeText(getApplicationContext(),HttpUtil.postRequest(url, map),Toast.LENGTH_SHORT).show();
        return new JSONObject(HttpUtil.postRequest(url, map));
    }
}
