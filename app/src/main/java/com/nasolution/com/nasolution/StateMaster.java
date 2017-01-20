package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.AddStateToServer;
import com.nasolution.com.nasolution.Connectivity.DeleteState;
import com.nasolution.com.nasolution.Connectivity.FetchStateDetails;
import com.nasolution.com.nasolution.Connectivity.UpdateStateToServer;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StateMaster extends BaseActivity implements View.OnClickListener {

    SwipeRefreshLayout StatMasterSwipeRefreshLayout;
    RecyclerView stateMasterRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;

    Button btnAddState;
    Button btnUpdateState;
    Button btnDeleteState;
    LinearLayout spinnerStateLayout;
    //TextView lblSelectState;
    Spinner spinnerShowState;
    LinearLayout addStateNameLayout;
    //TextView lblAddState;
    EditText edittxtAddState;
    LinearLayout updateStateNameLayout;
    //TextView lblUpdateState;
    EditText edittxtUpdateState;
    Button btnSubmitAddState;
    Button btnSubmitUpdateState;
    Button btnSubmitDeleteState;

    /*public List<StateListItems> sateNameList = new ArrayList<StateListItems>();
    public List<StateListItems> sateIdList = new ArrayList<StateListItems>();
*/
    public List<String> stateNameList = new ArrayList<String>();
    public List<String> stateIdList = new ArrayList<String>();
    public String stateResponseResult;
    private String[] stateArrayList;

    private String stateId;
    private String stateName;
    private int saveStateID;
    private String saveAddStateName;
    private String saveUpdateStateName;
    private StateSpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.state_master);

        btnAddState = (Button) findViewById(R.id.btnAddState);
        btnUpdateState = (Button) findViewById(R.id.btnUpdateState);
        btnDeleteState = (Button) findViewById(R.id.btnDeleteState);

        spinnerStateLayout = (LinearLayout) findViewById(R.id.spinnerStateLayouts);
        //lblSelectState = (TextView) findViewById(R.id.lblSelectState);
        spinnerShowState = (Spinner) findViewById(R.id.spinnerShowStateName);

        addStateNameLayout = (LinearLayout) findViewById(R.id.addNewStateRelativeLayout);
        //lblAddState = (TextView) findViewById(R.id.lblAddState);
        edittxtAddState = (EditText) findViewById(R.id.edittxtAddState);

        updateStateNameLayout = (LinearLayout) findViewById(R.id.updateStateRelativeLayout);
        //lblUpdateState = (TextView) findViewById(R.id.lblUpdateState);
        edittxtUpdateState = (EditText) findViewById(R.id.edittxtUpdateStateName);

        btnSubmitAddState = (Button) findViewById(R.id.btnSubmitAddState);
        btnSubmitUpdateState = (Button) findViewById(R.id.btnSubmitUpdateState);
        btnSubmitDeleteState = (Button) findViewById(R.id.btnSubmitDeleteState);

        //lblAddState.setText("State Name : ");
        edittxtAddState.setText("");
        spinnerStateLayout.setVisibility(View.GONE);
        updateStateNameLayout.setVisibility(View.GONE);
        btnSubmitUpdateState.setVisibility(View.GONE);
        btnSubmitDeleteState.setVisibility(View.GONE);

        btnAddState.setOnClickListener(this);
        btnUpdateState.setOnClickListener(this);
        btnDeleteState.setOnClickListener(this);
        btnSubmitAddState.setOnClickListener(this);
        btnSubmitUpdateState.setOnClickListener(this);
        btnSubmitDeleteState.setOnClickListener(this);

        getAllStateData();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddState) {
            chechAddStateLayoutField();
        }
        else if (v.getId() == R.id.btnUpdateState){
            checkUpdateStateLayoutField();
        }
        else if (v.getId() == R.id.btnDeleteState){
            checkDeleteStateLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitAddState) {
            addState();
            chechAddStateLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitUpdateState) {
            updateState();
            checkUpdateStateLayoutField();
        }
        else if (v.getId() == R.id.btnSubmitDeleteState) {
            deleteState();
            checkDeleteStateLayoutField();
        }
    }


    private void getAllStateData() {

        stateArrayList = new String[]{
                "Select State"
        };
        stateNameList = new ArrayList<>(Arrays.asList(stateArrayList));
        spinnerAdapter = new StateSpinnerAdapter(this, R.layout.district_spinneritem,stateNameList);
        fetchStateName();
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowState.setAdapter(spinnerAdapter);
        spinnerShowState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    stateName = parent.getItemAtPosition(position).toString();
                    stateId = stateIdList.get(position);
                    edittxtUpdateState.setText(stateName);
                    spinnerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void addState() {
        saveAddStateName = edittxtAddState.getText().toString();
        if(saveAddStateName.isEmpty()){
            Toast.makeText(StateMaster.this, "Fill all details", Toast.LENGTH_LONG).show();
        }
        else{
            progressDialog = ProgressDialog.show(this, "", "Adding state details please wait...", true);
            AddStateToServer addStateToServer = new AddStateToServer(this);
            addStateToServer.AddState(saveAddStateName,progressDialog);
        }
    }

    private void updateState() {
        saveUpdateStateName = edittxtUpdateState.getText().toString();
        if(saveUpdateStateName.isEmpty()|| stateId == null){
            Toast.makeText(this, "Fill all details ", Toast.LENGTH_LONG).show();
        }
        else {
            saveStateID = Integer.parseInt(stateId);
            progressDialog = ProgressDialog.show(this, "", "Updating state details please wait...", true);
            UpdateStateToServer updateStateToServer = new UpdateStateToServer(this);
            updateStateToServer.updateState(saveUpdateStateName,saveStateID, progressDialog);
        }
    }

    private void deleteState() {
        if(stateName == null  || stateId == null ){
            Toast.makeText(this, "Fill all details", Toast.LENGTH_LONG).show();
        }
        else {
            saveStateID = Integer.parseInt(stateId);
            progressDialog = ProgressDialog.show(this, "", "Deleting state details please wait...", true);
            DeleteState  deleteState = new DeleteState(this);
            deleteState.DeleteState(saveStateID,progressDialog);
        }
    }

    private void fetchStateName() {
        progressDialog = ProgressDialog.show(this, "", "Fetching state details please Wait...", true);
        FetchStateDetails fetchStateDetails = new FetchStateDetails(StateMaster.this);
        fetchStateDetails.FetchState(stateNameList,stateIdList,spinnerAdapter,progressDialog);
    }

    private void chechAddStateLayoutField(){

        if (stateNameList != null){
            stateNameList.clear();
            stateIdList.clear();
        }
        stateId = null;
        stateName = null;
        saveAddStateName = null;
        saveStateID = 0;
        spinnerAdapter.notifyDataSetChanged();
        getAllStateData();
        //lblAddState.setText("State Name : ");
        edittxtAddState.setText("");
        addStateNameLayout.setVisibility(View.VISIBLE);
        btnSubmitAddState.setVisibility(View.VISIBLE);
        spinnerStateLayout.setVisibility(View.GONE);
        updateStateNameLayout.setVisibility(View.GONE);
        btnSubmitUpdateState.setVisibility(View.GONE);
        btnSubmitDeleteState.setVisibility(View.GONE);
    }

    private void checkUpdateStateLayoutField(){

        if (stateNameList != null){
            stateNameList.clear();
            stateIdList.clear();
        }
        stateId = null;
        stateName = null;
        saveAddStateName = null;
        saveStateID = 0;
        spinnerAdapter.notifyDataSetChanged();
        getAllStateData();

        //lblUpdateState.setText("Update Name : ");
        edittxtUpdateState.setText("");
        spinnerStateLayout.setVisibility(View.VISIBLE);
        updateStateNameLayout.setVisibility(View.VISIBLE);
        addStateNameLayout.setVisibility(View.GONE);
        btnSubmitUpdateState.setVisibility(View.VISIBLE);
        btnSubmitDeleteState.setVisibility(View.GONE);
        btnSubmitAddState.setVisibility(View.GONE);
    }

    private void checkDeleteStateLayoutField(){

        if (stateNameList != null){
            stateNameList.clear();
            stateIdList.clear();
        }
        stateId = null;
        stateName = null;
        saveAddStateName = null;
        saveStateID = 0;
        spinnerAdapter.notifyDataSetChanged();
        getAllStateData();
        btnSubmitDeleteState.setVisibility(View.VISIBLE);
        spinnerStateLayout.setVisibility(View.VISIBLE);
        updateStateNameLayout.setVisibility(View.GONE);
        addStateNameLayout.setVisibility(View.GONE);
        btnSubmitUpdateState.setVisibility(View.GONE);
        btnSubmitAddState.setVisibility(View.GONE);
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