package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.CustomSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.AddNewArchitecture;
import com.nasolution.com.nasolution.Connectivity.DeleteArchitecture;
import com.nasolution.com.nasolution.Connectivity.FetchArchitecture;
import com.nasolution.com.nasolution.Connectivity.UpdateArchitectureDetails;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Architecture extends BaseActivity implements View.OnClickListener {

    TextView lblArchitecture;
    LinearLayout addArchitecLayout;
    LinearLayout updateAndDeleteLayout;
    EditText txtArchitecName;
    EditText txtArchitecEmail;
    EditText txtArchitecMobile;
    Spinner spinnerProfessionType;
    Button btnAddArchitec;
    Spinner spinnerArchitecName;
    Button btnUpdateArchitec;
    Button btnDeleteArchitec;
    ProgressDialog  progressDialog;
    ProgressDialog  detailsProgressDialog;
    ProgressDialog listProgressDialog;
    ProgressDialog  updateProgressDialog;

    private String addClick;
    private String editClick;
    private String deleteClick;

    private String saveArchitecName;
    private String saveArchitecEmail;
    private String saveArchitecMobile;
    private String professionName;
    private String saveProfessionName;

    private CustomSpinnerAdapter customSpinnerAdapter;
    private List<String> professionNameList = new ArrayList<String>();

    private String[] architecArrayList;
    private String architechName;
    private String architechEmail;
    private String architechMobile;
    private String architechProfession;
    private String architechId;
    private int saveArchitechId;
    private List<String> architecIdList = new ArrayList<String>();
    private List<String> architecNameList = new ArrayList<String>();

    private String architectResponseResult;
    private String architecDetailWebMethod = "GetArchitecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.architech);

        addClick = getIntent().getStringExtra("Add");
        editClick = getIntent().getStringExtra("Edit");
        deleteClick = getIntent().getStringExtra("Delete");

        lblArchitecture = (TextView) findViewById(R.id.lblArchitect);
        addArchitecLayout = (LinearLayout) findViewById(R.id.addArchitecLayout);
        updateAndDeleteLayout = (LinearLayout) findViewById(R.id.update_deleteArchitecLayout);
        txtArchitecName = (EditText) findViewById(R.id.txtArchitechName);
        txtArchitecEmail = (EditText) findViewById(R.id.txtArchitechEmail);
        txtArchitecMobile = (EditText) findViewById(R.id.txtArchitechMobile);
        spinnerProfessionType = (Spinner) findViewById(R.id.spinnerProfesionType);
        btnAddArchitec = (Button) findViewById(R.id.btnAddArchitech);
        spinnerArchitecName = (Spinner) findViewById(R.id.spinnerArchitecture);
        btnUpdateArchitec = (Button) findViewById(R.id.btnUpdateArchitech);
        btnDeleteArchitec = (Button) findViewById(R.id.btnDeleteArchitech);

        btnAddArchitec.setOnClickListener(this);
        btnUpdateArchitec.setOnClickListener(this);
        btnDeleteArchitec.setOnClickListener(this);

        if(addClick != null && addClick.equals("AddArchitec")){
            addArchitecLayout.setVisibility(View.VISIBLE);
            updateAndDeleteLayout.setVisibility(View.GONE);
            btnDeleteArchitec.setVisibility(View.GONE);
            btnUpdateArchitec.setVisibility(View.GONE);
            lblArchitecture.setText("Add Architecture");
            getProfessionSpinnerItems();
        }
        else if(editClick != null && editClick.equals("EditArchitec")){
            btnAddArchitec.setVisibility(View.GONE);
            btnDeleteArchitec.setVisibility(View.GONE);
            btnUpdateArchitec.setVisibility(View.VISIBLE);
            spinnerArchitecName.setVisibility(View.VISIBLE);
            btnUpdateArchitec.setVisibility(View.VISIBLE);
            lblArchitecture.setText("Update Architecture");
            getArchitectureSpinnerItems();
            getProfessionSpinnerItems();
        }
        else if(deleteClick != null && deleteClick.equals("DeleteArchitec")){
            updateAndDeleteLayout.setVisibility(View.VISIBLE);
            addArchitecLayout.setVisibility(View.GONE);
            btnUpdateArchitec.setVisibility(View.GONE);
            btnAddArchitec.setVisibility(View.GONE);
            lblArchitecture.setText("Delete Architecture");
            getArchitectureSpinnerItems();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAddArchitech){
            collectArchitectureData();
            if (txtArchitecName.getText().toString().isEmpty() || txtArchitecMobile.getText().toString().isEmpty() || professionName == null) {
                Toast.makeText(this, "All Details are neccessory", Toast.LENGTH_LONG).show();
            }
            else if (!isValidEmail(saveArchitecEmail)) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show();
            }
            else{
                AddArchitectureData();
            }
        }
        else if(v.getId() == R.id.btnUpdateArchitech){
            collectUpdateArchitectureData();
            if (txtArchitecName.getText().toString().isEmpty() || txtArchitecMobile.getText().toString().isEmpty()
                    || professionName == null || architechName.isEmpty() || architechId.isEmpty()) {
                Toast.makeText(this, "All Details are neccessory", Toast.LENGTH_LONG).show();
            }

            else if (!isValidEmail(saveArchitecEmail)) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show();
            }
            else {
                updateArchitectureData();
            }
        }
        else if(v.getId() == R.id.btnDeleteArchitech){
            if (architechName.isEmpty() || architechId.isEmpty()) {
                Toast.makeText(this, "Please Select Architecture", Toast.LENGTH_LONG).show();
            }
            else {
                saveArchitechId = Integer.parseInt(architechId);
                DeleteArchitectureData();
            }
        }
    }

    private void getProfessionSpinnerItems() {
        professionNameList = new ArrayList<String>();
        professionNameList.add("Choose Profession Type");
        professionNameList.add("Engineer");
        professionNameList.add("Professional");
        customSpinnerAdapter  = new CustomSpinnerAdapter(this, R.layout.spinner_item, professionNameList);
        customSpinnerAdapter.notifyDataSetChanged();
        customSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfessionType.setAdapter(customSpinnerAdapter);
        spinnerProfessionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    professionName = (String) parent.getItemAtPosition(position);
                    saveProfessionName = professionName;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getArchitectureSpinnerItems() {
        architecArrayList = new String[]{
                "Select Architecture"
        };
        architecNameList = new ArrayList<>(Arrays.asList(architecArrayList));
        customSpinnerAdapter = new CustomSpinnerAdapter(this, R.layout.spinner_item,architecNameList);
        getArchitectureList();
        customSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArchitecName.setAdapter(customSpinnerAdapter);
        spinnerArchitecName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    architechName = parent.getItemAtPosition(position).toString();
                    architechId = architecIdList.get(position);

                    if(!architechId.isEmpty()){
                        saveArchitechId = Integer.parseInt(architechId);
                        detailsProgressDialog = ProgressDialog.show(Architecture.this,"","Fetching details please wait...");
                        ArchitectDetailsAsyncTask task = new ArchitectDetailsAsyncTask();
                        task.execute();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void collectArchitectureData() {
            saveArchitecName = txtArchitecName.getText().toString();
            saveArchitecEmail = txtArchitecEmail.getText().toString();
            saveArchitecMobile = txtArchitecMobile.getText().toString();
            saveProfessionName = professionName;
    }

    private void collectUpdateArchitectureData() {
        saveArchitecName = txtArchitecName.getText().toString();
        saveArchitecEmail = txtArchitecEmail.getText().toString();
        saveArchitecMobile = txtArchitecMobile.getText().toString();
        saveProfessionName = professionName;
        saveArchitechId = Integer.parseInt(architechId);
    }

    public static boolean isValidEmail(String emailForValidation) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailForValidation).matches();
    }

    private void AddArchitectureData() {
        progressDialog = ProgressDialog.show(Architecture.this,"","Adding architect details please wait...");
        AddNewArchitecture addNewArchitecture = new AddNewArchitecture(this);
        addNewArchitecture.InsertArchitec(saveArchitecName,saveArchitecEmail,saveArchitecMobile,saveProfessionName,progressDialog);
    }

    private void updateArchitectureData() {
        updateProgressDialog = ProgressDialog.show(Architecture.this,"","Updating details please wait...");
        UpdateArchitectureDetails updateArchitectureDetails = new UpdateArchitectureDetails(this);
        updateArchitectureDetails.UpdateArchitec(saveArchitechId,saveArchitecName,saveArchitecEmail,saveArchitecMobile,saveProfessionName,updateProgressDialog);
    }

    private void DeleteArchitectureData() {
        saveArchitechId = Integer.parseInt(architechId);
        progressDialog = ProgressDialog.show(Architecture.this,"","Deleting architect details please wait...");
        DeleteArchitecture deleteArchitecture = new DeleteArchitecture(this);
        deleteArchitecture.DeleteArchitec(saveArchitechId,progressDialog);
    }

    private void getArchitectureList() {
        listProgressDialog = ProgressDialog.show(this, "", "Fetching details Please Wait...", true);
        FetchArchitecture fetchDetails = new FetchArchitecture(this);
        fetchDetails.FetchArchitecDetails(architecNameList,architecIdList,customSpinnerAdapter,listProgressDialog);
    }

    public  class ArchitectDetailsAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            architectResponseResult = GetDataWebService.GetArchitectureDetails(architecDetailWebMethod,saveArchitechId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(architectResponseResult.equals("No Record Found")) {
                detailsProgressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(Architecture.this);
                builder.setTitle("Result");
                builder.setMessage("Architect details not found");
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
                    JSONArray jsonArray = new JSONArray(architectResponseResult);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            architechId = obj.getString("architectureMasterId");
                            architechName = obj.getString("name");
                            architechEmail = obj.getString("email");
                            architechMobile = obj.getString("mobile");
                            saveProfessionName = obj.getString("professiontype");

                            txtArchitecName.setText(architechName);
                            txtArchitecEmail.setText(architechEmail);
                            txtArchitecMobile.setText(architechMobile);

                            if (saveProfessionName != null) {
                                int professionPosition = customSpinnerAdapter.getPosition(saveProfessionName);
                                spinnerProfessionType.setSelection(professionPosition);
                                customSpinnerAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (JSONException ex) {
                    ex.printStackTrace();
                }
                detailsProgressDialog.dismiss();
            }
        }
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
