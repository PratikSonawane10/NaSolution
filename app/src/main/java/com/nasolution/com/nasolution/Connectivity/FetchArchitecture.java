package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Architecture;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchArchitecture {


    private static Context context;
    private static CustomSpinnerAdapter adapter;
    private static ProgressDialog progressDialogBox;
    private static String architectDetailResponseResult;
    private static List<String> allArchitecIdList = new ArrayList<String>();
    private static List<String> allArchitecNameList = new ArrayList<String>();
    private static String getArchitecWebMethod = "GetAllArchitecture";

    public FetchArchitecture(Architecture architecture) {
        context = architecture;
    }


    public void FetchArchitecDetails(List<String> architecNameList, List<String> architecIdList, CustomSpinnerAdapter customSpinnerAdapter, ProgressDialog progressDialog) {
        allArchitecIdList = architecIdList;
        allArchitecNameList = architecNameList;
        adapter = customSpinnerAdapter;
        progressDialogBox = progressDialog;
        ArchiitectListAsyncTask  task = new ArchiitectListAsyncTask();
        task.execute();
    }

    public static class ArchiitectListAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
                architectDetailResponseResult = GetDataWebService.GetArchitectureList(getArchitecWebMethod);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(architectDetailResponseResult.equals("Error occured")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Failed to fetch architecture information");
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
            else if(architectDetailResponseResult.equals("No Record Found")) {
                progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Architecture details not found");
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
                    allArchitecIdList.clear();
                    allArchitecNameList.clear();
                    JSONArray jsonArray = new JSONArray(architectDetailResponseResult);
                    allArchitecNameList.add("Select Architecture");
                    allArchitecIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allArchitecIdList.add(obj.getString("architectureMasterId"));
                            allArchitecNameList.add(obj.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
                progressDialogBox.dismiss();
            }
        }
    }
}
