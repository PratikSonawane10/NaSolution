package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.TalukaMaster;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddTalukaToServer {


    private static int districtId;
    private static Context context;
    private static String  talukaName;
    private static ProgressDialog progressDialogBox;
    private static String addTalukaResponseResult;
    private static String addTalukaMethodName = "InsertTaluka";

    public AddTalukaToServer(TalukaMaster talukaMaster) {
        context = talukaMaster;
    }

    public void AddTaluka(int saveDistrictID, String saveAddTalukaName, ProgressDialog progressDialog) {
        districtId = saveDistrictID;
        talukaName = saveAddTalukaName;
        progressDialogBox  = progressDialog;
        AddTalukaDetailAsyncCallWS task = new AddTalukaDetailAsyncCallWS();
        task.execute();
    }
    public static class AddTalukaDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            addTalukaResponseResult = InsertDataWebservice.AddTaluka(addTalukaMethodName,talukaName,districtId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(addTalukaResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed To Add Taluka");
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
            else if(addTalukaResponseResult.equals("Taluka already exists.")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Taluka already exists.");
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
                builder.setMessage("New Taluka Added Successfully");
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
