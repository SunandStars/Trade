package com.example.dawn.car.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.car.activity.MainActivity;
import com.example.dawn.manage.Code;
import com.example.dawn.manage.activity.FindPWDActivity;
import com.example.dawn.manage.activity.HttpUtil;

import com.example.dawn.manage.activity.RegistActivity;
import com.example.dawn.manage.service.UserService;
import com.example.dawn.manage.service.UserServiceImp;
import com.example.dawn.manage.util.Constant;
import com.example.dawn.manage.util.HttpHelper;
import com.example.dawn.realbuyer.activity.BuyMainAcitivity;
import com.example.dawn.realseller.activity.SellMainActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dawn on 2015/9/2.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SetFragment extends Fragment
{
    private static EditText UserNameOrCellPhoneNum;
    private static EditText PassWord;
    private static ImageView iv_showCode;
    private static EditText authcode;

    private static  final int flag_login_success = 1;
    private static ProgressDialog dialog;
    //    private static int Code;
    private UserService userService = new UserServiceImp();
    private String loginName;
    private String password;
    //产生的验证码
    private String realCode;
    private String flag="";

    //创建SharedPreferences本地保存从服务器读取的用户注册信息的数据
    SharedPreferences PreferencesUserInf;
    SharedPreferences.Editor EditorUserInf;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_login,container,false);

        Button btnLogin;
        TextView textView;
        Button btnRegist;
        UserNameOrCellPhoneNum = (EditText)view.findViewById(R.id.LoginName);
        PassWord = (EditText)view.findViewById(R.id.PassWord);
        btnLogin = (Button)view.findViewById(R.id.ButtonforLogin);
        textView = (TextView)view.findViewById(R.id.forgetpwd);
        authcode=(EditText)view.findViewById(R.id.Login_et);
        iv_showCode =(ImageView)view.findViewById(R.id.iv_showCode);
        btnRegist=(Button)view.findViewById(R.id.btnregist);


        //将验证码用图片的形式显示出来
        iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode();
        //点击验证码图片更换验证码
        iv_showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_showCode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode();
            }
        });

//

//        UserNameOrCellPhone.setText(getActivity().getIntent().getStringExtra("cellphone"));
        //Click
      textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                String UserNameOrCellPhoneNumInf = UserNameOrCellPhoneNum.getText().toString();
                Intent intent = new Intent(getActivity(), FindPWDActivity.class);
