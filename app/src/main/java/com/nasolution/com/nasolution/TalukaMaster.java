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
import com.nasolution.com.nasolution.Adapter.TalukaSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.AddTalukaToServer;
import com.nasolution.com.nasolution.Connectivity.DeleteTaluka;
import com.nasolution.com.nasolution.Connectivity.FetchDistrictDetails;
import com.nasolution.com.nasolution.Connectivity.FetchStateDetails;
import com.nasolution.com.nasolution.Connectivity.FetchTalukaDetails;
import com.nasolution.com.nasolution.Connectivity.UpdateTalukaToServer;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TalukaMaster extends BaseActivity implements View.OnClickListener {

    Button btnAddTaluka;
    Button btnUpdateTaluka;
    Button btnDeleteTaluka;
    //TextView lblSelectTalukaState;
    Spinner spinnerShowTalukaState;
    //TextView lblSelectTalukaDistrict;
    Spinner spinnerShowTalukaDistrict;
    LinearLayout spinnerTalukaNameLayout;
    //TextView lblSelectTaluka;
    Spinner spinnerShowTaluka;
    LinearLayout addNewTalukaLayout;
    //TextView lblAddTaluka;
    EditText edittxtAddTalukaName;
    LinearLayout updateTalukaLayout;
    //TextView lblUpdateTaluka;
    EditText edittxtUpdateTalukaName;
    Button btnSubmitAddTaluka;
    Button btnSubmitUpdateTaluka;
    Button btnSubmitDeleteTaluka;
    private boolean addTalukaClickable = true;


    private String[] talukaStateArrayList;
    private List<String> talukaStateList = new ArrayList<String>();
    private List<String> talukaStateIdList = new ArrayList<String>();

    private String[] talukaDistrictArrayList;
    private List<String> talukaDistrictNameList = new ArrayList<String>();
    private List<String> talukaDistrictIdList = new ArrayList<String>();

    private String[] talukaNameArrayList;
    private List<String> talukaNameList = new ArrayList<String>();
    private List<String> talukaIdList = new ArrayList<String>();

    private StateSpinnerAdapter stateAdapter;
    private DistrictSpinnerAdapter districtSpinnerAdapter;
    private TalukaSpinnerAdapter talukaSpinnerAdapter;
    private String stateName;
    private String stateId;
    private String districtId;
    private String talukaId;
    private String districtName;
    private String talukaName;

    private int saveStateID;
    private int saveDistrictID;
    private int saveTalukaID;
    private String saveTalukaName;
    private String saveAddTalukaName;
    private String saveUpdateTalukaName;
    private ProgressDialog progressDialog;
    private ProgressDialog stateProgressDialog;
    private ProgressDialog disrictProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taluka_master);

        btnAddTaluka = (Button) findViewById(R.id.btnAddTaluka);
        btnUpdateTaluka = (Button) findViewById(R.id.btnUpdateTaluka);
        btnDeleteTaluka = (Button) findViewById(R.id.btnDeleteTaluka);

        //lblSelectTalukaState = (TextView) findViewById(R.id.lblSelectTalukaState);
        spinnerShowTalukaState = (Spinner) findViewById(R.id.spinnerTalukaState);
        //lblSelectTalukaDistrict = (TextView) findViewById(R.id.lblSelectTalukaDistrict);
        spinnerShowTalukaDistrict = (Spinner) findViewById(R.id.spinnerTalukaDistrict);
        spinnerTalukaNameLayout = (LinearLayout) findViewById(R.id.spinnerTalukaNameRelativeLayout);
        //lblSelectTaluka = (TextView) findViewById(R.id.lblSelectTalukaName);
        spinnerShowTaluka = (Spinner) findViewById(R.id.spinnerTalukaName);
        addNewTalukaLayout = (LinearLayout) findViewById(R.id.addNewTalukaRelativeLayout);
        //lblAddTaluka = (TextView) findViewById(R.id.lblAddTaluka);
        edittxtAddTalukaName = (EditText) findViewById(R.id.edittxtAddTaluka);
        updateTalukaLayout = (LinearLayout) findViewById(R.id.updateTalukaRelativelayout);
        //lblUpdateTaluka = (TextView) findViewById(R.id.lblUpdateTaluka);
        edittxtUpdateTalukaName = (EditText) findViewById(R.id.edittxtUpdateTalukaName);

        btnSubmitAddTaluka = (Button) findViewById(R.id.btnSubmitAddTaluka);
        btnSubmitUpdateTaluka = (Button) findViewById(R.id.btnSubmitUpdateTaluka);
        btnSubmitDeleteTaluka = (Button) findViewById(R.id.btnSubmitDeleteTaluka);

        //lblAddTaluka.setText("Taluka Name : ");
        spinnerTalukaNameLayout.setVisibility(View.GONE);
        updateTalukaLayout.setVisibility(View.GONE);
        btnSubmitUpdateTaluka.setVisibility(View.GONE);
        btnSubmitDeleteTaluka.setVisibility(View.GONE);
        btnAddTaluka.setOnClickListener(this);
        btnUpdateTaluka.setOnClickListener(this);
        btnDeleteTaluka.setOnClickListener(this);
        btnSubmitAddTaluka.setOnClickListener(this);
        btnSubmitUpdateTaluka.setOnClickListener(this);
        btnSubmitDeleteTaluka.setOnClickListener(this);

        getStateDetailsForTaluka();
        getDistrictForTaluka();
        getTaluka();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnAddTaluka) {
            addTalukaClickable = true;
            chechAddTalukaLayoutField();
        }
        else if (v.getId() == R.id.btnUpdateTaluka){
            addTalukaClickable = false;
            checkUpdateTalukaLayoutField();
        }
        else if (v.getId() == R.id.btnDeleteTaluka){
            addTalukaClickable = false;
            checkDeleteTalukaLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitAddTaluka) {
            addTalukaClickable = true;
            addTaluka();
            chechAddTalukaLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitUpdateTaluka) {
            addTalukaClickable = false;
            updateTaluka();
            checkUpdateTalukaLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitDeleteTaluka) {
            addTalukaClickable = false;
            deleteTaluka();
            checkDeleteTalukaLayoutField();
        }
    }

    public void getStateDetailsForTaluka() {

        talukaStateArrayList = new String[]{
                "Select State"
        };
        talukaStateList = new ArrayList<>(Arrays.asList(talukaStateArrayList));
        stateAdapter = new StateSpinnerAdapter(this, R.layout.district_spinneritem,talukaStateList);
        fetchStateName();
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowTalukaState.setAdapter(stateAdapter);
        spinnerShowTalukaState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    stateName = parent.getItemAtPosition(position).toString();
                    stateId = talukaStateIdList.get(position);
                    edittxtUpdateTalukaName.setText("");
                    if(stateId != null) {
                        fetchDistrictName();
                    }
                    spinnerShowTalukaDistrict.setSelection(talukaDistrictNameList.indexOf(0));
                    districtSpinnerAdapter.notifyDataSetChanged();
                    spinnerShowTaluka.setSelection(talukaNameList.indexOf(0));
                    talukaSpinnerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getDistrictForTaluka() {
        talukaDistrictArrayList = new String[]{
                "Select District"
        };
        talukaDistrictNameList = new ArrayList<>(Arrays.asList(talukaDistrictArrayList));
        districtSpinnerAdapter = new DistrictSpinnerAdapter(this, R.layout.district_spinneritem, talukaDistrictNameList);
        districtSpinnerAdapter.notifyDataSetChanged();
        districtSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowTalukaDistrict.setAdapter(districtSpinnerAdapter);
        spinnerShowTalukaDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    districtName = (String) parent.getItemAtPosition(position);
                    districtId = talukaDistrictIdList.get(position);

                    edittxtUpdateTalukaName.setText("");
                    if(addTalukaClickable == true){
                        edittxtAddTalukaName.setText("");
                    }
                    else {
                        if(districtId != null) {
                            fetchTalukaName();
                        }
                        spinnerShowTaluka.setSelection(talukaNameList.indexOf(0));
                        talukaSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getTaluka() {
        talukaNameArrayList = new String[]{
                "Select Taluka"
        };
        talukaNameList = new ArrayList<>(Arrays.asList(talukaNameArrayList));
        talukaSpinnerAdapter = new TalukaSpinnerAdapter(this, R.layout.district_spinneritem, talukaNameList);
        talukaSpinnerAdapter.notifyDataSetChanged();
        talukaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowTaluka.setAdapter(talukaSpinnerAdapter);
        spinnerShowTaluka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    talukaName = (String) parent.getItemAtPosition(position);
                    talukaId = talukaIdList.get(position);
                    edittxtUpdateTalukaName.setText(talukaName);
                    saveTalukaName = talukaName;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void addTaluka() {
        saveAddTalukaName = edittxtAddTalukaName.getText().toString();
        if(districtId == null  || saveAddTalukaName.isEmpty()){
            Toast.makeText(this, "Fill All Details", Toast.LENGTH_LONG).show();
        }
        else{
            saveDistrictID = Integer.parseInt(districtId);
            saveAddTalukaName = edittxtAddTalukaName.getText().toString();
            progressDialog = ProgressDialog.show(this, "", "Adding Taluka Details Please Wait...", true);
            AddTalukaToServer addTaluka= new AddTalukaToServer(this);
            addTaluka.AddTaluka(saveDistrictID,saveAddTalukaName,progressDialog);
        }
    }
    private void updateTaluka() {
        saveUpdateTalukaName = edittxtUpdateTalukaName.getText().toString();
        if(talukaId ==  null || districtId == null   || saveUpdateTalukaName.isEmpty()){
            Toast.makeText(this, "Fill All Details", Toast.LENGTH_LONG).show();
        }
        else {
            saveTalukaID = Integer.parseInt(talukaId);
            saveDistrictID = Integer.parseInt(districtId);
            progressDialog = ProgressDialog.show(this, "", "Updating Taluka Details Please Wait...", true);
            UpdateTalukaToServer updateTalukaToServer= new UpdateTalukaToServer(this);
            updateTalukaToServer.updateTaluka(saveUpdateTalukaName,saveTalukaID, saveDistrictID,progressDialog);
        }
    }
    private void deleteTaluka() {
        if(saveTalukaName == null|| talukaId == null ){
            Toast.makeText(this, "Fill All Details", Toast.LENGTH_LONG).show();
        }
        else {
            saveTalukaID = Integer.parseInt(talukaId);
            progressDialog = ProgressDialog.show(this, "", "Deleting Taluka Please Wait...", true);
            DeleteTaluka deleteTaluka = new DeleteTaluka(this);
            deleteTaluka.DeleteTaluka(saveTalukaID,progressDialog);
        }
    }

    private void fetchStateName() {
        progressDialog = ProgressDialog.show(TalukaMaster.this, "", "Fetching state details please wait...", true);

        FetchStateDetails fetchStateDetails = new FetchStateDetails(this);
        fetchStateDetails.FetchTalukaState(talukaStateList,talukaStateIdList,stateAdapter,progressDialog);
    }

    public  void fetchDistrictName(){
        saveStateID = Integer.parseInt(stateId);
        progressDialog = ProgressDialog.show(TalukaMaster.this, "", "Fetching district details please wait...", true);
        FetchDistrictDetails fetchDistrictName = new FetchDistrictDetails(this);
        fetchDistrictName.FetchDistrictForTaluka(talukaDistrictNameList,talukaDistrictIdList,saveStateID,talukaNameList,talukaIdList,districtSpinnerAdapter,progressDialog,talukaSpinnerAdapter);
    }

    public  void fetchTalukaName(){
        saveDistrictID = Integer.parseInt(districtId);
        progressDialog = ProgressDialog.show(TalukaMaster.this, "", "Fetching taluka details please wait...", true);
        FetchTalukaDetails fetchTalukaDetails = new FetchTalukaDetails(this);
        fetchTalukaDetails.FetchTaluka(talukaNameList,talukaIdList,saveDistrictID,talukaSpinnerAdapter,progressDialog);
    }

    private void chechAddTalukaLayoutField() {
        if (talukaStateList != null || talukaDistrictNameList != null ||talukaNameList != null ){
            talukaStateList.clear();
            talukaDistrictNameList.clear();
            talukaNameList.clear();
        }
        stateId = null;
        districtId = null;
        talukaId = null;
        saveStateID = 0;
        saveDistrictID = 0;
        talukaName = null;
        saveTalukaName = null;
        getStateDetailsForTaluka();
        getDistrictForTaluka();
        getTaluka();

        //lblAddTaluka.setText("Taluka Name : ");
        edittxtAddTalukaName.setText("");
        edittxtUpdateTalukaName.setText("");
        addNewTalukaLayout.setVisibility(View.VISIBLE);
        btnSubmitAddTaluka.setVisibility(View.VISIBLE);
        spinnerTalukaNameLayout.setVisibility(View.GONE);
        updateTalukaLayout.setVisibility(View.GONE);
        btnSubmitUpdateTaluka.setVisibility(View.GONE);
        btnSubmitDeleteTaluka.setVisibility(View.GONE);
    }

    private void checkUpdateTalukaLayoutField() {
        if (talukaStateList != null || talukaDistrictNameList != null ||talukaNameList != null ){
            talukaStateList.clear();
            talukaDistrictNameList.clear();
            talukaNameList.clear();
        }
        stateId = null;
        districtId = null;
        talukaId = null;
        saveStateID = 0;
        saveDistrictID = 0;
        talukaName = null;
        saveTalukaName = null;
        getStateDetailsForTaluka();
        getDistrictForTaluka();
        getTaluka();
        //lblUpdateTaluka.setText("Update Taluka : ");
        edittxtUpdateTalukaName.setText("");

        spinnerTalukaNameLayout.setVisibility(View.VISIBLE);
        updateTalukaLayout.setVisibility(View.VISIBLE);
        btnSubmitUpdateTaluka.setVisibility(View.VISIBLE);
        addNewTalukaLayout.setVisibility(View.GONE);
        btnSubmitDeleteTaluka.setVisibility(View.GONE);
        btnSubmitAddTaluka.setVisibility(View.GONE);
    }
    private void checkDeleteTalukaLayoutField() {
        if (talukaStateList != null || talukaDistrictNameList != null ||talukaNameList != null ){
            talukaStateList.clear();
            talukaDistrictNameList.clear();
            talukaNameList.clear();
        }
        stateId = null;
        districtId = null;
        talukaId = null;
        saveStateID = 0;
        saveDistrictID = 0;
        talukaName = null;
        saveTalukaName = null;
        getStateDetailsForTaluka();
        getDistrictForTaluka();
        getTaluka();
        edittxtAddTalukaName.setText("");
        edittxtUpdateTalukaName.setText("");
        spinnerTalukaNameLayout.setVisibility(View.VISIBLE);
        btnSubmitDeleteTaluka.setVisibility(View.VISIBLE);
        updateTalukaLayout.setVisibility(View.GONE);
        btnSubmitUpdateTaluka.setVisibility(View.GONE);
        addNewTalukaLayout.setVisibility(View.GONE);
        btnSubmitAddTaluka.setVisibility(View.GONE);
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
