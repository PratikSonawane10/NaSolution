package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.StateMaster;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddStateToServer {


    private static String  newStateName;
    private static ProgressDialog progressDialogBox;
    private static String addStateResponseResult;
    public static String addStateMethodName = "InsertState";
    private static Context context;


    public AddStateToServer(StateMaster stateMaster) {
        context = stateMaster;
    }

    public static void AddState(String saveNewStateName, ProgressDialog progressDialog){

        newStateName = saveNewStateName;
        progressDialogBox = progressDialog;
        AddStateDetailAsyncCallWS task = new AddStateDetailAsyncCallWS();
        task.execute();
    }

    public static class AddStateDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            addStateResponseResult = InsertDataWebservice.AddState(addStateMethodName,newStateName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(addStateResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed To Add  State");
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
            else if(addStateResponseResult.equals("State already exists.")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("State already exists.");
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
                builder.setMessage("New State Added Successfully");
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
