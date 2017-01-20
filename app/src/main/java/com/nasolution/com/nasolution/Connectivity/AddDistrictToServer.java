package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.DistrictMaster;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddDistrictToServer {


    private static String  districtName;
    private static ProgressDialog progressDialogBox;
    private static String addDistrictResponseResult;
    private static String addDistrictMethodName = "InsertDistrict";
    private static Context context;
    private static int stateId;


    public AddDistrictToServer(DistrictMaster districtMaster){
        context = districtMaster;
    }

    public static void AddDistrict(int saveDistrictStateID, String saveDistrictName , ProgressDialog progressDialog){

        stateId = saveDistrictStateID;
        districtName = saveDistrictName;
        progressDialogBox = progressDialog;
        AddDistrictDetailAsyncCallWS task = new AddDistrictDetailAsyncCallWS();
        task.execute();
    }

    public static class AddDistrictDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            addDistrictResponseResult = InsertDataWebservice.AddDistrict(addDistrictMethodName,districtName,stateId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(addDistrictResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed To Add District");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alertbox = builder.create();
                alertbox.show();
            }
            else if(addDistrictResponseResult.equals("District already exists.")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("District already exists.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alertbox = builder.create();
                alertbox.show();
            }
            else{
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("New District Added Successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
        }
    }
}
