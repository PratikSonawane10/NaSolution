package com.nasolution.com.nasolution;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.CompanyStateAdapter;
import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.AddNewCompany;
import com.nasolution.com.nasolution.Connectivity.FetchCompanyState;
import com.nasolution.com.nasolution.Connectivity.FetchDistrictDetails;
import com.nasolution.com.nasolution.Connectivity.FetchStateDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nasolution.com.nasolution.Registration.isValidEmail;

public class AddCompany extends BaseActivity implements View.OnClickListener {

    EditText editTextCompanyName;
    EditText editTextCompanyEmail;
    EditText editTextCompanyAddres;
    Spinner spinnerShowState;
    Spinner spinnerShowDistrict;
    EditText editTextCompanyCity;
    EditText editTextCompanyLandmark;
    EditText editTextCompanyPincode;
    Button btnSubmitCompany;

    private String companyName;
    private String email;
    private String companyAddres;
    private String companyStateName;
    private String companyDistrictName;
    private String companyCity;
    private String companyLandmark;
    private String companyPincode;

    private ProgressDialog progressDialog;
    private String stateId;
    private String districtId;
    private String stateName;
    private String districtName;
    private String saveCompanyStateName;
    private int saveCompanyStateId;
    private int saveCompanyDistrictId;
    private String saveCompanyDistrictName;

    private CompanyStateAdapter stateSpinnerAdapter;
    private DistrictSpinnerAdapter districtSpinnerAdapter;
    private String[] companyStateArrayList;
    private List<String> companyStateList = new ArrayList<String>();
    private List<String> companyStateIdList = new ArrayList<String>();

    private String[] companyDistrictArrayList;
    private List<String> companyDistrictList = new ArrayList<String>();
    private List<String> companyDistrictIdList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.addcompany);

        editTextCompanyName = (EditText) findViewById(R.id.edittxtCompanyName);
        editTextCompanyEmail = (EditText) findViewById(R.id.edittxtCompanyEmail);
        editTextCompanyAddres = (EditText) findViewById(R.id.edittxtCompanyAddress);
        spinnerShowState = (Spinner) findViewById(R.id.spinnerCompanyState);
        spinnerShowDistrict = (Spinner) findViewById(R.id.spinnerCompanyDistrict);
        editTextCompanyCity = (EditText) findViewById(R.id.edittxtCompanyCity);
        editTextCompanyLandmark = (EditText) findViewById(R.id.edittxtCompanyLandmark);
        editTextCompanyPincode = (EditText) findViewById(R.id.edittxtCompanyPincode);
        btnSubmitCompany = (Button) findViewById(R.id.btnSubmitCompany);

        fetchStateDetails();
        btnSubmitCompany.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSubmitCompany){
            companyName = editTextCompanyName.getText().toString();
            email = editTextCompanyEmail.getText().toString();
            companyAddres = editTextCompanyAddres.getText().toString();
            companyStateName = saveCompanyStateName;
            companyDistrictName = saveCompanyDistrictName;
            companyCity = editTextCompanyCity.getText().toString();
            companyLandmark = editTextCompanyLandmark.getText().toString();
            companyPincode = editTextCompanyPincode.getText().toString();

            if (editTextCompanyName.getText().toString().isEmpty() || editTextCompanyAddres.getText().toString().isEmpty()
                    || saveCompanyStateName.isEmpty() ||districtId == null || editTextCompanyCity.getText().toString().isEmpty()
                    || editTextCompanyLandmark.getText().toString().isEmpty() || editTextCompanyPincode.getText().toString().isEmpty() ) {

                Toast.makeText(this, "All Details are neccessory", Toast.LENGTH_LONG).show();
            }
            else if (!isValidEmail(email)) {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_LONG).show();
            }
            else{
                uploadCompanyData();
            }
        }
    }

    private void fetchStateDetails() {
        companyStateArrayList = new String[]{
                "Select State"
        };
        companyStateList = new ArrayList<>(Arrays.asList(companyStateArrayList));
        stateSpinnerAdapter = new CompanyStateAdapter(this,R.layout.district_spinneritem,companyStateList);
      //  progressDialog = ProgressDialog.show(getActivity(), "", "Fetching State Details Please Wait...", true);
        fetchStateName();
        stateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowState.setAdapter(stateSpinnerAdapter);
        spinnerShowState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    stateName = parent.getItemAtPosition(position).toString();
                    stateId = companyStateIdList.get(position);
                    saveCompanyStateName = stateName;
                 // progressDialog = ProgressDialog.show(getActivity(), "", "Fetching District Details Please Wait...", true);
                    fetchDistrictName();
                    spinnerShowDistrict.setSelection(companyDistrictList.indexOf(0));
                    stateSpinnerAdapter.notifyDataSetChanged();
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
        spinnerShowDistrict.setAdapter(districtSpinnerAdapter);
        spinnerShowDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    districtName = (String) parent.getItemAtPosition(position);
                    districtId = companyDistrictIdList.get(position);
                    saveCompanyDistrictName = districtName;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchStateName() {
        FetchCompanyState fetchCompanyState = new FetchCompanyState(this);
        fetchCompanyState.FetchCompanyState(companyStateList,companyStateIdList,stateSpinnerAdapter,progressDialog);
    }

    public  void fetchDistrictName(){
        saveCompanyStateId = Integer.parseInt(stateId);
       // FetchDistrictDetails fetchDistrictName = new FetchDistrictDetails(getActivity());
        //fetchDistrictName.FetchDistrictForCompany(companyDistrictList,companyDistrictIdList,saveCompanyStateId,districtSpinnerAdapter,progressDialog);
        FetchCompanyState fetchCompanyDistrict = new FetchCompanyState(this);
        fetchCompanyDistrict.FetchCompanyDistrict(companyDistrictList,companyDistrictIdList,saveCompanyStateId,districtSpinnerAdapter,progressDialog);
    }

    private void uploadCompanyData() {
        saveCompanyDistrictId = Integer.parseInt(districtId);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding New Company Please Wait...");
        progressDialog.show();
        AddNewCompany addCompanyData = new AddNewCompany(this);
        addCompanyData.InsertCompany(companyName,companyAddres,email,companyLandmark,companyCity,saveCompanyDistrictId,saveCompanyStateName,companyPincode,progressDialog);
    }
}
