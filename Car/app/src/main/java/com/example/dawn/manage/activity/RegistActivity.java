package com.example.dawn.manage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.manage.service.UserService;
import com.example.dawn.manage.service.UserServiceImp;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import org.crazyit.auction.client.util.DialogUtil;
//import org.crazyit.auction.client.util.HttpUtil;

public class RegistActivity extends Activity
{
    // 定义界面中两个文本框
//    private String url1=HttpUtil.BASE_URL+"login.jsp";
//    private String registerStep="验证码";
    private String checktemp;
    private UserService userService = new UserServiceImp();
    EditText etnumber, etPass,etRoute,etchecked;
    private TimeCount time;
    private Button checking;
    // 定义界面中两个按钮
    Button bnget, bnLogin;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regeist1);
        // 获取界面中两个编辑框
        etnumber = (EditText) findViewById(R.id.editText);
        etchecked=(EditText) findViewById(R.id.editText5);
        // 获取界面中的两个按钮
        bnget = (Button) findViewById(R.id.button);
        bnLogin=(Button) findViewById(R.id.button4);
        // 为bnCancal按钮的单击事件绑定事件监听器
        bnget.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final String username = etnumber.getText().toString();
                // String number = etnumber.getText().toString();
                if ( "".equals(username)) {
                    etnumber.setHint("电话号码不能为空");
                    etnumber.setFocusable(true);
                    etnumber.requestFocus();
                }
                else
                {
                    if (!userService.checkmobnum(username))
                    {
                        Toast.makeText(getApplicationContext(),"请正确输入手机号",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        loginPro();

                        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
                        checking = (Button) findViewById(R.id.button);


                        time.start();//开始计时
                        checking.setClickable(false);
                    }
                    // Toast.makeText(getApplicationContext(),"444444",Toast.LENGTH_LONG).show();
                }
            }
        });
        bnLogin.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String cellphonenum = etnumber.getText().toString();
                String checkword= etchecked.getText().toString();
                if ("".equals(cellphonenum)) {
                    etnumber.setHint("电话号码不能为空");
                    etnumber.setFocusable(true);
                    etnumber.requestFocus();
                    //textview1.performClick();
                }else {
                    if (!userService.checkmobnum(cellphonenum)){
                        Toast.makeText(v.getContext(), "请输入正确的手机号", Toast.LENGTH_LONG).show();
                    }
                    else {

                        //判断是否收到验证码，防止未点击获取验证码就点击注册按钮
                        if(checktemp!=null)
                        {
                            if (checkword.equals("") || "".equals(checkword)||checktemp.equalsIgnoreCase(null))
                            {
//                                etchecked.setEnabled(false);
                                etchecked.setHint("验证码不能为空");
                                etchecked.setFocusable(true);
                                etchecked.requestFocus();
                                Toast.makeText(getApplicationContext(),"请等待接收验证码",Toast.LENGTH_SHORT).show();
                            }

                            else
                            {


                                etchecked.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "验证码是：" + checktemp + "输入的是：" + checkword, Toast.LENGTH_LONG).show();
                                if (checktemp.equalsIgnoreCase(checkword)) {
                                    Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_LONG).show();
                                    Intent intent1=new Intent(RegistActivity.this,UserInfActivity.class);
                                    intent1.putExtra("cellphonenum",cellphonenum);
                                    startActivity(intent1);
                                    finish();
                                } else
                                    Toast.makeText(getApplicationContext(), "验证未成功,请正确输入验证码", Toast.LENGTH_LONG).show();
                                //  }    //     1
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"请点击获取验证码以后再输入验证码！",Toast.LENGTH_LONG).show();
                        }




                    }
                }

            }
        });
    }
    private Boolean  loginPro()
    {
        // 获取用户输入的用户名、密码
        String number = etnumber.getText().toString();
        String registerStep="验证码";

        JSONObject jsonObj;
        try
        {
            jsonObj = query(registerStep, number);
            //Toast.makeText(getApplicationContext(),"555",Toast.LENGTH_SHORT).show();
            //  如果userId 大于0
            Toast.makeText(getApplicationContext(),"收的的回调码："+jsonObj.getString("reback"),Toast.LENGTH_LONG).show();

            if (jsonObj.getString("reback") .equals("已注册"))
            {
                Toast.makeText(getApplicationContext(),"此号码已注册",Toast.LENGTH_LONG).show();

                return false;

            }
            else
            {

                checktemp=jsonObj.getString("identyNum");
                Toast.makeText(getApplicationContext(),"验证码是："+checktemp,Toast.LENGTH_LONG).show();
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
    private JSONObject query(String registerStep,String number)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", number);
        map.put("registerStep", registerStep);
        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "register.jsp";
        // 发送请求
//        Toast.makeText(getApplicationContext(),HttpUtil.postRequest(url, map),Toast.LENGTH_SHORT).show();
        return new JSONObject(HttpUtil.postRequest(url, map));
    }

    //延迟
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            checking.setText("重新验证");
            checking.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            checking.setClickable(false);
            checking.setText(millisUntilFinished / 1000 + "秒后再尝试");
        }
    }

}
