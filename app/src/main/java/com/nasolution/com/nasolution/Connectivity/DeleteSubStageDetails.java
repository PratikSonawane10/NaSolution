package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.WebServices.DeleteDataWebService;
import com.nasolution.com.nasolution.DeleteSubStage;

public class DeleteSubStageDetails {

    private static int subStageId;
    private static ProgressDialog progressDialogBox;
    private static String deleteSubStageResponseResult;
    private static String deleteSubStageMethodName = "DeleteSubStage";
    private static Context context;

    public DeleteSubStageDetails(DeleteSubStage deleteSubStage) {
        context = deleteSubStage;
    }

    public void DeleteSubStage(int saveSubStageId, ProgressDialog subStageDialogBox) {
        subStageId =saveSubStageId;
        progressDialogBox = subStageDialogBox;
        DeleteSubStageAsynTaskCallWS task = new DeleteSubStageAsynTaskCallWS();
        task.execute();
    }

    public static class DeleteSubStageAsynTaskCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            deleteSubStageResponseResult = DeleteDataWebService.DeleteSubStage(deleteSubStageMethodName, subStageId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(deleteSubStageResponseResult.equals("Error occured")) {
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
            else if(deleteSubStageResponseResult.equals("Delete Sub-Stage")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Sub Stage Deleted Sucessfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        Intent gotohome = new Intent(context, Home.class);
                        context.startActivity(gotohome);
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
    }
}
