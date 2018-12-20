package com.example.administrator.myapplication.bean;

public class LoginBean {
    private String member_id;
    private String pwd;
    private String  device_Key;

    public String getMember_id() {
        return member_id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getDevice_Key() {
        return device_Key;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setDevice_Key(String device_Key) {
        this.device_Key = device_Key;
    }
}
