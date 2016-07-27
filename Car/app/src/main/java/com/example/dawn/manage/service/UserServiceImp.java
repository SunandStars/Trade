package com.example.dawn.manage.service;

import android.util.Log;

import com.example.dawn.manage.bean.User;
import com.example.dawn.manage.util.Constant;
import com.example.dawn.manage.util.HttpHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015-9-10.
 */
public class UserServiceImp implements UserService {
    private RequestParams rp;
    private static int returncode;
    public int userLogin(String LoginName, String PassWord) throws Exception{

        return 0;
    }

    @Override
    public int authorize(RequestParams rp,String active) throws IOException {
        HttpHelper.dopost(Constant.url_parent +"Regeist.do", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                super.onSuccess(response);
                try {

                    if (response.getString("code").equals("400")) {
                        Log.d("code",response.getString("code"));
                        returncode = 400;
                        Log.d("returncode",String.valueOf(returncode));
                    } else {
                        //if()
                    }
                } catch (Exception e) {

                }
            }
        });
        Log.d("returncode1", String.valueOf(returncode));
        return returncode;
    }

    @Override
    public boolean checkmobnum(String cellphonenum) {

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(cellphonenum);

        return m.matches();
    }

    @Override
    public boolean isValidEmail(String mail) {
        Pattern pattern = Pattern
                .compile("^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})");
        Matcher mc = pattern.matcher(mail);
        return mc.matches();
    }

    @Override
    public boolean isZipNO(String zipString){
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }

    @Override
    public boolean checkusername(String username){
        String str ="^[a-zA-Z][a-zA-Z0-9_]{0,15}$";
        return Pattern.compile(str).matcher(username).matches();
    }



    @Override
    public int Regeist(User user) throws IOException {
        return 0;
    }

}
