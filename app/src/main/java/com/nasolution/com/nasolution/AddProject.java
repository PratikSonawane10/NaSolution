package com.nasolution.com.nasolution;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ServiceTypeSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.TalukaSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.AddProjectDetail;
import com.nasolution.com.nasolution.Connectivity.FetchClientsDetails;
import com.nasolution.com.nasolution.Connectivity.FetchDistrictDetails;
import com.nasolution.com.nasolution.Connectivity.FetchServiceType;
import com.nasolution.com.nasolution.Connectivity.FetchStateDetails;
import com.nasolution.com.nasolution.Connectivity.FetchTalukaDetails;
import com.nasolution.com.nasolution.MultiSelectionSpinner.MultiSelectionSpinner;
import com.nasolution.com.nasolution.SessionManager.SessionManager;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class AddProject extends BaseActivity implements View.OnClickListener,MultiSelectionSpinner.OnMultipleItemsSelectedListener{
    Spinner spinnershowCompany;
    Spinner spinnerShowArchitecture;
    Spinner spinnerShowServiceType;
    Spinner spinnerProjectState;
    Spinner spinnerProjectDistrict;
    Spinner spinnerProjectTaluka;

    TextView addNewCompany;
    TextView lblArchitect;
    EditText editTextProjectName;
    EditText editTextOldSurvey;
    EditText editTextNewSurvey;
    EditText editTextFileNumber;
    EditText editTextVillageName;
    EditText editTextProjectArea;
    EditText editTextProjectDescription;
    Button btnSubmitProject;
    private ProgressDialog projectDailogbox;

    String userId;
    int saveUserId;
    int saveCompanyId;
    int saveServiceTypeId;
    String saveProjectName;
    String saveOldSurvey;
    String saveNewSurvey;
    String saveFileNumber;
    String saveVillageName;
    String saveProjectArea;
    String saveProjectDescription;

    //project Spinner Items
    String companyName;
    String companyId;
    private String[] companyArrayList;
    private ProgressDialog companyDialogBox;
    private ClientSpinnerAdapter clientSpinnerAdapter;
    public List<String> companyIdList = new ArrayList<String>();
    public List<String> companyNameList = new ArrayList<String>();


    //serviceType spinner items
    String serviceName;
    String serviceId;
    private String[] serviceArrayList;
    private ServiceTypeSpinnerAdapter serviceSpinnerAdapter;
    private ProgressDialog serviceDialogBox;
    private List<String> serviceIdList = new ArrayList<String>();
    private List<String> serviceNameList = new ArrayList<String>();

    private String[] allArchitectureArray;
    public List<String> architectureNameList = new ArrayList<String>();
    public List<String> architectureIdList = new ArrayList<String>();
    private ProgressDialog ArchitectureDialogBox;

    //state details
    private StateSpinnerAdapter stateAdapter;
    private DistrictSpinnerAdapter districtSpinnerAdapter;
    private TalukaSpinnerAdapter talukaSpinnerAdapter;
    private String stateId;
    private String districtId;
    private String talukaId;
    private String districtName;
    private String talukaName;
    private String stateName;

    private String[] stateArrayList;
    private ProgressDialog stateDailogbox;
    private List<String> stateNameList = new ArrayList<String>();
    private List<String> stateIdList = new ArrayList<String>();

    private String[] districtArrayList;
    private ProgressDialog districtDailogbox;
    private List<String> districtNameList = new ArrayList<String>();
    private List<String> districtIdList = new ArrayList<String>();

    private String[] talukaNameArrayList;
    private ProgressDialog talukaDailogbox;
    private List<String> talukaNameList = new ArrayList<String>();
    private List<String> talukaIdList = new ArrayList<String>();

    private int saveStateID;
    private int saveDistrictID;
    private int saveTalukaID;
    MultiSelectionSpinner multiSelectionSpinner;
    private static String architectureResponseResult;
    private static String architectureMethodName = "GetAllArchitecture";
    String multipleSelectedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_project);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId = user.get(SessionManager.KEY_USER_ID);

        addNewCompany = (TextView) findViewById(R.id.txtAddCommpanyNew);
        lblArchitect = (TextView) findViewById(R.id.lblArchitectforSpinner);
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spinnerSelectEngineer);
        spinnershowCompany = (Spinner)findViewById(R.id.spinnerSelectCompany);
        spinnerShowServiceType = (Spinner)findViewById(R.id.spinnerServiceType);
        spinnerProjectState = (Spinner)findViewById(R.id.spinnerProjectState);
        spinnerProjectDistrict = (Spinner)findViewById(R.id.spinnerProjectDistrict);
        spinnerProjectTaluka = (Spinner)findViewById(R.id.spinnerProjectTaluka);
        editTextProjectName = (EditText) findViewById(R.id.txtProjectName);
        editTextOldSurvey = (EditText) findViewById(R.id.txtOldSurveyNumber);
        editTextNewSurvey = (EditText) findViewById(R.id.txtNewSurveyNumber);
        editTextFileNumber = (EditText) findViewById(R.id.txtFileNumber);
        editTextVillageName = (EditText) findViewById(R.id.txtVillageName);
        editTextProjectArea = (EditText) findViewById(R.id.txtProjectArea);
        editTextProjectDescription = (EditText) findViewById(R.id.txtProjectDescription);
        btnSubmitProject = (Button) findViewById(R.id.btnSubmitProject);

        addNewCompany.setText("Add new company here");
        lblArchitect.setText("Select Architect : ");
        addNewCompany.setOnClickListener(this);
        btnSubmitProject.setOnClickListener(this);
        getCompanySpinnerItem();
        getAllArchitectureSpinnerItem();
        getServiceTypeSpinnerItems();
        getProjectStateDetails();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==  R.id.txtAddCommpanyNew){
            Intent companyIntent = new Intent(AddProject.this,AddCompany.class);
            startActivity(companyIntent);
        }
        else if(v.getId() ==  R.id.btnSubmitProject){
            multipleSelectedValue = multiSelectionSpinner.getSelectedItemsAsString();
            collectAllData();
            if (editTextProjectName.getText().toString().isEmpty() || editTextOldSurvey.getText().toString().isEmpty() ||
                    editTextNewSurvey.getText().toString().isEmpty() || editTextFileNumber.getText().toString().isEmpty() ||
                    editTextVillageName.getText().toString().isEmpty() || editTextProjectArea.getText().toString().isEmpty() ||
                    editTextProjectDescription.getText().toString().isEmpty() ||  districtId == null || talukaId == null) {
                Toast.makeText(this, "All Details are neccessory", Toast.LENGTH_LONG).show();
            }
            else{
                projectDailogbox = ProgressDialog.show(AddProject.this,"","Adding project details please wait...");
                AddNewProjectDetails();
            }
        }
    }

    private void collectAllData() {
        saveCompanyId = Integer.parseInt(companyId);
        saveProjectName = editTextProjectName.getText().toString();
        saveOldSurvey  = editTextOldSurvey.getText().toString();
        saveNewSurvey = editTextNewSurvey.getText().toString();
        saveFileNumber = editTextFileNumber.getText().toString();
        saveVillageName = editTextVillageName.getText().toString();
        saveProjectArea = editTextProjectArea.getText().toString();
        saveProjectDescription = editTextProjectDescription.getText().toString();
        saveDistrictID = Integer.parseInt(districtId);
        saveTalukaID = Integer.parseInt(talukaId);
        saveServiceTypeId = Integer.parseInt(serviceId);
        saveUserId = Integer.parseInt(userId);
    }

    private void getCompanySpinnerItem() {
        companyArrayList = new String[]{
                "Select Company"
        };
        companyNameList = new ArrayList<>(Arrays.asList(companyArrayList));
        clientSpinnerAdapter = new ClientSpinnerAdapter(this, R.layout.spinner_item, companyNameList);
        getCompanyList();
        clientSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnershowCompany.setAdapter(clientSpinnerAdapter);
        spinnershowCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    companyName = parent.getItemAtPosition(position).toString();
                    companyId = companyIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getAllArchitectureSpinnerItem() {
        ArchitectureDialogBox = ProgressDialog.show(this, "", "Fetching Architecture Please Wait...", true);
        ArchitectureAsyncCallWS task = new ArchitectureAsyncCallWS();
        task.execute();
    }


    public class ArchitectureAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            architectureResponseResult = GetDataWebService.GetAllArchitecture(architectureMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (architectureResponseResult.equals("No Network Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProject.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch Architecture ");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                        Intent intent = new Intent(AddProject.this,AddProject.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert1 = builder.create();
                alert1.show();
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(architectureResponseResult);
                    architectureIdList.add("0");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            architectureNameList.add(obj.getString("name"));
                            architectureIdList.add(obj.getString("architectureMasterId"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    String[] array =new String[architectureNameList.size()];
                    array = architectureNameList.toArray(array);
                    multiSelectionSpinner.setItems(array);
                    multiSelectionSpinner.setListener(AddProject.this);


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            if (ArchitectureDialogBox != null && ArchitectureDialogBox.isShowing()) {
                ArchitectureDialogBox.dismiss();
            }
        }
    }

    private void getServiceTypeSpinnerItems() {
        serviceArrayList = new String[]{
                "Select ServiceType"
        };
        serviceNameList = new ArrayList<>(Arrays.asList(serviceArrayList));
        serviceSpinnerAdapter = new ServiceTypeSpinnerAdapter(this, R.layout.spinner_item,serviceNameList);
        getServiceTypeList();
        serviceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowServiceType.setAdapter(serviceSpinnerAdapter);
        spinnerShowServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    serviceName = parent.getItemAtPosition(position).toString();
                    serviceId = serviceIdList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getProjectStateDetails() {

        stateArrayList = new String[]{
                "Select State"
        };
        stateNameList = new ArrayList<>(Arrays.asList(stateArrayList));
        stateAdapter = new StateSpinnerAdapter(this, R.layout.district_spinneritem, stateNameList);
        fetchStateName();
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProjectState.setAdapter(stateAdapter);
        spinnerProjectState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    stateId = stateIdList.get(position);

                    if(stateId != null){
                        fetchDistrictName();
                    }
                    spinnerProjectDistrict.setSelection(districtNameList.indexOf(0));
                    districtSpinnerAdapter.notifyDataSetChanged();
                    spinnerProjectTaluka.setSelection(talukaNameList.indexOf(0));
                    talukaSpinnerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        districtArrayList = new String[]{
                "Select District"
        };
        districtNameList = new ArrayList<>(Arrays.asList(districtArrayList));
        districtSpinnerAdapter = new DistrictSpinnerAdapter(this, R.layout.district_spinneritem, districtNameList);
        districtSpinnerAdapter.notifyDataSetChanged();
        districtSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProjectDistrict.setAdapter(districtSpinnerAdapter);
        spinnerProjectDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    districtName = (String) parent.getItemAtPosition(position);
                    districtId = districtIdList.get(position);
                    if(districtId != null) {
                        fetchTalukaName();
                    }
                    spinnerProjectTaluka.setSelection(talukaNameList.indexOf(0));
                    talukaSpinnerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        talukaNameArrayList = new String[]{
                "Select Taluka"
        };
        talukaNameList = new ArrayList<>(Arrays.asList(talukaNameArrayList));
        talukaSpinnerAdapter = new TalukaSpinnerAdapter(this, R.layout.district_spinneritem, talukaNameList);
        talukaSpinnerAdapter.notifyDataSetChanged();
        talukaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProjectTaluka.setAdapter(talukaSpinnerAdapter);
        spinnerProjectTaluka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    talukaName = (String) parent.getItemAtPosition(position);
                    talukaId = talukaIdList.get(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getCompanyList() {
        companyDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchClientsDetails fetchClientsDetails = new FetchClientsDetails(this);
        fetchClientsDetails.FetchAllClient(companyNameList,companyIdList,clientSpinnerAdapter, companyDialogBox);
    }

    private void getServiceTypeList() {
        serviceDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchServiceType fetchServiceType = new FetchServiceType(this);
        fetchServiceType.GetAllServiceType(serviceIdList,serviceNameList,serviceSpinnerAdapter,serviceDialogBox);
    }

    private void fetchStateName() {
        stateDailogbox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchStateDetails fetchStateDetails = new FetchStateDetails(this);
        fetchStateDetails.FetchState(stateNameList,stateIdList,stateAdapter,stateDailogbox);
    }

    public  void fetchDistrictName(){
        saveStateID = Integer.parseInt(stateId);
        districtDailogbox = ProgressDialog.show(this, "", "Fetching district details please wait...", true);
        FetchDistrictDetails fetchDistrictName = new FetchDistrictDetails(this);
        fetchDistrictName.FetchAllDistrict(districtNameList,districtIdList,saveStateID,districtSpinnerAdapter,districtDailogbox);
    }

    public  void fetchTalukaName(){
        saveDistrictID = Integer.parseInt(districtId);
        talukaDailogbox = ProgressDialog.show(this, "", "Fetching taluka details please wait...", true);
        FetchTalukaDetails fetchTalukaDetails = new FetchTalukaDetails(this);
        fetchTalukaDetails.FetchTaluka(talukaNameList,talukaIdList,saveDistrictID,talukaSpinnerAdapter,talukaDailogbox);
    }

    private void AddNewProjectDetails() {
        AddProjectDetail addProjectDetail = new AddProjectDetail(this);
        addProjectDetail.AddProject(saveCompanyId,saveProjectName,saveProjectDescription,saveServiceTypeId,
        saveOldSurvey,saveNewSurvey,saveTalukaID,saveDistrictID, saveFileNumber,saveVillageName, saveProjectArea,saveUserId,projectDailogbox,multipleSelectedValue );
    }

    @Override
    public void selectedIndices(List<Integer> indices) {
    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }
}
