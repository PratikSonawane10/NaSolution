package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.DeleteSubStage;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchSubStage {

    private static Context context;
    private static ProgressDialog progressDialog;
    private static int stageId;
    private static String subStageResponseResult;
    private static String subStageWebMethod = "GetSubstage";
    private static List<String> allSubStageIdList = new ArrayList<String>();
    private static List<String> allSubStageNameList = new ArrayList<String>();

    public FetchSubStage(DeleteSubStage deleteSubStage) {
        context = deleteSubStage;
    }

    public void GetSubStage(List<String> subStageNameList, List<String> subStageIdList, int saveStageId, ProgressDialog subStageDialogBox) {
        allSubStageIdList = subStageIdList;
        allSubStageNameList = subStageNameList;
        stageId = saveStageId;
        progressDialog = subStageDialogBox;
        SubStageAsynCall task = new SubStageAsynCall();
        task.execute();
    }

    public static  class SubStageAsynCall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            subStageResponseResult = GetDataWebService.GetSubStageById(subStageWebMethod , stageId );
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(subStageResponseResult.equals("Error occured")) {
                allSubStageIdList.clear();
                allSubStageNameList.clear();
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to fetch sub-stage information");
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
            else if(subStageResponseResult.equals("No Record Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("sub-stage details not found");
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
                try {
                    allSubStageIdList.clear();
                    allSubStageNameList.clear();
                    JSONArray jsonArray = new JSONArray(subStageResponseResult);
                    allSubStageNameList.add("Select Sub-Stage");
                    allSubStageIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allSubStageIdList.add(obj.getString("subStageId"));
                            allSubStageNameList.add(obj.getString("title"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }
    }
}
