package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.DistrictMaster;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateDistrictToServer {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String updateDistrictResponseResult;
    private static String updateDistrictMethodName = "EditDistrict";
    private static int stateId;
    private static int districtId;
    private static String updateDistrictName;

    public UpdateDistrictToServer(DistrictMaster updateDistrict) {
        context = updateDistrict;
    }

    public void updateDistrict(int saveDistrictStateID, int saveDistrictID, String saveUpdateDistrictName, ProgressDialog progressDialog) {

        progressDialogbox = progressDialog;
        stateId = saveDistrictStateID;
        districtId = saveDistrictID;
        updateDistrictName = saveUpdateDistrictName;
        UpdateDistrictDetailAsyncCallWS task = new UpdateDistrictDetailAsyncCallWS();
        task.execute();
    }

    public static class UpdateDistrictDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            updateDistrictResponseResult = UpdateDataWebservice.UpdateDistrict(updateDistrictMethodName,stateId,districtId, updateDistrictName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(updateDistrictResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed To Update District");
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
            else if(updateDistrictResponseResult.equals("State Not Found")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed To Update District");
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
            else {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Updated Successfully");
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
