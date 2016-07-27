package com.example.dawn.manage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.manage.service.UserService;
import com.example.dawn.manage.service.UserServiceImp;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class FindPWDActivity extends Activity {
    private String checktemp;

    EditText etusername, etChecked;
    private TimeCount time;
    private Button checking;
    private UserService userService = new UserServiceImp();
    // 定义界面中两个按钮
    Button bnGet, bnFind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd1);
        etusername=(EditText)findViewById(R.id.UserName);
        etChecked=(EditText)findViewById(R.id.AuthorizedCode);
        bnGet=(Button)findViewById(R.id.btn4authorize);
        bnFind=(Button)findViewById(R.id.btn4findpwd);

        bnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etusername.getText().toString();
                if(username.equals("")||"".equals(username))
                {
                    etusername.setHint("用户名不能为空");
                    etusername.setFocusable(true);
                    etusername.requestFocus();
                }
                else

                {
                    if(!userService.checkusername(username))
                    {
                        Toast.makeText(getApplicationContext(),"用户名格式不正确，请重新输入",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        findpro();
//                        CountdownTimerActivity time1=new CountdownTimerActivity();
//                        time1.onclick();


                        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
                        checking = (Button) findViewById(R.id.btn4authorize);


                        time.start();//开始计时
                        checking.setClickable(false);
                    }





                }

                // }
            }
        });


        bnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etusername.getText().toString();
                String checkword=etChecked.getText().toString();
                if (username .equals("") || "".equals(username)) {
                    etusername.setHint("用户名不能为空");
                    etusername.setFocusable(true);
                    etusername.requestFocus();
                    //textview1.performClick();
                }else {

                    if (checkword.equals("") || "".equals(checkword)||checktemp.equalsIgnoreCase(null)) {
                        //  etChecked.setEnabled(false);
                        etChecked.setHint("验证码不能为空");
                        etChecked.setFocusable(true);
                        etChecked.requestFocus();
                        Toast.makeText(getApplicationContext(),"请等待接收验证码",Toast.LENGTH_SHORT).show();
                    }
//                    else{
////                        if(!username.equals(bufferusername))
////                        {
////                            Toast.makeText(v.getContext(), "输入的手机号与验证手机号码不一致", Toast.LENGTH_LONG).show();
////                        }else{
////                            user.setUserName(username);
////                            user.setMobileNumber(username);
////                        }
//                    }
                    else {

//                                if (checktemp.equalsIgnoreCase(null)) {      //       1
//                                  //  setVisible(false);
//                                 //   etchecked.setEnabled(false);
//                                  //  Toast.makeText(getApplicationContext(),"请等待输入验证码",Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_LONG).show();
//
//                                } else {
                        etChecked.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "验证码是：" + checktemp + "输入的是：" + checkword, Toast.LENGTH_LONG).show();
                        if (checktemp.equalsIgnoreCase(checkword)) {
                            etChecked.setEnabled(false);

                            Toast.makeText(getApplicationContext(), "验证成功，请等待短信", Toast.LENGTH_LONG).show();
//                                Intent intent=new Intent(Login.this,UserInfActivity.class);
//                                startActivity(intent);
//                                finish();
                            findProw();
                        } else
                            Toast.makeText(getApplicationContext(), "验证未成功,请正确输入验证码", Toast.LENGTH_LONG).show();
                        //  }    //     1
                    }
                    // }
                    //  }
                }


            }
        });

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



