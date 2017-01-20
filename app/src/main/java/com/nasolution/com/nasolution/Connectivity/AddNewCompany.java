package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.ClientContact;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddNewCompany {

    private String companyTitle;
    private String emailId;
    private String address;
    private String companyStateName;
    private int companyDistrictID;
    private String city;
    private String landmark;
    private String pincode;
  //  private String fullName;
    private static Context context;
    private static ProgressDialog progressDialogBox;
    private static String companyResponseResult;
    public String addCompanyMethodName = "InsertCompany";

    public AddNewCompany(FragmentActivity activity) {
        context = activity;
    }


    public void InsertCompany(String companyName, String companyAddres, String email, String companyLandmark, String companyCity, int saveCompanyDistrictID, String saveCompanyStateName, String companyPincode, ProgressDialog progressDialog) {
        companyTitle = companyName;
        address = companyAddres;
        emailId = email;
        landmark = companyLandmark;
        city = companyCity;
        companyDistrictID = saveCompanyDistrictID;
        companyStateName = saveCompanyStateName;
        pincode = companyPincode;
        progressDialogBox = progressDialog;
        AddCompanyAsyncCallWS task = new AddCompanyAsyncCallWS();
        task.execute();
    }

    public class AddCompanyAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            companyResponseResult = InsertDataWebservice.CreateCompany(addCompanyMethodName, companyTitle, address, emailId, landmark, city, companyDistrictID, companyStateName, pincode);
             return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(companyResponseResult.equals("Company already exists.")) {
                progressDialogBox.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage(companyResponseResult);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if(companyResponseResult.equals("Error occured")) {
                progressDialogBox.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to Add Company");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
            else if(companyResponseResult != null){
                final String saveCompanyId = companyResponseResult;
                progressDialogBox.hide();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Company Added Successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                        Intent clientIntent = new Intent(context,ClientContact.class);
                        clientIntent.putExtra("COMPANY_ID",saveCompanyId);
                        context.startActivity(clientIntent);
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
        }
    }
}
