package com.nasolution.com.nasolution.Model;


public class AllClientsItems {

    public String clientMasterId;
    public String clientname;

    public String city;
    public String email;
    public String addres;


    public AllClientsItems() {
    }

    public AllClientsItems(String clientMasterId,String clientname,String city,String addres,String email) {

        this.clientMasterId = clientMasterId;
        this.clientname = clientname;
        this.city = city;
        this.addres = addres;
        this.email = email;

    }


    public String getclientMasterId() {
        return clientMasterId;
    }

    public void setclientMasterId(String clientMasterId) {
        this.clientMasterId = clientMasterId;
    }

    public String getclientname() {
        return clientname;
    }

    public void setclientname(String clientname) {
        this.clientname = clientname;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.addres = city;
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




}
