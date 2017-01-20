package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Architecture;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;

public class DeleteArchitecture {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String deleteArchitectureResponse;
    private static String deleteArchitectureMethod = "DeleteArchitecture";
    private static int  architecId;
    public DeleteArchitecture(Architecture architecture) {
        context = architecture;

    }

    public void DeleteArchitec(int saveArchitechId, ProgressDialog progressDialog) {
        architecId = saveArchitechId;
        progressDialogbox = progressDialog;
        DeletetArchitecAsyncCallWS task = new DeletetArchitecAsyncCallWS();
        task.execute();

    }

    public static class DeletetArchitecAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            deleteArchitectureResponse = DeleteDataWebService.DeleteAchitecture(deleteArchitectureMethod, architecId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(deleteArchitectureResponse.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to update contact information");
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
            else  if(deleteArchitectureResponse.equals("Architecture Not Found")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to update Architecture");
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
            else if(deleteArchitectureResponse.equals("Architecture Deleted")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Architecture Deleted successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent gotohome = new Intent(context, Home.class);
                        context.startActivity(gotohome);
                    }
                });
                AlertDialog alertBox = builder.create();
                alertBox.show();
            }
        }
    }
}
