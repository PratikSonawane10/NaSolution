package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.StateMaster;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class DeleteState {
    
    private static int stateId;
    private static ProgressDialog progressDialogBox;
    private static String deleteStateResponseResult;
    private static String deleteStateMethodName = "DeleteState";
    private static Context context;

    public DeleteState(StateMaster stateMaster) {
        context = stateMaster;
    }

    public void DeleteState(int saveStateID, ProgressDialog progressDialog) {
        stateId = saveStateID;
        progressDialogBox = progressDialog;
        DeleteAsynTaskCallWS task = new DeleteAsynTaskCallWS();
        task.execute();
    }

    public static class DeleteAsynTaskCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            deleteStateResponseResult = DeleteDataWebService.DeleteState(deleteStateMethodName,stateId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(deleteStateResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to delete state details please try again");
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
                builder.setMessage("State details deleted successfully");
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
