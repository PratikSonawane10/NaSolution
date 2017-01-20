package com.nasolution.com.nasolution.Model;


public class AllUsersItems {

    public String userMasterId;
    public String username;
    public String email;
    public String addres;
    public String emp_code;
    public String mobile;


    public AllUsersItems() {
    }

    public AllUsersItems(String userMasterId,String username,String addres,String email,String emp_code,String mobile) {

        this.userMasterId = userMasterId;
        this.username = username;
        this.addres = addres;
        this.email = email;
        this.email = emp_code;
        this.email = mobile;

    }


    public String getuserMasterId() {
        return userMasterId;
    }

    public void setuserMasterId(String userMasterId) {
        this.userMasterId = userMasterId;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getaddres() {
        return addres;
    }

    public void setaddres(String addres) {
        this.addres = addres;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getemp_code() {
        return emp_code;
    }

    public void setemp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile) {
        this.mobile = mobile;
    }




}
