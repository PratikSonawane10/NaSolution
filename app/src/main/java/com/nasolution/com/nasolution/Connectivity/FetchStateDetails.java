package com.nasolution.com.nasolution.Connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.nasolution.com.nasolution.Adapter.CompanyStateAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.DistrictMaster;
import com.nasolution.com.nasolution.StateMaster;
import com.nasolution.com.nasolution.TalukaMaster;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchStateDetails {

    private  static Context context;

    private static StateSpinnerAdapter stateSpinnerAdapter;
    private static CompanyStateAdapter adapter;
    private static List<String> allStateList = new ArrayList<String>();
    private static List<String> allStateIdList = new ArrayList<String>();

    private static String stateResponseResult;
    private static String stateMethodName = "GetStateForDistrict_Master";
    private static ProgressDialog stateDialogBox;


    public FetchStateDetails(DistrictMaster districtMaster) {
        context = districtMaster;
    }

    public FetchStateDetails(TalukaMaster talukaMaster) {
        context = talukaMaster;
    }

    public FetchStateDetails(StateMaster stateMaster) {
        context = stateMaster;
    }

    public FetchStateDetails(FragmentActivity activity) {
        context = activity;
    }


    public void FetchDistrictState( List<String> stateList,List<String> districtStateIDList, StateSpinnerAdapter spinnerAdapter,ProgressDialog progressDialog) {
        StateAsyncCallWS task = new StateAsyncCallWS();
        task.execute();
        allStateList = stateList;
        allStateIdList = districtStateIDList;
        stateSpinnerAdapter = spinnerAdapter;
        stateDialogBox = progressDialog;
    }

    public void FetchTalukaState(List<String> talukaStateList, List<String> talukaStateIdList, StateSpinnerAdapter talukaSpinnerAdapter,ProgressDialog progressDialog) {
        allStateList = talukaStateList;
        allStateIdList = talukaStateIdList;
        stateSpinnerAdapter = talukaSpinnerAdapter;
        stateDialogBox = progressDialog;
        StateAsyncCallWS task = new StateAsyncCallWS();
        task.execute();
    }

    public void FetchState(List<String> stateNameList, List<String> stateIdList, StateSpinnerAdapter spinnerAdapter, ProgressDialog progressDialog) {
        allStateList = stateNameList;
        allStateIdList = stateIdList;
        stateSpinnerAdapter = spinnerAdapter;
        stateDialogBox = progressDialog;
        StateAsyncCallWS task = new StateAsyncCallWS();
        task.execute();
    }

    public void UpdateCompanyState(List<String> updateCompanyStateList, List<String> updateCompanyStateIdList, StateSpinnerAdapter stateSpinner, ProgressDialog progressDialog) {
        allStateList = updateCompanyStateList;
        allStateIdList = updateCompanyStateIdList;
        stateSpinnerAdapter = stateSpinner;
        stateDialogBox = progressDialog;
        StateAsyncCallWS task = new StateAsyncCallWS();
        task.execute();
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
                stateDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Result");
                builder.setMessage("Unable to  fetch state details please try again later.");
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
                stateDialogBox.dismiss();
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
}