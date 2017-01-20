package com.nasolution.com.nasolution.Connectivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.TalukaSpinnerAdapter;
import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchAllTalukaDetails {

    private  static Context context;

    private static TalukaSpinnerAdapter talukaSpinnerAdapter;
    private static List<String> allTalukaList = new ArrayList<String>();
    private static List<String> allTalukaIdList = new ArrayList<String>();

    private static String talukaResponseResult;
    private static String talukaMethodName = "GetAllTaluka";


    public FetchAllTalukaDetails(UpdateProject updateProject) {
        context = updateProject;
    }


    public void FetchAllTaluka(List<String> talukaNameList, List<String> talukaIdList, TalukaSpinnerAdapter spinnerAdapter) {
        allTalukaList = talukaNameList;
        allTalukaIdList = talukaIdList;
        talukaSpinnerAdapter = spinnerAdapter;
        FetchAllTalukaDetails.TalukaAsyncCallWS task = new FetchAllTalukaDetails.TalukaAsyncCallWS();
        task.execute();
    }


    public static class TalukaAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            talukaResponseResult = GetDataWebService.GetAllTalukaForUpdate(talukaMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(talukaResponseResult.isEmpty()) {
                //               progressDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch District ");
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
                //               progressDialogBox.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(talukaResponseResult);
                    allTalukaIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allTalukaList.add(obj.getString("talukaName"));
                            allTalukaIdList.add(obj.getString("taluka_MasterId"));
                         //  talukaSpinnerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}