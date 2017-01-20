package com.nasolution.com.nasolution;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.FetchStateDetails;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;
import com.nasolution.com.nasolution.WebServices.UpdateDataWebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateCompany extends BaseActivity implements View.OnClickListener {

    Spinner spinnerUpdateCompany;
    EditText txtUpdateCompanyName;
    EditText txtUpdateCompanyEmail;
    EditText txtUpdateCompanyAddres;
    Spinner spinnerUpdateShowState;
    Spinner spinnerUpdateShowDistrict;
    EditText txtUpdateCompanyCity;
    EditText txtUpdateCompanyLandmark;
    EditText txtUpdateCompanyPincode;
    Button btnSubmitUpdateCompany;
    ProgressDialog progressDialog;
    ProgressDialog updateDistrictDialog;
    ProgressDialog submitUpdateDialog;

    public int updateCompanyId;
    public String updateCompanyName;
    public String updateEmail;
    public String updateCompanyAddres;
    public String updateCompanyStateName;
    public String updateCompanyDistrictName;
    public int updateCompanyDistrictId;
    public String updateCompanyCity;
    public String updateCompanyLandmark;
    public String updateCompanyPincode;
    public String updateStateId;
    public String updateDistrictId;
    public String updateStateName;
    public String updateDistrictName;
    public String saveUpdateCompanyStateName;
    public int saveUpdateCompanyStateId;
    public int saveUpdateCompanyDistrictId;
    public String saveUpdateCompanyDistrictName;

    public int tempCompanyId;
    public String tempCompanyName;
    public String tempEmail;
    public String tempCompanyAddres;
    public String tempCompanyStateName;
    public String tempCompanyDistrictID;
    public String tempCompanyDistrictName;
    public String tempCompanyCity;
    public String tempCompanyLandmark;
    public String tempCompanyPincode;

    private StateSpinnerAdapter companyStateAdapter;
    private DistrictSpinnerAdapter districtSpinnerAdapter;
    private String[] companyUpdateStateArrayList;
    private List<String> updateCompanyStateList = new ArrayList<String>();
    private List<String> updateompanyStateIdList = new ArrayList<String>();

    private String[] companyDistrictArrayList;
    private List<String> companyDistrictList = new ArrayList<String>();
    private List<String> companyDistrictIdList = new ArrayList<String>();

    private View v;
    private static Context context;
    private String companyID;
    private String companyName;
    private String[] companyArrayList;
    private ClientSpinnerAdapter adapter;
    private String companyResponseResult;
    private String companyDetailsResponse;
    private String districtResponseResult;
    private String companyDetailsWebMethod = "GetClient";
    private String allCcompanyMethodName = "GetAllClient";
    private String districtMethodName = "SelectDistrict";
    private List<String> companyNameList = new ArrayList<String>();
    private List<String> companyIdList = new ArrayList<String>();

    private String updateCompanyWebMethod = "UpdateClient";
    private String submitUpdateResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.updatecompany);

        spinnerUpdateCompany = (Spinner) findViewById(R.id.spinnerUpdateClient);
        txtUpdateCompanyName = (EditText) findViewById(R.id.txtUpdateCompanyName);
        txtUpdateCompanyEmail = (EditText) findViewById(R.id.txtUpdateCompanyEmail);
        txtUpdateCompanyAddres = (EditText) findViewById(R.id.txtUpdateCompanyAddress);
        spinnerUpdateShowState = (Spinner) findViewById(R.id.spinnerUpdateCompanyState);
        spinnerUpdateShowDistrict = (Spinner) findViewById(R.id.spinnerUpdateCompanyDistrict);
        txtUpdateCompanyCity = (EditText) findViewById(R.id.txtUpdateCompanyCity);
        txtUpdateCompanyLandmark = (EditText) findViewById(R.id.txtUpdateCompanyLandmark);
        txtUpdateCompanyPincode = (EditText) findViewById(R.id.txtUpdateCompanyPincode);
        btnSubmitUpdateCompany = (Button) findViewById(R.id.btnSubmitUpdateCompany);
        getClients();
        fetchUpdateStateDetails();
        btnSubmitUpdateCompany.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitUpdateCompany) {

            updateCompanyId = tempCompanyId;
            updateCompanyName = txtUpdateCompanyName.getText().toString();
            updateEmail = txtUpdateCompanyEmail.getText().toString();
            updateCompanyAddres = txtUpdateCompanyAddres.getText().toString();
            updateCompanyStateName = saveUpdateCompanyStateName;
            updateCompanyDistrictName = saveUpdateCompanyDistrictName;
            updateCompanyDistrictId = saveUpdateCompanyDistrictId;
            updateCompanyCity = txtUpdateCompanyCity.getText().toString();
            updateCompanyLandmark = txtUpdateCompanyLandmark.getText().toString();
            updateCompanyPincode = txtUpdateCompanyPincode.getText().toString();

            if (txtUpdateCompanyName.getText().toString().isEmpty() || txtUpdateCompanyAddres.getText().toString().isEmpty()
                    || saveUpdateCompanyStateName.isEmpty() || updateDistrictId == null || txtUpdateCompanyCity.getText().toString().isEmpty()
                    || txtUpdateCompanyLandmark.getText().toString().isEmpty() || txtUpdateCompanyPincode.getText().toString().isEmpty()) {

                Toast.makeText(UpdateCompany.this, "All Details are neccessory", Toast.LENGTH_LONG).show();
            } else if (!isValidEmail(updateEmail)) {
                Toast.makeText(UpdateCompany.this, "Email is not valid", Toast.LENGTH_LONG).show();
            } else {
                updateCompanyData();
            }
        }
    }

    private void fetchUpdateStateDetails() {
        companyUpdateStateArrayList = new String[]{
                "Select State"
        };
        updateCompanyStateList = new ArrayList<>(Arrays.asList(companyUpdateStateArrayList));
        companyStateAdapter = new StateSpinnerAdapter(this, R.layout.district_spinneritem, updateCompanyStateList);
        //  progressDialog = ProgressDialog.show(getActivity(), "", "Fetching State Details Please Wait...", true);
        fetchUpdateStateName();
        companyStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpdateShowState.setAdapter(companyStateAdapter);

        spinnerUpdateShowState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    saveUpdateCompanyStateName = null;
                    updateStateName = parent.getItemAtPosition(position).toString();
                    updateStateId = updateompanyStateIdList.get(position);
                    saveUpdateCompanyStateName = updateStateName;
                    if(updateStateId != null) {
                        saveUpdateCompanyStateId = Integer.parseInt(updateStateId);
                        fetchDistrictName();
                    }
                    companyStateAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        companyDistrictArrayList = new String[]{
                "Select District"
        };
        companyDistrictList = new ArrayList<>(Arrays.asList(companyDistrictArrayList));
        districtSpinnerAdapter = new DistrictSpinnerAdapter(this, R.layout.district_spinneritem, companyDistrictList);

        districtSpinnerAdapter.notifyDataSetChanged();
        districtSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpdateShowDistrict.setAdapter(districtSpinnerAdapter);
        spinnerUpdateShowDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    saveUpdateCompanyDistrictId = -1;
                    updateDistrictName = (String) parent.getItemAtPosition(position);
                    updateDistrictId = companyDistrictIdList.get(position);
                    saveUpdateCompanyDistrictName = updateDistrictName;
                    saveUpdateCompanyDistrictId = Integer.parseInt(updateDistrictId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getClients() {
        companyArrayList = new String[]{
                "Select Client"
        };
        companyNameList = new ArrayList<>(Arrays.asList(companyArrayList));
        adapter = new ClientSpinnerAdapter(this, R.layout.client_spinner_item, companyNameList);
        fetchAllClients();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUpdateCompany.setAdapter(adapter);
        spinnerUpdateCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    companyID = null;
                    companyName = parent.getItemAtPosition(position).toString();
                    companyID = companyIdList.get(position);
                    adapter.notifyDataSetChanged();
                    updateCompanyStateList.clear();
                    updateompanyStateIdList.clear();
                    companyDistrictList.clear();
                    companyDistrictIdList.clear();
                    progressDialog = ProgressDialog.show(UpdateCompany.this, "", "Fetching company details please wait...", true);
                    fetchUpdateStateDetails();

                    if (companyID != null) {
                        tempCompanyId = Integer.parseInt(companyID);
                        fetchCompanyDetails();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchAllClients() {
        AllClientAsyncCallWS task = new AllClientAsyncCallWS();
        task.execute();
    }

    public class AllClientAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            companyResponseResult = GetDataWebService.GetAllClients(allCcompanyMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (companyResponseResult.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCompany.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to fetch clients details plaease try again later ");
                AlertDialog alert1 = builder.create();
                alert1.show();
            } else {
                //  progressDialog.hide();
                try {
                    JSONArray jsonArray = new JSONArray(companyResponseResult);
                    companyIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            companyNameList.add(obj.getString("fullname"));
                            companyIdList.add(obj.getString("clientMasterId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void fetchCompanyDetails() {
        CompanyDetailsAsyncCallWS detailsTask = new CompanyDetailsAsyncCallWS();
        detailsTask.execute();
    }

    public class CompanyDetailsAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            companyDetailsResponse = GetDataWebService.GetClients(companyDetailsWebMethod, tempCompanyId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (companyDetailsResponse.equals("No Network Found")) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UpdateCompany.this);
                alertBuilder.setTitle("Result");
                alertBuilder.setMessage("Unable to Fetch Company Details ");
                AlertDialog alert1 = alertBuilder.create();
                alert1.show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(companyDetailsResponse);
                    companyIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            tempCompanyId = -1;
                            JSONObject obj = jsonArray.getJSONObject(i);
                            tempCompanyId = Integer.parseInt(obj.getString("clientMasterId"));
                            tempCompanyName = obj.getString("fullname");
                            tempEmail = obj.getString("email");
                            tempCompanyAddres = obj.getString("addres");
                            tempCompanyStateName = obj.getString("state");
                            tempCompanyDistrictID = obj.getString("districtId");
                            tempCompanyDistrictName = obj.getString("districtName");
                            tempCompanyCity = obj.getString("city");
                            tempCompanyLandmark = obj.getString("landmark");
                            tempCompanyPincode = obj.getString("pincode");

                            txtUpdateCompanyName.setText(tempCompanyName);
                            txtUpdateCompanyEmail.setText(tempEmail);
                            txtUpdateCompanyAddres.setText(tempCompanyAddres);
                            txtUpdateCompanyCity.setText(tempCompanyCity);
                            txtUpdateCompanyLandmark.setText(tempCompanyLandmark);
                            txtUpdateCompanyPincode.setText(tempCompanyPincode);
                            saveUpdateCompanyStateName = tempCompanyStateName;
                            saveUpdateCompanyDistrictId = Integer.parseInt(tempCompanyDistrictID);

                            if (!tempCompanyStateName.equals(null)) {
                                int spinnerStatePosition = companyStateAdapter.getPosition(tempCompanyStateName);
                                spinnerUpdateShowState.setSelection(spinnerStatePosition);
                                companyStateAdapter.notifyDataSetChanged();
                            }

                            if (!tempCompanyDistrictName.equals(null)) {
                                int spinnerStatePosition = districtSpinnerAdapter.getPosition(tempCompanyDistrictName);
                                spinnerUpdateShowDistrict.setSelection(spinnerStatePosition);
                                districtSpinnerAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    progressDialog.dismiss();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void fetchUpdateStateName() {
        FetchStateDetails fetchStateDetails = new FetchStateDetails(UpdateCompany.this);
        fetchStateDetails.UpdateCompanyState(updateCompanyStateList, updateompanyStateIdList, companyStateAdapter, progressDialog);
    }

    public void fetchDistrictName() {
        updateDistrictDialog = ProgressDialog.show(UpdateCompany.this, "", "Fetching District Details Please Wait...", true);
        DistrictAsyncCallWS districtTask = new DistrictAsyncCallWS();
        districtTask.execute();
    }

    public class DistrictAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            districtResponseResult = GetDataWebService.GetAllDistrict(districtMethodName, saveUpdateCompanyStateId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (districtResponseResult.equals("District Not Found")) {
                updateDistrictDialog.dismiss();
                companyNameList.clear();
                companyDistrictIdList.add("0");
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCompany.this);
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
                updateDistrictDialog.dismiss();
                try {
                    companyDistrictList.clear();
                    companyDistrictIdList.clear();
                    JSONArray jsonArray = new JSONArray(districtResponseResult);
                    companyDistrictIdList.add("0");
                    companyDistrictList.add("Select District");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            companyDistrictList.add(obj.getString("districtName"));
                            companyDistrictIdList.add(obj.getString("district_MasterId"));
                            districtSpinnerAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (tempCompanyDistrictName != null) {
                        int spinnerDistrictPosition = districtSpinnerAdapter.getPosition(tempCompanyDistrictName);
                        spinnerUpdateShowDistrict.setSelection(spinnerDistrictPosition);
                        districtSpinnerAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(UpdateCompany.this, "please select client first", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static boolean isValidEmail(String emailForValidation) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailForValidation).matches();
    }

    private void updateCompanyData() {
        submitUpdateDialog = ProgressDialog.show(UpdateCompany.this, "", "Updating company details please wait...");
        SubmitUpdateAsyncall submitUpdateAsyncall = new SubmitUpdateAsyncall();
        submitUpdateAsyncall.execute();
    }

    public class SubmitUpdateAsyncall extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            submitUpdateResponse = UpdateDataWebservice.UpdateCompanytDetail(updateCompanyWebMethod, updateCompanyId, updateCompanyName, updateCompanyAddres, updateEmail, updateCompanyLandmark, updateCompanyCity, saveUpdateCompanyDistrictId, saveUpdateCompanyStateName, updateCompanyPincode);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            submitUpdateDialog.dismiss();
            if (submitUpdateResponse.equals("Error occurred")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCompany.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to update details  please try again later.");
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
            } else {
                submitUpdateDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCompany.this);
                builder.setTitle("Result");
                builder.setMessage("Company details updated successfully");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        alert.dismiss();
                        String clientId = submitUpdateResponse;
                        Intent gotoContact = new Intent(UpdateCompany.this, ClientContact.class);
                        gotoContact.putExtra("UpdateClientID", clientId);
                        gotoContact.putExtra("UpdateCompanyName", companyName);

                        startActivity(gotoContact);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }
}
