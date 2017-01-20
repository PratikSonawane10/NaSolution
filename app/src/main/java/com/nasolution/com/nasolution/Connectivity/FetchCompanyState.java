package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.CompanyStateAdapter;
import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchCompanyState {

    private  static Context context;

    private static CompanyStateAdapter stateSpinnerAdapter;
    private static CompanyStateAdapter adapter;
    private static List<String> allStateList = new ArrayList<String>();
    private static List<String> allStateIdList = new ArrayList<String>();
    private static String stateResponseResult;
    private static String stateMethodName = "GetStateForDistrict_Master";
    private static ProgressDialog stateDialogBox;

    private  static int stateID;
    private static DistrictSpinnerAdapter districtAdapter;
    private static String districtResponseResult;
    private static String districtMethodName = "SelectDistrict";
    private static ProgressDialog districtDialogBox;
    private static List<String> districtList = new ArrayList<String>();
    private static List<String> districtIdList = new ArrayList<String>();

    public FetchCompanyState(FragmentActivity activity) {
        context = activity;
    }


    public void FetchCompanyState(List<String> companyStateList, List<String> companyStateIdList, CompanyStateAdapter stateSpinner, ProgressDialog progressDialog) {
        allStateList = companyStateList;
        allStateIdList = companyStateIdList;
        stateSpinnerAdapter = stateSpinner;
        stateDialogBox = progressDialog;
        StateAsyncCallWS task = new StateAsyncCallWS();
        task.execute();
    }

    public void FetchCompanyDistrict(List<String> companyDistrictList, List<String> companyDistrictIdList, int saveCompanyStateId, DistrictSpinnerAdapter districtSpinnerAdapter, ProgressDialog progressDialog) {

        DistrictAsyncCallWS stateTask = new DistrictAsyncCallWS();
        stateTask.execute();
        districtList = companyDistrictList;
        districtIdList = companyDistrictIdList;
        stateID = saveCompanyStateId;
        districtAdapter = districtSpinnerAdapter;
        districtDialogBox = progressDialog;
    }

    public static class StateAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            stateResponseResult = GetDataWebService.GetStateForDistrict(stateMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(stateResponseResult.isEmpty()) {
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
                    JSONArray jsonArray = new JSONArray(stateResponseResult);
                    allStateIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            allStateList.add(obj.getString("stateName"));
                            allStateIdList.add(obj.getString("state_MasterId"));
//                            stateSpinnerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            if(stateDialogBox != null && stateDialogBox.isShowing())
            {
                stateDialogBox.dismiss();
            }
        }
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
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else if(districtResponseResult.equals("District Not Found")) {
                districtList.clear();
                districtIdList.clear();
                districtList.add("Select District");
                districtIdList.add("0");
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
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else {
                try {
                    districtList.clear();
                    districtIdList.clear();
                    JSONArray jsonArray = new JSONArray(districtResponseResult);
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

            if(districtDialogBox != null && districtDialogBox.isShowing())
            {
                districtDialogBox.dismiss();
            }
        }
    }
}
