package com.netease.nim.demo.login.bean;

public class UserBean {

    private String accid="";
    private String nickname="";
    private String upassword="";
    private String icon="";
    private String sign="";
    private String email="";
    private String birth;
    private String mobile="";
    private String gender="";
    private String ex="";   //扩展字段，json 最长1024字节

    @Override
    public String toString() {
        return "UserBean{" +
                "accid='" + accid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + upassword + '\'' +
                ", icon='" + icon + '\'' +
                ", sign='" + sign + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", ex='" + ex + '\'' +
                '}';
    }

    public String getPassword() {
        return upassword;
    }

    public void setPassword(String password) {
        this.upassword = password;
    }



    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getName() {
        return nickname;
    }

    public void setName(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }



}
