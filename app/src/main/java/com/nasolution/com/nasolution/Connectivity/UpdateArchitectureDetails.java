package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Architecture;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateArchitectureDetails {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String updateArchitecResponse;
    private static String updateArchitecWebMethod = "EditArchitecture";
    private static String architecMobile;
    private static String architecName;
    private static String architecEmail;
    private static String architecProfessionName;
    private static int  architecId;

    public UpdateArchitectureDetails(Architecture architecture) {
        context = architecture;
    }

    public void UpdateArchitec(int saveArchitechId, String saveArchitecName, String saveArchitecEmail, String saveArchitecMobile, String saveProfessionName, ProgressDialog updateProgressDialog) {
        architecId = saveArchitechId;
        architecName = saveArchitecName;
        architecEmail  = saveArchitecEmail;
        architecMobile = saveArchitecMobile;
        architecProfessionName = saveProfessionName;
        progressDialogbox = updateProgressDialog;
        UpdateArchitecAsyncCallWS task = new UpdateArchitecAsyncCallWS();
        task.execute();

    }

    public static class UpdateArchitecAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            updateArchitecResponse = UpdateDataWebservice.UpdateArchitecture(updateArchitecWebMethod, architecId,architecName,architecEmail,architecMobile, architecProfessionName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(updateArchitecResponse.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to update Architecture Details ");
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
            else if(updateArchitecResponse.equals("Architecture Not Found")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to update Architecture Details ");
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
            else if(updateArchitecResponse.equals("Architecture Updated")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Architecture details updated successfully");
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
