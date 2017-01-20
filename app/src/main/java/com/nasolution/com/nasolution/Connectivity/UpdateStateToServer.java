package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.StateMaster;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

public class UpdateStateToServer {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String updateStateResponseResult;
    private static String updateStateMethod = "EditState";
    private static int stateId;
    private static String  stateName;

    public UpdateStateToServer(StateMaster stateMaster) {
        context = stateMaster;
    }

    public void updateState(String saveUpdateStateName, int saveStateID, ProgressDialog progressDialog) {
        stateName = saveUpdateStateName;
        stateId = saveStateID;
        progressDialogbox = progressDialog;
        UpdateStateDetailAsyncCallWS task = new UpdateStateDetailAsyncCallWS();
        task.execute();
    }

    public static class UpdateStateDetailAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            updateStateResponseResult = UpdateDataWebservice.UpdateState(updateStateMethod, stateName,stateId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(updateStateResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to update state details please try again");
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
                builder.setMessage("State updated successfully");
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
