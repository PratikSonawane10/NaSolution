package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.TalukaSpinnerAdapter;
import com.nasolution.com.nasolution.DistrictMaster;
import com.nasolution.com.nasolution.TalukaMaster;
import com.nasolution.com.nasolution.UpdateProject;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchDistrictDetails {

    private static Context context;
    private static String districtResponseResult;
    private static String districtMethodName = "SelectDistrict";

    private  static int stateID;
    private static StateSpinnerAdapter adapter;
    private static DistrictSpinnerAdapter districtAdapter;
    private static List<String> districtList = new ArrayList<String>();
    private static List<String> districtIdList = new ArrayList<String>();
    private static List<String> talukaList = new ArrayList<String>();
    private static List<String> talukaIDList = new ArrayList<String>();
    private static ProgressDialog districtDialogBox;
    private static TalukaSpinnerAdapter talukaAdapter;


    public FetchDistrictDetails(DistrictMaster districtMaster) {
        context = districtMaster;
    }

    public FetchDistrictDetails(TalukaMaster talukaMaster) {
        context = talukaMaster;
    }

    public FetchDistrictDetails(FragmentActivity activity) {
        context = activity;
    }

    public FetchDistrictDetails(UpdateProject updateProject) {
        context = updateProject;
    }

    public static void FetchAllDistrict(List<String> districtNameList, List<String> districtidlist,int saveDistrictStateID ,final DistrictSpinnerAdapter spinnerAdapter,ProgressDialog progressDialog) {

        DistrictAsyncCallWS stateTask = new DistrictAsyncCallWS();
        stateTask.execute();
        stateID = saveDistrictStateID;
        districtList = districtNameList;
        districtIdList = districtidlist;
        districtAdapter = spinnerAdapter;
        districtDialogBox = progressDialog;
    }

    public void FetchDistrictForCompany(List<String> companyDistrictList, List<String>  companyDistrictIdList, int saveCompanyStateId, DistrictSpinnerAdapter districtSpinnerAdapter, ProgressDialog progressDialog) {
        DistrictAsyncCallWS stateTask = new DistrictAsyncCallWS();
        stateTask.execute();
        districtList = companyDistrictList;
        districtIdList = companyDistrictIdList;
        stateID = saveCompanyStateId;
        districtAdapter = districtSpinnerAdapter;
        districtDialogBox = progressDialog;
    }

    public void FetchDistrictForTaluka(List<String> talukaDistrictNameList, List<String> talukaDistrictIdList, int saveStateID, List<String> talukaNameList, List<String> talukaIdList, DistrictSpinnerAdapter districtSpinnerAdapter, ProgressDialog progressDialog,TalukaSpinnerAdapter talukaSpinnerAdapter) {
        districtList = talukaDistrictNameList;
        districtIdList = talukaDistrictIdList;
        stateID = saveStateID;
        talukaList = talukaNameList;
        talukaIDList = talukaIdList;
        districtAdapter = districtSpinnerAdapter;
        districtDialogBox = progressDialog;
        talukaAdapter = talukaSpinnerAdapter;
        DistrictAsyncCallWS stateTask = new DistrictAsyncCallWS();
        stateTask.execute();
    }

    public static class DistrictAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            districtResponseResult = GetDataWebService.GetAllDistrict(districtMethodName, stateID);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(districtResponseResult.isEmpty()) {
                districtDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to fetch district details please try again later.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else if(districtResponseResult.equals("District details not found")) {
                districtDialogBox.dismiss();
                districtList.clear();
                districtIdList.clear();
                talukaList.clear();
                talukaIDList.clear();
                districtList.add("Select District");
                districtIdList.add("0");
                talukaList.add("Select Taluka");
                talukaIDList.add("0");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("District not found");
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
                districtDialogBox.dismiss();
                try {
                    districtList.clear();
                    districtIdList.clear();
                    talukaList.clear();
                    talukaIDList.clear();
                    JSONArray jsonArray = new JSONArray(districtResponseResult);
                    talukaList.add("Select Taluka");
                    talukaIDList.add("0");
                    districtList.add("Select District");
                    districtIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            districtList.add(obj.getString("districtName"));
                            districtIdList.add(obj.getString("district_MasterId"));
                            districtAdapter.notifyDataSetChanged();
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
