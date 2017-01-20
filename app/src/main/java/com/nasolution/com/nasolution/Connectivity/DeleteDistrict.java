package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.DistrictMaster;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;

public class DeleteDistrict {

    private static int stateId;
    private static int districtId;
    private static ProgressDialog progressDialogBox;
    private static String deleteDistrictResponseResult;
    private static String deleteDistrictMethodName = "DeleteDistrict";
    private static Context context;

    public DeleteDistrict(DistrictMaster districtMaster) {
        context = districtMaster;
    }

    public void deleteDistrict(int saveDistrictID, ProgressDialog progressDialog) {
        districtId = saveDistrictID;
        progressDialogBox = progressDialog;
        DeleteDistrictAsynTaskCallWS task = new DeleteDistrictAsynTaskCallWS();
        task.execute();
    }

    public static class DeleteDistrictAsynTaskCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            deleteDistrictResponseResult = DeleteDataWebService.DeleteDistrict(deleteDistrictMethodName, districtId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(deleteDistrictResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to delete district details please try again");
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
            else if(deleteDistrictResponseResult.equals("District Not Found")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("District  details not found");
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
            else {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("District details deleted successfully");
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
