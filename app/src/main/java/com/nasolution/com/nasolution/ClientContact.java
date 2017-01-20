package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nasolution.com.nasolution.Adapter.ClientSpinnerAdapter;
import com.nasolution.com.nasolution.Connectivity.FetchClientsDetails;
import com.nasolution.com.nasolution.Connectivity.AddClientContacts;
import com.nasolution.com.nasolution.Connectivity.UpdateClientContacts;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientContact extends BaseActivity implements View.OnClickListener {

    private LinearLayout allFieldsLinearLayout;
    private RelativeLayout defaultLayout;
    private RelativeLayout relativeLayout;
    private EditText edittxtFullName;
    private EditText edittxtMobile;
    private Button btnAddMore;
    private Button btnSubmit;
    private Button btnCancel;
    private Spinner spinnerShowClient;
    public ProgressDialog progressDialog;
    public ProgressDialog clientProgressDialog;

    int k = -1;
    int flag;
    public static LinearLayout layout[] = new LinearLayout[100];
    public static EditText txtName[] = new EditText[100];
    public static EditText txtMobile[] = new EditText[100];
    public static Button btnRemove[] = new Button[100];

    private String clientID;
    private String clientName;
    private String[] clientArrayList;
    private ClientSpinnerAdapter clientSpinnerAdapter;
    private List<String> clientNameList = new ArrayList<String>();
    private List<String> clientIdList = new ArrayList<String>();

    private String updateClientID;
    private String updateCompanyName;
    private String addClientId;
    public String saveAllName;
    public String saveAllMobile;
    private int saveClientID;
    private int id;

    public StringBuilder strName;
    public StringBuilder strMobile;
    public StringBuilder strAppendName;
    public StringBuilder strAppendMobile;

    //Update ClientContact  Field
    public String tempName;
    public String tempMobile;
    public String clientContactResponse;
    public String contactWebMethod = "ViewClientContact";
    public ProgressDialog updateContactDialogBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        //passed Id from UpdateCompany
        updateClientID = getIntent().getStringExtra("UpdateClientID");
        updateCompanyName = getIntent().getStringExtra("UpdateCompanyName");

        //passed Id from AddCompany
        addClientId = getIntent().getStringExtra("COMPANY_ID");

        allFieldsLinearLayout = (LinearLayout) findViewById(R.id.fieldLinearLayout);
        defaultLayout = (RelativeLayout) findViewById(R.id.clientContactDefaultLayout);
        edittxtFullName = (EditText) findViewById(R.id.edittxtClientFullName);
        edittxtMobile = (EditText) findViewById(R.id.edittxtClientMobile);
        btnAddMore = (Button) findViewById(R.id.btnAddMoreFields);
        btnSubmit = (Button) findViewById(R.id.btnSubmitClientContact);
        spinnerShowClient = (Spinner) findViewById(R.id.clientCompanySpinner);

        if (addClientId != null) {
            saveClientID = Integer.parseInt(addClientId);
        } else {
            saveClientID = Integer.parseInt(updateClientID);
            updateContactDialogBox = ProgressDialog.show(this, "", "Fetching Contact Details Please Wait...", true);
            ClientContactAsyncCallWS contactTask = new ClientContactAsyncCallWS();
            contactTask.execute();
            defaultLayout.setVisibility(View.GONE);
        }

        btnAddMore.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        getClients();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddMoreFields) {
            collectAllData();
            addMoreFields();
        } else if ((v.getId() == R.id.btnSubmitClientContact)) {
            if (addClientId != null) {
                collectAllData();
                uplaodAllDetails();
            } else {
                collectAllData();
                uplaodUpdateDetails();
            }
        }
    }

    private void collectAllData() {

        strName = new StringBuilder();
        strMobile = new StringBuilder();
        strAppendName = new StringBuilder();
        strAppendMobile = new StringBuilder();

        for (int i = 0; i <= k; i++) {
            if (!txtName[i].getText().toString().equals("null") || !txtMobile[i].getText().toString().equals("null")) {
                strName.append(txtName[i].getText().toString() + ",");
                strMobile.append(txtMobile[i].getText().toString() + ",");
            }
        }
        if (addClientId != null) {
            strAppendName.append(edittxtFullName.getText().toString() + "," + strName);
            strAppendMobile.append(edittxtMobile.getText().toString() + "," + strMobile);
        } else {
            strAppendName = strName;
            strAppendMobile = strMobile;
        }

        saveAllName = strAppendName.toString();
        saveAllMobile = strAppendMobile.toString();
    }

    private void addMoreFields() {
        try {
            k++;
            flag = k;
            final LinearLayout.LayoutParams lparams;
            lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layout[flag] = new LinearLayout(ClientContact.this);
            layout[flag].setLayoutParams(lparams);
            layout[flag].setId(flag);

            txtName[flag] = new EditText(ClientContact.this);
            txtName[flag].setLayoutParams(lparams);
            txtName[flag].setHint("full name");
            txtName[flag].setId(flag);
            if (updateClientID != null) {
                txtName[flag].setText(tempName);
            }
            tempName = null;

            txtMobile[flag] = new EditText(ClientContact.this);
            txtMobile[flag].setLayoutParams(lparams);
            txtMobile[flag].setHint("mobile no");
            txtMobile[flag].setId(flag);
            if (updateClientID != null) {
                txtMobile[flag].setText(tempMobile);
            }
            tempMobile = null;

            btnRemove[flag] = new Button(ClientContact.this);
            btnRemove[flag].setLayoutParams(lparams);
            btnRemove[flag].setText("Remove");
            btnRemove[flag].setAllCaps(false);
            btnRemove[flag].setBackground(null);
            btnRemove[flag].setTextColor(getResources().getColor(R.color.btnColor));
            btnRemove[flag].setId(flag);
            btnRemove[flag].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = k;
                    id = v.getId();
                    layout[id] = (LinearLayout) v.getParent();
                    txtMobile[id].setText("null");
                    txtName[id].setText("null");
                    //removeData(id, v);
                    layout[id].removeView(txtName[id]);
                    layout[id].removeView(txtMobile[id]);
                    allFieldsLinearLayout.removeView(layout[id]);
                    collectAllData();
                    for (int i = 0; i < 5; i++) {
                        Toast.makeText(ClientContact.this, "n = " + saveAllName + "  " + "m = " + saveAllMobile, Toast.LENGTH_LONG).show();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        layout[flag].addView(txtName[flag]);
        layout[flag].addView(txtMobile[flag]);
        layout[flag].addView(btnRemove[flag]);
        allFieldsLinearLayout.addView(layout[flag]);
    }

    private void getClients() {

        clientArrayList = new String[]{
                "Select Client "
        };
        clientNameList = new ArrayList<>(Arrays.asList(clientArrayList));
        clientSpinnerAdapter = new ClientSpinnerAdapter(this, R.layout.client_spinner_item, clientNameList);
        progressDialog = ProgressDialog.show(this, "", "Fetching Company Details Please Wait...", true);
        fetchClientList();
        clientSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowClient.setAdapter(clientSpinnerAdapter);
        spinnerShowClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    clientName = parent.getItemAtPosition(position).toString();
                    clientID = clientIdList.get(position);
                    clientSpinnerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fetchClientList() {
        FetchClientsDetails fetchClientsDetails = new FetchClientsDetails(this);
        fetchClientsDetails.FetchClient(clientNameList, saveClientID, clientIdList, clientSpinnerAdapter, progressDialog);
    }

    private boolean checkAllField() {
        boolean textval = false;

        for (int i = 0; i <= k; i++) {
            if (TextUtils.isEmpty(txtName[i].getText().toString()) || TextUtils.isEmpty(txtMobile[i].getText().toString())) {
                textval = true;
                break;
            } else {
                textval = false;
            }
        }
        return textval;
    }

    private void uplaodAllDetails() {

        if (edittxtFullName.getText().toString().isEmpty() || edittxtMobile.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show();
        } else if (checkAllField() == true) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show();
        } else {
            if (clientID == null) {
                Toast.makeText(ClientContact.this, "Please select client name", Toast.LENGTH_LONG).show();
            } else {
                saveClientID = Integer.parseInt(clientID);
                clientProgressDialog = ProgressDialog.show(ClientContact.this, "", "Adding contact information please wait...", true);
                AddClientContacts addClientContacts = new AddClientContacts(this);
                addClientContacts.UploadClientContact(saveClientID, saveAllName, saveAllMobile, clientProgressDialog);
            }
        }
    }

    private void uplaodUpdateDetails() {
        if (checkAllField() == true) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show();
        } else {
            if (clientID == null) {
                Toast.makeText(ClientContact.this, "Please select client name", Toast.LENGTH_LONG).show();
            } else {
                saveClientID = Integer.parseInt(clientID);
                clientProgressDialog = ProgressDialog.show(ClientContact.this, "", "Updating contact information please wait...", true);
                UpdateClientContacts updateClientContacts = new UpdateClientContacts(this);
                updateClientContacts.UpdateClientContact(saveClientID, saveAllName, saveAllMobile, clientProgressDialog);
            }
        }
    }

    public class ClientContactAsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            clientContactResponse = GetDataWebService.GetClientContact(contactWebMethod, saveClientID);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (clientContactResponse.equals("No Record Found")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClientContact.this);
                builder.setTitle("Result");
                builder.setMessage("Contact details not found");
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
                    JSONArray jsonArray = new JSONArray(clientContactResponse);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            tempName = obj.getString("name");
                            tempMobile = obj.getString("mobile");
                            addMoreFields();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            if (updateContactDialogBox != null && updateContactDialogBox.isShowing()) {
                updateContactDialogBox.dismiss();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ClientContact.this);
        builder.setTitle("Result");
        builder.setMessage("Please upload contact details");
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
}