//找密码第二步，收短信，会收到reback用于检测是否已注册过，不会收到,identyNum

    private Boolean  findProw()
    {
        // 获取用户输入的用户名、密码
        String username = etusername.getText().toString();
        String manageStep="找回密码";
        Toast.makeText(getApplicationContext(), manageStep, Toast.LENGTH_LONG).show();
//        String checkword= etchecked.getText().toString();
//        String pwd = etPass.getText().toString();
//        String route=etRoute.getText().toString();
        JSONObject jsonObj;
        try
        //验证是否是注册了，有可能是没有注册
        {
            jsonObj = query(manageStep, username);
            //Toast.makeText(getApplicationContext(),"555",Toast.LENGTH_SHORT).show();
            //  如果userId 大于0
//            Toast.makeText(getApplicationContext(),"收到",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"收的的回调码："+jsonObj.getString("reback"),Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),"第一次收到验证码："+jsonObj.getString("identyNum"),Toast.LENGTH_LONG).show();
            if (jsonObj.getString("reback") .equals("用户不存在"))
            {
                Toast.makeText(getApplicationContext(),"此用户不存在，请确认是否已经注册",Toast.LENGTH_LONG).show();
                // checktemp="xxxx";
                // Toast.makeText(getApplicationContext(),checktemp,Toast.LENGTH_LONG).show();
                return false;
            }
            else
            {
                //Toast.makeText(getApplicationContext(),"1111",Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getApplicationContext(),"第二次收到的验证码是："+checktemp,Toast.LENGTH_LONG).show();
//                checktemp=jsonObj.getString("identyNum");
                //  Toast.makeText(getApplicationContext(),"第二次收到的验证码是："+checktemp,Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),"收到的密码是："+checktemp,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"密码已成功发送，请等待",Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),"收到的密码是："+checktemp,Toast.LENGTH_LONG).show();
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        Toast.makeText(getApplicationContext(),"发送123",Toast.LENGTH_SHORT).show();
        return false;
    }
//    private JSONObject query1(String manageStep,String number)
//            throws Exception
//    {
//        // 使用Map封装请求参数
//        Map<String, String> map = new HashMap<>();
//        map.put("username", number);
//        map.put("manageStep", manageStep);
//        // 定义发送请求的URL
//
//        String url = HttpUtil.BASE_URL + "register.jsp";
//        // 发送请求
//        Toast.makeText(getApplicationContext(),"发送",Toast.LENGTH_SHORT).show();
//        return new JSONObject(HttpUtil.postRequest(url, map));
//    }
//}

    //找密码第一步收到短信是验证码，会收到reback用于确认是否已注册过 ，会收到identyNum用于收到验证码
    private Boolean  findpro()
    {
        // 获取用户输入的用户名、密码
        String username = etusername.getText().toString();
        String manageStep="验证码";
        Toast.makeText(getApplicationContext(), manageStep, Toast.LENGTH_LONG).show();
//        String checkword= etchecked.getText().toString();
//        String pwd = etPass.getText().toString();
//        String route=etRoute.getText().toString();
        JSONObject jsonObj;
        try
        //验证是否是注册了，有可能是没有注册
        {
            jsonObj = query(manageStep, username);
            //Toast.makeText(getApplicationContext(),"555",Toast.LENGTH_SHORT).show();
            //  如果userId 大于0
//            Toast.makeText(getApplicationContext(),"收到",Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),"收的的回调码："+jsonObj.getString("reback"),Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),"第一次收到验证码："+jsonObj.getString("identyNum"),Toast.LENGTH_LONG).show();
            if (jsonObj.getString("reback") .equals("用户不存在"))
            {
                Toast.makeText(getApplicationContext(),"此用户不存在，请确认是否已经注册",Toast.LENGTH_LONG).show();
                // checktemp="xxxx";
                // Toast.makeText(getApplicationContext(),checktemp,Toast.LENGTH_LONG).show();
                return false;
            }
            else
            {
                //Toast.makeText(getApplicationContext(),"1111",Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getApplicationContext(),"第二次收到的验证码是："+checktemp,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"收到验证码"+jsonObj.getString("identyNum"),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"短信将发到："+jsonObj.getString("phoneNumber")+",请确认",Toast.LENGTH_LONG).show();
                checktemp=jsonObj.getString("identyNum");//得到验证码
                //  Toast.makeText(getApplicationContext(),"第二次收到的验证码是："+checktemp,Toast.LENGTH_LONG).show();
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        Toast.makeText(getApplicationContext(),"发送123",Toast.LENGTH_SHORT).show();
        return false;
    }
    private JSONObject query(String manageStep,String username)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("manageStep", manageStep);
        // 定义发送请求的URL

        String url = HttpUtil.BASE_URL + "manage.jsp";
        // 发送请求
        Toast.makeText(getApplicationContext(),"发送",Toast.LENGTH_SHORT).show();
        return new JSONObject(HttpUtil.postRequest(url, map));
    }
}
