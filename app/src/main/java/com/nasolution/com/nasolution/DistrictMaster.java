package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.AddDistrictToServer;
import com.nasolution.com.nasolution.Connectivity.DeleteDistrict;
import com.nasolution.com.nasolution.Connectivity.FetchDistrictDetails;
import com.nasolution.com.nasolution.Connectivity.FetchStateDetails;
import com.nasolution.com.nasolution.Connectivity.UpdateDistrictToServer;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistrictMaster extends BaseActivity implements View.OnClickListener {

    Button btnAddDistrict;
    Button btnUpdateDistrict;
    Button btnDeleteDistrict;
    //TextView lblSelectState;
    Spinner spinnerShowState;
    LinearLayout spinnerDistrictNameLayout;
    //TextView lblSelectDistrict;
    Spinner spinnerShowDistrict;
    LinearLayout addNewDisttrictLayout;
    //TextView lblAddDistrict;
    EditText edittxtAddDistricName;
    LinearLayout updateDistrictLayout;
    //TextView lblUpdateDistrict;
    EditText edittxtUpdateDistricName;
    Button btnSubmitAddDistrict;
    Button btnSubmitUpdateDistrict;
    Button btnSubmitDeleteDistrict;
    private boolean addDistrictClickable = true;

    private String[] districtStateArrayList;
    private List<String> districtStateList = new ArrayList<String>();
    private List<String> districtStateIdList = new ArrayList<String>();

    private String[] districtNameArrayList;
    private List<String> districtNameList = new ArrayList<String>();
    private List<String> districtIdList = new ArrayList<String>();
    private String stateId;
    private String districtId;
    private String stateName;
    private String districtName;
    private StateSpinnerAdapter spinnerAdapter;
    private DistrictSpinnerAdapter districtSpinnerAdapter;

    private int saveDistrictStateID;
    private String saveDistrictStateName;
    private String saveDistrictName;
    private int saveDistrictID;
    private String saveAddDistrictName;
    private String saveUpdateDistrictName;
    private ProgressDialog progressDialog;
    private ProgressDialog stateProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.district_master);

        btnAddDistrict = (Button) findViewById(R.id.btnAddDistrict);
        btnUpdateDistrict = (Button) findViewById(R.id.btnUpdateDistrict);
        btnDeleteDistrict = (Button) findViewById(R.id.btnDeleteDistrict);
       //lblSelectState = (TextView) findViewById(R.id.lblSelectDistrictState);
        spinnerShowState = (Spinner) findViewById(R.id.spinnerDistrictState);
        spinnerDistrictNameLayout = (LinearLayout) findViewById(R.id.spinnerDistrictNameRelativeLayout);
        //lblSelectDistrict = (TextView) findViewById(R.id.txtSelectDistrictName);
        spinnerShowDistrict = (Spinner) findViewById(R.id.spinnerDistrictName);
        addNewDisttrictLayout = (LinearLayout) findViewById(R.id.addNewDisttrictRelativeLayout);
        //lblAddDistrict = (TextView) findViewById(R.id.lblAddDistrict);
        edittxtAddDistricName = (EditText) findViewById(R.id.edittxtAddDistrict);
        updateDistrictLayout = (LinearLayout) findViewById(R.id.updateDistrictRelativelayout);
        //lblUpdateDistrict = (TextView) findViewById(R.id.lblUpdateDistrict);
        edittxtUpdateDistricName = (EditText) findViewById(R.id.edittxtUpdateDistrictName);
        btnSubmitAddDistrict = (Button) findViewById(R.id.btnSubmitAddDistrict);
        btnSubmitUpdateDistrict = (Button) findViewById(R.id.btnSubmitUpdateDistrict);
        btnSubmitDeleteDistrict = (Button) findViewById(R.id.btnSubmitDeleteDistrict);

        //lblAddDistrict.setText("Distric Name : ");
        spinnerDistrictNameLayout.setVisibility(View.GONE);
        updateDistrictLayout.setVisibility(View.GONE);
        btnSubmitUpdateDistrict.setVisibility(View.GONE);
        btnSubmitDeleteDistrict.setVisibility(View.GONE);

        btnAddDistrict.setOnClickListener(this);
        btnUpdateDistrict.setOnClickListener(this);
        btnDeleteDistrict.setOnClickListener(this);
        btnSubmitAddDistrict.setOnClickListener(this);
        btnSubmitUpdateDistrict.setOnClickListener(this);
        btnSubmitDeleteDistrict.setOnClickListener(this);

        districtSpinnerData();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnAddDistrict) {
            addDistrictClickable = true;
            chechAddDistrictLayoutField();
        }
        else if (v.getId() == R.id.btnUpdateDistrict){
            addDistrictClickable = false;
            checkUpdateDistrictLayoutField();
        }
        else if (v.getId() == R.id.btnDeleteDistrict){
            addDistrictClickable = false;
            checkDeleteDistrictLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitAddDistrict) {
            addDistrictClickable = true;
            addDistrict();
            chechAddDistrictLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitUpdateDistrict) {
            addDistrictClickable = false;
            updateDistrict();
            checkUpdateDistrictLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitDeleteDistrict) {
            addDistrictClickable = false;
            deleteDistrict();
            checkDeleteDistrictLayoutField();
        }
    }

    public void districtSpinnerData() {

        edittxtAddDistricName.setText("");
        districtStateArrayList = new String[]{
                "Select State"
        };
        districtStateList = new ArrayList<>(Arrays.asList(districtStateArrayList));
        spinnerAdapter = new StateSpinnerAdapter(this, R.layout.district_spinneritem,districtStateList);
        progressDialog = ProgressDialog.show(this, "", "Fetching State Details Please Wait...", true);
        fetchStateName();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowState.setAdapter(spinnerAdapter);
        spinnerShowState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    stateName = parent.getItemAtPosition(position).toString();
                    stateId = districtStateIdList.get(position);
                    saveDistrictStateName = stateName;
                    edittxtUpdateDistricName.setText("");

                    if(addDistrictClickable == true){
                        edittxtUpdateDistricName.setText("");
                    }
                    else {
                        if(stateId != null) {
                            progressDialog = ProgressDialog.show(DistrictMaster.this, "", "Fetching District Details Please Wait...", true);
                            fetchDistrictName();
                        }
                        spinnerShowDistrict.setSelection(districtNameList.indexOf(0));
                        districtSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        districtNameArrayList = new String[]{
                "Select District"
        };
        districtNameList = new ArrayList<>(Arrays.asList(districtNameArrayList));
        districtSpinnerAdapter = new DistrictSpinnerAdapter(DistrictMaster.this, R.layout.district_spinneritem, districtNameList);
        districtSpinnerAdapter.notifyDataSetChanged();
        districtSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowDistrict.setAdapter(districtSpinnerAdapter);
        spinnerShowDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    districtName = (String) parent.getItemAtPosition(position);
                    districtId = districtIdList.get(position);
                    edittxtUpdateDistricName.setText(districtName);
                    saveDistrictName = districtName;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addDistrict() {
        saveAddDistrictName = edittxtAddDistricName.getText().toString();
        if(stateId == null  || saveAddDistrictName.isEmpty()){
            Toast.makeText(DistrictMaster.this, "Fill All Details", Toast.LENGTH_LONG).show();
        }
        else{
            saveDistrictStateID = Integer.parseInt(stateId);
            progressDialog = ProgressDialog.show(DistrictMaster.this, "", "Adding District Details Please Wait...", true);
            AddDistrictToServer addDistrict= new AddDistrictToServer(this);
            addDistrict.AddDistrict(saveDistrictStateID,saveAddDistrictName,progressDialog);
        }
    }

    private void updateDistrict() {
        saveUpdateDistrictName = edittxtUpdateDistricName.getText().toString();
        if(stateId ==  null || districtId == null   || saveUpdateDistrictName.isEmpty()){
            Toast.makeText(DistrictMaster.this, "Fill All Details", Toast.LENGTH_LONG).show();
        }
        else {
            saveDistrictStateID = Integer.parseInt(stateId);
            saveDistrictID = Integer.parseInt(districtId);
            progressDialog = ProgressDialog.show(DistrictMaster.this, "", "Updating District Details Please Wait...", true);
            UpdateDistrictToServer updateDistrict= new UpdateDistrictToServer(this);
            updateDistrict.updateDistrict(saveDistrictStateID,saveDistrictID, saveUpdateDistrictName,progressDialog);
        }
    }

    private void deleteDistrict() {
        if(saveDistrictStateName == null  || districtId == null ){
            Toast.makeText(DistrictMaster.this, "please fill all details", Toast.LENGTH_LONG).show();
        }
        else {
            saveDistrictID = Integer.parseInt(districtId);
            progressDialog = ProgressDialog.show(DistrictMaster.this, "", "Deleting district details please wait...", true);
            DeleteDistrict deleteDistrict = new DeleteDistrict(this);
            deleteDistrict.deleteDistrict(saveDistrictID,progressDialog);
        }
    }

    private void fetchStateName() {
        FetchStateDetails fetchStateDetails = new FetchStateDetails(DistrictMaster.this);
        fetchStateDetails.FetchDistrictState(districtStateList,districtStateIdList,spinnerAdapter,progressDialog);
    }

    public  void fetchDistrictName(){
        saveDistrictStateID = Integer.parseInt(stateId);
        FetchDistrictDetails fetchDistrictName = new FetchDistrictDetails(DistrictMaster.this);
        fetchDistrictName.FetchAllDistrict(districtNameList,districtIdList,saveDistrictStateID,districtSpinnerAdapter,progressDialog);
    }

    private void chechAddDistrictLayoutField(){

        if (districtNameList != null){
            districtNameList.clear();
            districtStateList.clear();
        }
        stateId = null;
        districtId = null;
        saveDistrictStateName = null;
        saveDistrictName = null;
        saveDistrictStateID = 0;
        saveDistrictID = 0;
        spinnerAdapter.notifyDataSetChanged();
        districtSpinnerData();

        //lblAddDistrict.setText("Distric Name : ");
        edittxtAddDistricName.setText("");
        btnSubmitAddDistrict.setVisibility(View.VISIBLE);
        addNewDisttrictLayout.setVisibility(View.VISIBLE);
        spinnerDistrictNameLayout.setVisibility(View.GONE);
        updateDistrictLayout.setVisibility(View.GONE);
        btnSubmitUpdateDistrict.setVisibility(View.GONE);
        btnSubmitDeleteDistrict.setVisibility(View.GONE);
    }

    private void checkUpdateDistrictLayoutField() {

        if (districtNameList != null){
            districtNameList.clear();
            districtStateList.clear();
        }
        stateId = null;
        districtId = null;
        saveDistrictStateName = null;
        saveDistrictName = null;
        saveDistrictStateID = 0;
        saveDistrictID = 0;
        spinnerAdapter.notifyDataSetChanged();
        //lblUpdateDistrict.setText("Update District : ");
        districtSpinnerData();
        edittxtUpdateDistricName.setText("");

        spinnerDistrictNameLayout.setVisibility(View.VISIBLE);
        updateDistrictLayout.setVisibility(View.VISIBLE);
        btnSubmitUpdateDistrict.setVisibility(View.VISIBLE);
        addNewDisttrictLayout.setVisibility(View.GONE);
        btnSubmitDeleteDistrict.setVisibility(View.GONE);
        btnSubmitAddDistrict.setVisibility(View.GONE);
    }

    private void checkDeleteDistrictLayoutField() {

        if (districtNameList != null){
            districtNameList.clear();
            districtStateList.clear();
        }
        stateId = null;
        districtId = null;
        saveDistrictStateName = null;
        saveDistrictName = null;
        saveDistrictStateID = 0;
        saveDistrictID = 0;
        spinnerAdapter.notifyDataSetChanged();
        districtSpinnerData();

        spinnerDistrictNameLayout.setVisibility(View.VISIBLE);
        btnSubmitDeleteDistrict.setVisibility(View.VISIBLE);
        addNewDisttrictLayout.setVisibility(View.GONE);
        updateDistrictLayout.setVisibility(View.GONE);
        btnSubmitUpdateDistrict.setVisibility(View.GONE);
        btnSubmitAddDistrict.setVisibility(View.GONE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = this.getPackageManager();
        ComponentName component = new ComponentName(this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = this.getPackageManager();
        ComponentName component = new ComponentName(this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}