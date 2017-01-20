package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.TalukaMaster;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;

public class DeleteTaluka {

    private static int talukaId;
    private static ProgressDialog progressDialogBox;
    private static String deleteTalukaResponseResult;
    private static String deleteTalukaMethodName = "DeleteTaluka";
    private static Context context;

    public DeleteTaluka(TalukaMaster talukaMaster) {
        context = talukaMaster;
    }

    public void DeleteTaluka(int saveTalukaID, ProgressDialog progressDialog) {
        talukaId = saveTalukaID;
        progressDialogBox = progressDialog;
        DeleteTalukaAsynTaskCallWS task = new DeleteTalukaAsynTaskCallWS();
        task.execute();
    }

    public static class DeleteTalukaAsynTaskCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            deleteTalukaResponseResult = DeleteDataWebService.DeleteTaluka(deleteTalukaMethodName, talukaId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(deleteTalukaResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed To Delete");
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
            else if(deleteTalukaResponseResult.equals("Taluka Not Found")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Record Not Found");
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
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Taluka Deleted Successfully");
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
