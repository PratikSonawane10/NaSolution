package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.DistrictSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ProjectSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.ServiceTypeSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.StateSpinnerAdapter;
import com.nasolution.com.nasolution.Adapter.TalukaSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.FetchAllDistrictDetails;
import com.nasolution.com.nasolution.Connectivity.FetchAllTalukaDetails;
import com.nasolution.com.nasolution.Connectivity.FetchClientsDetails;
import com.nasolution.com.nasolution.Connectivity.FetchDistrictDetails;
import com.nasolution.com.nasolution.Connectivity.FetchProjectList;
import com.nasolution.com.nasolution.Connectivity.FetchServiceType;
import com.nasolution.com.nasolution.Connectivity.FetchStateDetails;
import com.nasolution.com.nasolution.Connectivity.FetchTalukaDetails;
import com.nasolution.com.nasolution.Connectivity.UpdateProjectDetail;
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

public class UpdateProject extends BaseActivity implements View.OnClickListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {


    MultiSelectionSpinner multiSelectionSpinner;
    Spinner spinnershowProject;
    Spinner spinnershowCompany;
    Spinner spinnerShowServiceType;
    Spinner spinnerProjectState;
    Spinner spinnerProjectDistrict;
    Spinner spinnerProjectTaluka;

    EditText editTextProjectName;
    EditText editTextOldSurvey;
    EditText editTextNewSurvey;
    EditText editTextFileNumber;
    EditText editTextVillageName;
    EditText editTextProjectArea;
    EditText editTextProjectDescription;
    Button btnSubmitProject;
    private ProgressDialog projectDailogbox;

    String tempProjectId;
    String tempCompanyId;
    String tempServiceTypeId;
    String tempDistrictId;
    String tempTalukaId;
    String tempStateId;
    String tempStateName;
    String tempDistrictName;
    String tempTalukaName;
    String tempProjectName;
    String tempOldSurvey;
    String tempNewSurvey;
    String tempFileNumber;
    String tempVillageName;
    String tempProjectArea;
    String tempProjectDescription;

    int updateProjectId;
    int userId;
//    int updateUserId;
//    int updateServiceTypeId;
//    String updateProjectName;
//    String updateOldSurvey;
//    String updateNewSurvey;
//    String updateFileNumber;
//    String updateVillageName;
//    String updateProjectArea;
//    String updateProjectDescription;


    //project Spinner Items
    String projectName;
    String projectId;
    private String[] projectArrayList;
    private ProgressDialog projectDialogBox;
    private String projectResponseResult;
    private String prevArchitectureResponseResult;
    private ProgressDialog projectDetailDaialogBox;
    private ProjectSpinnerAdapter projectSpinnerAdapter;
    private String projectWebMethod = "GetProjectById";
    private String prevArchitectureWebMethod = "EditArchitectureForProject";
    public List<String> projectIdList = new ArrayList<String>();
    public List<String> projectNameList = new ArrayList<String>();

    LinearLayout projectDetailsLinearLayout;

    //company Spinner Items
    String companyName;
    int companyId;
    private String[] companyArrayList;
    private ProgressDialog companyDialogBox;
    private ClientSpinnerAdapter clientSpinnerAdapter;
    public List<String> companyIdList = new ArrayList<String>();
    public List<String> companyNameList = new ArrayList<String>();

    //serviceType spinner items
    String serviceName;
    int serviceId;
    private String[] serviceArrayList;
    private ServiceTypeSpinnerAdapter serviceSpinnerAdapter;
    private ProgressDialog serviceDialogBox;
    private List<String> serviceIdList = new ArrayList<String>();
    private List<String> serviceNameList = new ArrayList<String>();

    //state details
    private DistrictSpinnerAdapter districtSpinnerAdapter;
    private TalukaSpinnerAdapter talukaSpinnerAdapter;
    private int districtId;
    private int talukaId;
    private String districtName;
    private String talukaName;

