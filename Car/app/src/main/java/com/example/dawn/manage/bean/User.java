package com.example.dawn.manage.bean;

/**
 * Created by Administrator on 2015-9-10.
 */
public class User {
    private String UserName;
    private String UserPassword;
    private String CompanyName;
    private String ShopPic;
    private String BossName;
    private String MobileNumber;
    private String Lisence;
    private String AlipayAccount;
    private String WechatpayAccount;
    private String qq;
    private String WechatNum;
    private int UserType;

    public String getUserName() {
        return UserName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getShopPic() {
        return ShopPic;
    }

    public String getBossName() {
        return BossName;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public String getLisence() {
        return Lisence;
    }

    public String getAlipayAccount() {
        return AlipayAccount;
    }

    public String getWechatpayAccount() {
        return WechatpayAccount;
    }

    public String getQq() {
        return qq;
    }

    public String getWechatNum() {
        return WechatNum;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public void setShopPic(String shopPic) {
        ShopPic = shopPic;
    }

    public void setBossName(String bossName) {
        BossName = bossName;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public void setLisence(String lisence) {
        Lisence = lisence;
    }

    public void setAlipayAccount(String alipayAccount) {
        AlipayAccount = alipayAccount;
    }

    public void setWechatpayAccount(String wechatpayAccount) {
        WechatpayAccount = wechatpayAccount;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setWechatNum(String wechatNum) {
        WechatNum = wechatNum;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }
}
