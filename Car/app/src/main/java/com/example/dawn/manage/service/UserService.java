package com.example.dawn.manage.service;

import com.example.dawn.manage.bean.User;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

/**
 * Created by Administrator on 2015-9-10.
 */
public interface UserService {
    public int userLogin(String LoginName, String PassWord) throws Exception;
    public int authorize(RequestParams rp, String active) throws IOException;
    public boolean checkmobnum(String cellphonenum);
    public boolean isValidEmail(String mail);
    public boolean isZipNO(String zipString);
    public boolean checkusername(String username);
    public int Regeist(User user) throws IOException;
    //public String  findPassword(String username)throws IOException, ServiceRulesException;

}
