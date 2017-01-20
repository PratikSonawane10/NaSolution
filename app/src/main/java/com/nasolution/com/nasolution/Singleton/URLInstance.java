package com.nasolution.com.nasolution.Singleton;

public class URLInstance {

    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://23.91.115.57/nasystem/NAWebService.asmx";
    private static String SOAP_ACTION = "http://tempuri.org/";

    public static String getUrl() {
        return URL;
    }
    public static String getNameSpace(){
        return  NAMESPACE;
    }

    public static  String getSOAP_ACTION(){
        return SOAP_ACTION;
    }
}