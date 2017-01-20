package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Architecture;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddNewArchitecture {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String architecResponseResult;
    private static String addArchitecWebMethod = "InsertArchitecture";
    private static String mobile;
    private static String  name;
    private static String email;
    private static String professionType;

    public AddNewArchitecture(Architecture architecture) {
        context = architecture;
    }

    public void InsertArchitec(String saveArchitecName, String saveArchitecEmail, String saveArchitecMobile, String saveProfessionName, ProgressDialog progressDialog) {
        name = saveArchitecName;
        email = saveArchitecEmail;
        mobile = saveArchitecMobile;
        professionType = saveProfessionName;
        progressDialogbox = progressDialog;
        AddArtitechAsyncCallWS  task = new AddArtitechAsyncCallWS();
        task.execute();
    }

    public static class AddArtitechAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            architecResponseResult = InsertDataWebservice.InsertArchitect(addArchitecWebMethod, name,email,mobile,professionType);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(architecResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to insert architech information");
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
            else if(architecResponseResult.equals("Architect already exists.")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Architect already exists.");
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
            else if(architecResponseResult.equals("Architect added successfully.")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Architecture Added successfully");
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