//                intent.putExtra("usernameorcellphonenum",UserNameOrCellPhoneNumInf);
                startActivity(intent);
                //  finish();
            }
        });


        //点击登录按钮，进行登录操作
        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String phoneCode = authcode.getText().toString();
                //进行不区分大小写的验证码验证
                if(phoneCode.toLowerCase().equals(realCode.toLowerCase())){
//                    Toast.makeText(getActivity(), phoneCode + "验证码正确", Toast.LENGTH_SHORT).show();

                    loginName =  UserNameOrCellPhoneNum.getText().toString();
                    password = PassWord.getText().toString();
                    if (loginName == null || "".equals(loginName))
                    {
                        UserNameOrCellPhoneNum.setHint("用户名不能为空");
                        UserNameOrCellPhoneNum.setFocusable(true);
                        UserNameOrCellPhoneNum.requestFocus();
                        Toast.makeText(getActivity(), phoneCode + "用户名不能为空", Toast.LENGTH_SHORT).show();
                        //textview1.performClick();
                    }
                    else if (password == null || "".equals(password)) {
                        PassWord.setHint("密码不能为空");
                        PassWord.setFocusable(true);
                        PassWord.requestFocus();
                        Toast.makeText(getActivity(), phoneCode + "密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (LoginPro())
                        {
                            if(flag.equals("汽修厂"))
                            {
                                SharedPreferences sharedPreferences;
                                sharedPreferences=getActivity().getSharedPreferences("LoginState",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor;
                                editor=sharedPreferences.edit();
                                editor.putInt("State",1);
                                editor.apply();
                                Intent intent=new Intent(getActivity(),BuyMainAcitivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            else
                            {
                                SharedPreferences sharedPreferences;
                                sharedPreferences=getActivity().getSharedPreferences("LoginState",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor;
                                editor=sharedPreferences.edit();
                                editor.putInt("State",2);
                                editor.apply();
                                Intent intent=new Intent(getActivity(),SellMainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }

                        }

                    }


                }else{
                    Toast.makeText(getActivity(), phoneCode+"验证码错误", Toast.LENGTH_SHORT).show();
                }


            }



        });

        //点击免费注册按钮转到注册界面
        btnRegist.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(getActivity(),RegistActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }



    private Boolean LoginPro()
    {
        // 获取用户输入的用户名、密码、用户类型
        // String Name=name.getText().toString();
        String UserNameOrCellPhoneNumInf = UserNameOrCellPhoneNum.getText().toString();
        String Password=PassWord.getText().toString();
        String LoginType;
        Pattern p=Pattern.compile("[0-9]*");
        Matcher m=p.matcher(UserNameOrCellPhoneNumInf);
        if(m.matches())
        {
           LoginType="手机号";

        }
        else
        {
            LoginType="用户名";
        }



        JSONObject jsonObj;
        try
        {
            jsonObj = query(LoginType, UserNameOrCellPhoneNumInf, Password);
            String reback=jsonObj.getString("reback");

            switch (reback){

                case "成功":
                    PreferencesUserInf=getActivity().getSharedPreferences("userinf", Context.MODE_PRIVATE);
                    EditorUserInf=PreferencesUserInf.edit();
                    Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
                    String UserName=jsonObj.getString("username");
                    String UserType=jsonObj.getString("usertype");
                    String PhoneNumber=jsonObj.getString("phoneNumber");
                    String Company=jsonObj.getString("company");
                    String Point=jsonObj.getString("point");
                    String LegalPersonName=jsonObj.getString("legalpersonname");
                    String Address=jsonObj.getString("address");
                    String AliPayNum=jsonObj.getString("alipaynum");
                    String WeChatNum=jsonObj.getString("weixinnum");
                    String Email=jsonObj.getString("email");
                    String PostNum=jsonObj.getString("postnum");
                    String LisencePic=jsonObj.getString("lisencepic");
                    String CompanyPic=jsonObj.getString("companypic");
                    String QQ=jsonObj.getString("qq");
                    flag=UserType;
//                    Toast.makeText(getActivity(),PostNum,Toast.LENGTH_LONG).show();
                    ////保存用户信息到本地方便读取
                    EditorUserInf.putString("username",UserName );
                    EditorUserInf.putString("usertype",UserType);
                    EditorUserInf.putString("phoneNumber",PhoneNumber );
                    EditorUserInf.putString("company",Company );
                    EditorUserInf.putString("point",Point );
                    EditorUserInf.putString("legalpersonname",LegalPersonName );
                    EditorUserInf.putString("address",Address );
                    EditorUserInf.putString("alipaynum",AliPayNum );
                    EditorUserInf.putString("weixinnum",WeChatNum );
                    EditorUserInf.putString("email",Email );
                    EditorUserInf.putString("postnum",PostNum );
                    EditorUserInf.putString("lisencepic", LisencePic);
                    EditorUserInf.putString("companypic", CompanyPic);
                    EditorUserInf.putString("qq", QQ);
                    EditorUserInf.apply();
                    return true;
                case "用户名不存在":
                    Toast.makeText(getActivity(),"用户名不存在",Toast.LENGTH_LONG).show();

                    return false;
                case "手机号不存在":
                    Toast.makeText(getActivity(),"手机号不存在",Toast.LENGTH_LONG).show();
                    return false;
                case "密码错误":
                    Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_LONG).show();
                    return false;
                case "限制":
                    Toast.makeText(getActivity(),"限制",Toast.LENGTH_LONG).show();
                    return false;
                default:
                    return false;
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private JSONObject query(String LoginType,String UserNameOrCellPhoneNumInf,String Password)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("logintype",  LoginType);

        Pattern p=Pattern.compile("[0-9]*");
        Matcher m=p.matcher(UserNameOrCellPhoneNumInf);
        if(m.matches())
        {
//            String PhoneNum=UserNameOrCellPhoneNumInf;
            map.put("phoneNumber",UserNameOrCellPhoneNumInf);
        }
        else
        {
//            String UserName=UserNameOrCellPhoneNumInf;
            map.put("username",UserNameOrCellPhoneNumInf);
        }

        map.put("pass", Password);


        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "login.jsp";
        // 发送请求

        return new JSONObject(HttpUtil.postRequest(url, map));
    }

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public void showLoginStatus(Integer loginstatus) throws InterruptedException {
//        // Looper.prepare();
//        if(loginstatus.equals(Constant.Fail))
//        {
//            Toast.makeText(getActivity().getApplicationContext(), "用户名密码错误", Toast.LENGTH_LONG).show();
//        }else{
//            if(loginstatus.equals(Constant.UserNameNotExist)){
//                Toast.makeText(getActivity().getApplicationContext(), "用户名不存在", Toast.LENGTH_LONG).show();
//            }else{
//                if(loginstatus.equals(Constant.UnknowError))
//                {
//                    Toast.makeText(getActivity().getApplicationContext(), "未知错误", Toast.LENGTH_LONG).show();
//                }else{
//                    if(loginstatus.equals(Constant.OverTime))
//                    {
//                        Toast.makeText(getActivity().getApplicationContext(), "登陆超时", Toast.LENGTH_LONG).show();
//                    }
//                    else{
//
//                        Toast.makeText(getActivity().getApplicationContext(), "登陆成功", Toast.LENGTH_LONG).show();
//
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        intent.putExtra("username", loginName);
//                        startActivity(intent);
////                        finish();
//                    }
//                }
//            }
//        }
//        Looper.loop();
//    }

}
