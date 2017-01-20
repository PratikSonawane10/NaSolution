package com.nasolution.com.nasolution.Connectivity;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchAllDistrictDetails {

    private  static Context context;

    private static DistrictSpinnerAdapter districtSpinnerAdapter;
    private static List<String> allDistrictList = new ArrayList<String>();
    private static List<String> allDistrictIdList = new ArrayList<String>();

    private static String districtResponseResult;
    private static String districtMethodName = "GetAllDistrict";


    public FetchAllDistrictDetails(UpdateProject updateProject) {
        context = updateProject;
    }


    public void FetchAllDistrict(List<String> districtNameList, List<String> districtIdList, DistrictSpinnerAdapter spinnerAdapter) {
        allDistrictList = districtNameList;
        allDistrictIdList = districtIdList;
        districtSpinnerAdapter = spinnerAdapter;
        FetchAllDistrictDetails.DistrictAsyncCallWS task = new FetchAllDistrictDetails.DistrictAsyncCallWS();
        task.execute();
    }


    public static class DistrictAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            districtResponseResult = GetDataWebService.GetAllDistrictForUpdate(districtMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(districtResponseResult.isEmpty()) {
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
                    JSONArray jsonArray = new JSONArray(districtResponseResult);
                    allDistrictIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allDistrictList.add(obj.getString("districtName"));
                            allDistrictIdList.add(obj.getString("district_MasterId"));
                           // districtSpinnerAdapter.notifyDataSetChanged();
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