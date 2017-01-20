package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.ClientContact;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

public class AddClientContacts {

    private static Context context;
    private static ProgressDialog progressDialogbox;
    private static String clientResponseResult;
    private static String clientContactMethod = "InsertClientContact";
    private static String allMobile;
    private static String  allName;
    private static int  clientId;

    public AddClientContacts(ClientContact clientContact) {
        context  = clientContact;
    }

    public void UploadClientContact(int saveClientID, String saveAllName, String saveAllMobile, ProgressDialog clientProgressDialog) {
        clientId = saveClientID;
        allName = saveAllName;
        allMobile = saveAllMobile;
        progressDialogbox = clientProgressDialog;
        UploadClientContactAsyncCallWS  task = new UploadClientContactAsyncCallWS();
        task.execute();
    }

    public static class UploadClientContactAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            clientResponseResult = InsertDataWebservice.InsertClient(clientContactMethod, clientId,allName,allMobile);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(clientResponseResult.equals("Error occured")) {
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to insert contact information");
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
            else if(clientResponseResult.equals("Insert Client Contact")){
                progressDialogbox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Contact information Added successfully");
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
