package com.nasolution.com.nasolution.Connectivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.StageListAdapter;
import com.nasolution.com.nasolution.AddSubStage;
import com.nasolution.com.nasolution.DeleteSubStage;
import com.nasolution.com.nasolution.UpdateSubStage;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchProjectStage {

    //project stage
    private static Context context;
    private static ProgressDialog progressDialog;
    private static int projectId;
    private static String stageResponseResult;
    private static StageListAdapter adapter;
    private static String stageWebMethod = "AllStageDetails";
    private static List<String> allStageIdList = new ArrayList<String>();
    private static List<String> allStageNameList = new ArrayList<String>();
    private static List<String> allStagePercentList = new ArrayList<String>();

    public FetchProjectStage(AddSubStage addSubStage) {
        context = addSubStage;
    }

    public FetchProjectStage(DeleteSubStage deleteSubStage) {
        context = deleteSubStage;
    }

    public FetchProjectStage(UpdateSubStage updateSubStage) {
        context = updateSubStage;
    }

    public void ProjectStage(List<String> stageIdList, List<String> stageNameList, int saveProjectId, ProgressDialog stageDialogBox, StageListAdapter stageListAdapter) {
        allStageIdList= stageIdList;
        allStageNameList = stageNameList;
        projectId = saveProjectId;
        progressDialog = stageDialogBox;
        adapter = stageListAdapter;
        ProjectStageAsynCall task = new ProjectStageAsynCall();
        task.execute();
    }

    public void ProjectStageWithPercent(List<String> stageIdList, List<String> stageNameList, int saveProjectId, List<String> stagePercentList, ProgressDialog stageDialogBox, StageListAdapter stageListAdapter) {
        allStageIdList= stageIdList;
        allStageNameList = stageNameList;
        projectId = saveProjectId;
        allStagePercentList = stagePercentList;
        progressDialog = stageDialogBox;
        adapter = stageListAdapter;
        ProjectStageWithPercentAsynCall task = new ProjectStageWithPercentAsynCall();
        task.execute();
    }

    public static  class ProjectStageAsynCall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            stageResponseResult = GetDataWebService.GetStageById(stageWebMethod , projectId );
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(stageResponseResult.equals("Error occured")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to fetch stage information");
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
            else  if(stageResponseResult.equals("No Record Found")) {
                allStageIdList.clear();
                allStageNameList.clear();
                allStageNameList.add("Select Stage");
                allStageIdList.add("0");
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("stage details not found");
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
                    allStageIdList.clear();
                    allStageNameList.clear();
                    JSONArray jsonArray = new JSONArray(stageResponseResult);
                    allStageNameList.add("Select Stage");
                    allStageIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allStageIdList.add(obj.getString("stageMasterId"));
                            allStageNameList.add(obj.getString("title"));
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

    public static  class ProjectStageWithPercentAsynCall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            stageResponseResult = GetDataWebService.GetStageById(stageWebMethod , projectId );
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(stageResponseResult.equals("Error occured")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to fetch stage information");
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
            else  if(stageResponseResult.equals("No Record Found")) {
                allStageNameList.clear();
                allStageNameList.add("Select Stage");
                allStagePercentList.add("0");
                allStageIdList.add("0");
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("stage details not found");
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
                    allStageIdList.clear();
                    allStageNameList.clear();
                    JSONArray jsonArray = new JSONArray(stageResponseResult);
                    allStageNameList.add("Select Stage");
                    allStagePercentList.add("0");
                    allStageIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allStageIdList.add(obj.getString("stageMasterId"));
                            allStageNameList.add(obj.getString("title"));
                            allStagePercentList.add(obj.getString("stagepercent"));
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
