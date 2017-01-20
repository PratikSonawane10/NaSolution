package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.TalukaMaster;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateTalukaToServer {
    
    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String updateTalukaResponseResult;
    private static String updateTalukaMethodName = "EditTaluka";
    private static int talukaId;
    private static int districtId;
    private static String updateTalukaName;

    public UpdateTalukaToServer(TalukaMaster talukaMaster) {
        context = talukaMaster;
    }

    public void updateTaluka(String saveUpdateTalukaName, int saveTalukaID, int saveDistrictID, ProgressDialog progressDialog) {
        updateTalukaName = saveUpdateTalukaName;
        talukaId = saveTalukaID;
        districtId = saveDistrictID;
        progressDialogbox = progressDialog;

        UpdateDistrictDetailAsyncCallWS task = new UpdateDistrictDetailAsyncCallWS();
        task.execute();
    }
    public static class UpdateDistrictDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            updateTalukaResponseResult = UpdateDataWebservice.UpdateTaluka(updateTalukaMethodName,updateTalukaName,talukaId, districtId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(updateTalukaResponseResult.equals("Error occured")) {
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
            else if(updateTalukaResponseResult.equals("Taluka Not Found")) {
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
                builder.setMessage("Taluka Updated Successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                }); builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

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