    private String[] districtArrayList;
    private ProgressDialog districtDailogbox;
    private List<String> districtNameList = new ArrayList<String>();
    private List<String> districtIdList = new ArrayList<String>();

    private String[] talukaNameArrayList;
    private ProgressDialog talukaDailogbox;
    private List<String> talukaNameList = new ArrayList<String>();
    private List<String> talukaIdList = new ArrayList<String>();

    private static String talukaResponseResult;
    private static String talukaMethodName = "SelectTaluka";

    private int saveUpdateDistrictID;
    String multipleSelectedValue;
    private ProgressDialog ArchitectureDialogBox;
    private static String architectureResponseResult;
    private static String architectureMethodName = "GetAllArchitecture";

    public List<String> architectureNameList = new ArrayList<String>();
    public List<String> architectureIdList = new ArrayList<String>();
    public List<String> PrevArchitectureNameList = new ArrayList<String>();
    String[] precSelectedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_project);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        projectDetailsLinearLayout = (LinearLayout) findViewById(R.id.projectDetailsLinearLayout);
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spinnerUpdateEngineer);
        spinnershowProject = (Spinner) findViewById(R.id.spinnerUpdateProject);
        spinnershowCompany = (Spinner) findViewById(R.id.spinnerUpdateCompany);
        spinnerShowServiceType = (Spinner) findViewById(R.id.spinnerUpdateServiceType);
        spinnerProjectDistrict = (Spinner) findViewById(R.id.spinnerUpdateProjectDistrict);
        spinnerProjectTaluka = (Spinner) findViewById(R.id.spinnerUpdateProjectTaluka);
        editTextProjectName = (EditText) findViewById(R.id.txtUpdateProjectName);
        editTextOldSurvey = (EditText) findViewById(R.id.txtUpdateOldSurveyNumber);
        editTextNewSurvey = (EditText) findViewById(R.id.txtUpdateNewSurveyNumber);
        editTextFileNumber = (EditText) findViewById(R.id.txtUpdateFileNumber);
        editTextVillageName = (EditText) findViewById(R.id.txtUpdateVillageName);
        editTextProjectArea = (EditText) findViewById(R.id.txtUpdateProjectArea);
        editTextProjectDescription = (EditText) findViewById(R.id.txtUpdatetProjectDescription);
        btnSubmitProject = (Button) findViewById(R.id.btnSubmitUpdateProject);

        projectDetailsLinearLayout.setVisibility(View.GONE);
        btnSubmitProject.setOnClickListener(this);

        getProjectSpinnerItem();
        getAllDistrict();
        getCompanySpinnerItem();
        getArchitectureSpinnerItem();
        getServiceTypeSpinnerItems();


        if (projectId != null) {
            editTextProjectName.setText(tempProjectName);
            editTextFileNumber.setText(tempFileNumber);
            editTextOldSurvey.setText(tempOldSurvey);
            editTextNewSurvey.setText(tempNewSurvey);
            editTextProjectArea.setText(tempProjectArea);
            editTextVillageName.setText(tempVillageName);
            editTextProjectDescription.setText(tempProjectDescription);
        }
    }


    private void fetchAllTalukaName() {
        FetchAllTalukaDetails fetchAllTalukaDetails = new FetchAllTalukaDetails(this);
        fetchAllTalukaDetails.FetchAllTaluka(talukaNameList, talukaIdList, talukaSpinnerAdapter);
    }

    private void getAllDistrict() {

        districtArrayList = new String[]{
                "Select District"
        };
        districtNameList = new ArrayList<>(Arrays.asList(districtArrayList));
        districtSpinnerAdapter = new DistrictSpinnerAdapter(this, R.layout.district_spinneritem, districtNameList);
        fetchAllDistrictName();
        districtSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProjectDistrict.setAdapter(districtSpinnerAdapter);
        spinnerProjectDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    districtName = (String) parent.getItemAtPosition(position);
                    districtId = Integer.parseInt(districtIdList.get(position));
                    fetchTalukaName();
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
        fetchAllTalukaName();
        talukaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProjectTaluka.setAdapter(talukaSpinnerAdapter);
        spinnerProjectTaluka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    talukaName = (String) parent.getItemAtPosition(position);
                    talukaId = Integer.parseInt(talukaIdList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchAllDistrictName() {
        FetchAllDistrictDetails fetchAllDistrictDetails = new FetchAllDistrictDetails(this);
        fetchAllDistrictDetails.FetchAllDistrict(districtNameList, districtIdList, districtSpinnerAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitUpdateProject) {
            multipleSelectedValue = multiSelectionSpinner.getSelectedItemsAsString();
            collectUpdatedData();

            if (editTextProjectName.getText().toString().isEmpty() || editTextOldSurvey.getText().toString().isEmpty() ||
                    editTextNewSurvey.getText().toString().isEmpty() || editTextFileNumber.getText().toString().isEmpty() ||
                    editTextVillageName.getText().toString().isEmpty() || editTextProjectArea.getText().toString().isEmpty() ||
                    editTextProjectDescription.getText().toString().isEmpty()) {
                Toast.makeText(this, "All Details are neccessory", Toast.LENGTH_LONG).show();
            } else {
                projectDailogbox = ProgressDialog.show(UpdateProject.this, "", "Updating project details please wait...");
                UpdateProjectDetails();
            }
        }

    }

    private void collectUpdatedData() {
        tempProjectName = editTextProjectName.getText().toString();
        tempOldSurvey = editTextOldSurvey.getText().toString();
        tempNewSurvey = editTextNewSurvey.getText().toString();
        tempFileNumber = editTextFileNumber.getText().toString();
        tempVillageName = editTextVillageName.getText().toString();
        tempProjectArea = editTextProjectArea.getText().toString();
        tempProjectDescription = editTextProjectDescription.getText().toString();

    }

    private void UpdateProjectDetails() {
        UpdateProjectDetail updateProjectDetail = new UpdateProjectDetail(this);
        updateProjectDetail.UpdateProject(updateProjectId, companyId, tempProjectName, tempProjectDescription, serviceId,
                tempOldSurvey, tempNewSurvey, talukaId, districtId, tempFileNumber, tempVillageName, tempProjectArea, userId, projectDailogbox, multipleSelectedValue);
    }

    private void getProjectSpinnerItem() {
        projectArrayList = new String[]{
                "Select Project"
        };
        projectNameList = new ArrayList<>(Arrays.asList(projectArrayList));
        projectSpinnerAdapter = new ProjectSpinnerAdapter(this, R.layout.spinner_item, projectNameList);
        getProjectList();
        projectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnershowProject.setAdapter(projectSpinnerAdapter);
        spinnershowProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    projectName = parent.getItemAtPosition(position).toString();
                    projectId = projectIdList.get(position);

                    if (projectId != null) {
                        projectDetailsLinearLayout.setVisibility(View.VISIBLE);
                        updateProjectId = Integer.parseInt(projectId);
                        GetProjectDetails();
                        GetPreviousArchitecture();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProject.this);
                        builder.setTitle("Result");
                        builder.setMessage("Unable to get project details please try again later. ");
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                if (position > 0) {
                    companyName = parent.getItemAtPosition(position).toString();
                    companyId = Integer.parseInt(companyIdList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getArchitectureSpinnerItem() {
        ArchitectureDialogBox = ProgressDialog.show(this, "", "Fetching Architecture Please Wait...", true);
        ArchitectureUpdateAsyncCallWS task = new ArchitectureUpdateAsyncCallWS();
        task.execute();
    }

    private void getServiceTypeSpinnerItems() {
        serviceArrayList = new String[]{
                "Select ServiceType"
        };
        serviceNameList = new ArrayList<>(Arrays.asList(serviceArrayList));
        serviceSpinnerAdapter = new ServiceTypeSpinnerAdapter(this, R.layout.spinner_item, serviceNameList);
        getServiceTypeList();
        serviceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowServiceType.setAdapter(serviceSpinnerAdapter);
        spinnerShowServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    serviceName = parent.getItemAtPosition(position).toString();
                    serviceId = Integer.parseInt(serviceIdList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getProjectList() {
        projectDialogBox = ProgressDialog.show(UpdateProject.this, "", "Fetching details please wait...");
        FetchProjectList fetchProjectList = new FetchProjectList(this);
        fetchProjectList.GetAllProject(projectNameList, projectIdList, projectSpinnerAdapter, projectDialogBox);
    }

    private void GetProjectDetails() {
        projectDialogBox = ProgressDialog.show(UpdateProject.this, "", "Fetching details please wait...");
        ProjectDetailsAsyncTask task = new ProjectDetailsAsyncTask();
        task.execute();
    }

    private void GetPreviousArchitecture() {
        GetPreviousArchitecturesAsyncTask task = new GetPreviousArchitecturesAsyncTask();
        task.execute();
    }


    private void getCompanyList() {
        companyDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchClientsDetails fetchClientsDetails = new FetchClientsDetails(this);
        fetchClientsDetails.FetchAllClient(companyNameList, companyIdList, clientSpinnerAdapter, companyDialogBox);
    }

    private void getServiceTypeList() {
        serviceDialogBox = ProgressDialog.show(this, "", "Fetching details please wait...", true);
        FetchServiceType fetchServiceType = new FetchServiceType(this);
        fetchServiceType.GetAllServiceType(serviceIdList, serviceNameList, serviceSpinnerAdapter, serviceDialogBox);
    }

    public void fetchTalukaName() {
        saveUpdateDistrictID = districtId;
        talukaDailogbox = ProgressDialog.show(UpdateProject.this, "", "Fetching taluka details please wait...", true);
        TalukaAsyncCallWS talukaTask = new TalukaAsyncCallWS();
        talukaTask.execute();
    }



    @Override
    public void selectedIndices(List<Integer> indices) {
        //Toast.makeText(this, indices.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }

    public class ProjectDetailsAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            projectResponseResult = GetDataWebService.GetProjectById(projectWebMethod, updateProjectId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (projectResponseResult.equals("Error occured")) {
                projectDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProject.this);
                builder.setTitle("Result");
                builder.setMessage("Failed to fetch project details please try again ");
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
            } else if (projectResponseResult.equals("No Record Found")) {
                projectDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProject.this);
                builder.setTitle("Result");
                builder.setMessage("Project details not found");
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
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(projectResponseResult);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            tempProjectId = obj.getString("projectMasterId");
                            tempCompanyId = obj.getString("fullname");
                            tempServiceTypeId = obj.getString("serviceName");
                            tempDistrictName = obj.getString("districtName");
                            tempTalukaName = obj.getString("talukaName");
                            tempStateName = obj.getString("stateName");
                            tempProjectName = obj.getString("title");
                            tempOldSurvey = obj.getString("oldSurveyNo");
                            tempNewSurvey = obj.getString("NewSurveyNo");
                            tempFileNumber = obj.getString("fileNumber");
                            tempVillageName = obj.getString("VillageName");
                            tempProjectArea = obj.getString("ProjectArea");
                            tempProjectDescription = obj.getString("description");
                            tempStateId = obj.getString("state_MasterId");
                            tempDistrictId = obj.getString("district");
                            tempTalukaId = obj.getString("taluka");

                            editTextProjectName.setText(tempProjectName);
                            editTextOldSurvey.setText(tempOldSurvey);
                            editTextNewSurvey.setText(tempNewSurvey);
                            editTextFileNumber.setText(tempFileNumber);
                            editTextVillageName.setText(tempVillageName);
                            editTextProjectArea.setText(tempProjectArea);
                            editTextProjectDescription.setText(tempProjectDescription);


                            if (!tempCompanyId.equals(null)) {
                                int companyId = clientSpinnerAdapter.getPosition(tempCompanyId);
                                spinnershowCompany.setSelection(companyId);
                                clientSpinnerAdapter.notifyDataSetChanged();
                            }

                            if (!tempServiceTypeId.equals(null)) {
                                int serviceTypId = serviceSpinnerAdapter.getPosition(tempServiceTypeId);
                                spinnerShowServiceType.setSelection(serviceTypId);
                                serviceSpinnerAdapter.notifyDataSetChanged();
                            }
                            if (!tempDistrictId.equals(null)) {
                                int districtIdPos = districtSpinnerAdapter.getPosition(tempDistrictName);
                                spinnerProjectDistrict.setSelection(districtIdPos);
                                districtSpinnerAdapter.notifyDataSetChanged();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                projectDialogBox.dismiss();
            }
        }
    }

    public class ArchitectureUpdateAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            architectureResponseResult = GetDataWebService.GetAllArchitecture(architectureMethodName);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (architectureResponseResult.equals("No Network Found")) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UpdateProject.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to Fetch Architecture ");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                        Intent intent = new Intent(UpdateProject.this, UpdateProject.class);
                        startActivity(intent);
                    }
                });
                android.app.AlertDialog alert1 = builder.create();
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

                    String[] array = new String[architectureNameList.size()];
                    array = architectureNameList.toArray(array);
                    multiSelectionSpinner.setItems(array);
                    multiSelectionSpinner.setListener(UpdateProject.this);


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            if (ArchitectureDialogBox != null && ArchitectureDialogBox.isShowing()) {
                ArchitectureDialogBox.dismiss();
            }
        }
    }

    public class GetPreviousArchitecturesAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            prevArchitectureResponseResult = GetDataWebService.EditArchitectureForProject(prevArchitectureWebMethod, updateProjectId);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (prevArchitectureResponseResult.equals("Error occured")) {
                // projectDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProject.this);
                builder.setTitle("Result");
                builder.setMessage("Failed to fetch project information");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface alert, int which) {
                        // TODO Auto-generated method stub
                        //Do something
                        alert.dismiss();
                    }
                });
                AlertDialog alert2 = builder.create();
                alert2.show();
            } else if (prevArchitectureResponseResult.equals("No Record Found")) {
                // projectDialogBox.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProject.this);
                builder.setTitle("Result");
                builder.setMessage("Project details not found");
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
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(prevArchitectureResponseResult);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            PrevArchitectureNameList.add(obj.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    precSelectedArray = new String[PrevArchitectureNameList.size()];
                    precSelectedArray = PrevArchitectureNameList.toArray(precSelectedArray);
                    multiSelectionSpinner.setSelection(precSelectedArray);
                    multiSelectionSpinner.setListener(UpdateProject.this);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class TalukaAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            talukaResponseResult = GetDataWebService.GetAllTaluka(talukaMethodName, saveUpdateDistrictID);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (talukaResponseResult.equals("Taluka Not Found")) {
                talukaDailogbox.dismiss();
                companyNameList.clear();
                talukaIdList.add("0");
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProject.this);
                builder.setTitle("Result");
                builder.setMessage("Taluka not found");
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
                talukaDailogbox.dismiss();
                try {
                    talukaNameList.clear();
                    talukaIdList.clear();
                    JSONArray jsonArray = new JSONArray(talukaResponseResult);
                    talukaIdList.add("0");
                    talukaNameList.add("Select Taluka");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            talukaNameList.add(obj.getString("talukaName"));
                            talukaIdList.add(obj.getString("taluka_MasterId"));
                            districtSpinnerAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!tempTalukaId.equals(null)) {
                        int talukaIdPos = talukaSpinnerAdapter.getPosition(tempTalukaName);
                        spinnerProjectTaluka.setSelection(talukaIdPos);
                        talukaSpinnerAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
