package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nasolution.com.nasolution.Adapter.ExpandableListAdapter;
import com.nasolution.com.nasolution.SessionManager.SessionManager;
import com.nasolution.com.nasolution.WebServices.GetDataWebService;
import com.nasolution.com.nasolution.WebServices.InsertDataWebservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected ActionBarDrawerToggle drawerToggle;
    SessionManager sessionManager;
    protected DrawerLayout drawer;
    protected FrameLayout frameLayout;
    protected LinearLayout linearLayout;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
//Drawer
    public List<String> listDataTitle = new ArrayList<String>();
    public HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

    List<String> Project = new ArrayList<String>();
    List<String> Architecture = new ArrayList<String>();
    List<String> Task = new ArrayList<String>();
    List<String> Client = new ArrayList<String>();
    List<String> Master = new ArrayList<String>();
    List<String> User = new ArrayList<String>();

    public List<Integer> listDataImages;
    public int checkGroupPosition;
    public int checkChildPositin;
    public int projectPosition;
    public int architectPosition;
    public int clientPosition;
    public int taskPosition;
    public int masterPosition;
    public int userPosition;

    int requestcode = 0 ;
    String userName;
    String userRole;
    String userPassword;
    public String rolesResponseResult;
    public String userRoleWebMethod = "GetAllUserRole";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        HashMap<String, String> userRole = sessionManager.getUserDetails();
        userName = userRole.get(SessionManager.KEY_EMAIL);
        userPassword = userRole.get(SessionManager.KEY_USERPASSWORD);

        progressDialog = ProgressDialog.show(BaseActivity.this, "", "Getting details  please wait...");
        FetchRolesAsyncTask task = new FetchRolesAsyncTask();
        task.execute();

        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer, null);
        frameLayout = (FrameLayout) drawer.findViewById(R.id.contentFrame);
        expListView = (ExpandableListView) drawer.findViewById(R.id.drawerListview);
        prepareDefaultData();
        listAdapter = new ExpandableListAdapter(this, listDataTitle, listDataChild,drawer, listDataImages);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                checkGroupPosition = groupPosition;
                checkChildPositin = childPosition;
                ClickChildItems();
                return true;
            }
        });

        drawer.setClickable(true);
        listAdapter.notifyDataSetChanged();
        expListView.setAdapter(listAdapter);

        toolbar = (Toolbar) drawer.findViewById(R.id.app_bar);
        if (toolbar != null) {
              setSupportActionBar(toolbar);
              getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        super.setContentView(drawer);
    }

    private void prepareDefaultData() {
        listDataTitle = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataTitle.add("Home");

        // Adding child data
        List<String> homePage = new ArrayList<String>();
        homePage.add("Dashboard");

        listDataChild.put(listDataTitle.get(0), homePage);
    }

    private void ClickChildItems() {
        final String selected = (String) listAdapter.getChild(checkGroupPosition, checkChildPositin);

        if(selected.equals("Dashboard")){
            drawer.closeDrawers();
            Intent homeIntent = new Intent(this,Home.class);
            startActivity(homeIntent);
        }
        else if(selected.equals("Add Project")){
            drawer.closeDrawers();
            Intent addProjectIntent = new Intent(this,AddProject.class);
            startActivity(addProjectIntent);
        }
        else if(selected.equals("Edit Project")){
            drawer.closeDrawers();
            Intent updateProjectIntent = new Intent(this,UpdateProject.class);
            startActivity(updateProjectIntent);
        }
        else if(selected.equals("Delete Project")){
            drawer.closeDrawers();
            Intent deleteProjectIntent = new Intent(this,DeleteProject.class);
            startActivity(deleteProjectIntent);
        }
        else if(selected.equals("Add SubStage")){
            drawer.closeDrawers();
            Intent addSubStage = new Intent(this,AddSubStage.class);
            startActivity(addSubStage);
        }
        else if(selected.equals("Edit SubStage")){
            drawer.closeDrawers();
            Intent updateSubStage = new Intent(this,UpdateSubStage.class);
            startActivity(updateSubStage);
        }
        else if(selected.equals("Delete SubStage")){
            drawer.closeDrawers();
            Intent deleteSubStage = new Intent(this,DeleteSubStage.class);
            startActivity(deleteSubStage);
        }
        else if(selected.equals("Add Client")){
            drawer.closeDrawers();
            Intent clientIntent = new Intent(this,AddCompany.class);
            clientIntent.putExtra("AddClient","AddClient");
            startActivity(clientIntent);
        }
        else if(selected.equals("Edit Client")){
            drawer.closeDrawers();
            Intent clientIntent = new Intent(this,UpdateCompany.class);
            clientIntent.putExtra("EditClient","EditClient");
            startActivity(clientIntent);
        }
        else if(selected.equals("Delete Client")){
            drawer.closeDrawers();
            Intent clientIntent = new Intent(this,DeleteCompany.class);
            clientIntent.putExtra("DeleteClient","DeleteClient");
            startActivity(clientIntent);
        }
        else if(selected.equals("Add Architect")){
            drawer.closeDrawers();
            String addValue = "AddArchitec";
            Intent addArchitect = new Intent(this,Architecture.class);
            addArchitect.putExtra("Add",addValue);
            startActivity(addArchitect);
        }
        else if(selected.equals("Edit Architect")){
            drawer.closeDrawers();
            String editValue = "EditArchitec";
            Intent updateArchitect = new Intent(this,Architecture.class);
            updateArchitect.putExtra("Edit",editValue);
            startActivity(updateArchitect);
        }
        else if(selected.equals("Delete Architect")){
            drawer.closeDrawers();
            String deleteValue = "DeleteArchitec";
            Intent deleteArchitect = new Intent(this,Architecture.class);
            deleteArchitect.putExtra("Delete",deleteValue);
            startActivity(deleteArchitect);
        }
        else if(selected.equals("Add Task")){
            drawer.closeDrawers();
            Intent addTaskIntent = new Intent(this,AddTask.class);
            startActivity(addTaskIntent);
        }
        else if(selected.equals("Edit Task")){
            drawer.closeDrawers();
            Intent editTaskIntent = new Intent(this,UpdateTask.class);
            startActivity(editTaskIntent);
        }
        else if(selected.equals("Delete Task")){
            drawer.closeDrawers();
            Intent deleteTaskIntent = new Intent(this,DeleteTask.class);
            startActivity(deleteTaskIntent);
        }
        else if(selected.equals("Add User")){
            drawer.closeDrawers();
            Intent addUser = new Intent(this,AddUser.class);
            startActivity(addUser);
        }
        /*else if(selected.equals("Edit User")){
            drawer.closeDrawers();
           // Intent updateUser = new Intent(this,UpdateUser.class);
          //  startActivity(updateUser);
        }*/
        else if(selected.equals("State Master")){
            drawer.closeDrawers();
            Intent stateMasterIntent = new Intent(this,StateMaster.class);
            startActivity(stateMasterIntent);
        }
        else if(selected.equals("District Master")){
            drawer.closeDrawers();
            Intent districtMasterIntent = new Intent(this,DistrictMaster.class);
            startActivity(districtMasterIntent);
        }
        else if(selected.equals("Taluka Master")){
            drawer.closeDrawers();
            Intent talukaMasterIntent = new Intent(this,TalukaMaster.class);
            startActivity(talukaMasterIntent);
        }
    }

    public void setContentView(int layoutId) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutId, null, false);
        drawer.addView(contentView,0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id ==  R.id.logoutMenu){
            sessionManager.logoutUser();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void prepareListData() {
        listDataTitle = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataTitle.add("Project");
        listDataTitle.add("Client");
        listDataTitle.add("Architecture");
        listDataTitle.add("Task");
        listDataTitle.add("Master");

        // Adding child data
        List<String> Project = new ArrayList<String>();
        Project.add("Add Project");
        Project.add("Edit Project");
        Project.add("Delete Project");
        Project.add("Add SubStage");
        Project.add("Edit SubStage");
        Project.add("Delete SubStage");

        List<String> Client = new ArrayList<String>();
        Client.add("Add Client");
        Client.add("Edit Client");
        Client.add("Delete Client");

        List<String> Architecture = new ArrayList<String>();
        Architecture.add("Add Architect");
        Architecture.add("Edit Architect");
        Architecture.add("Delete Architect");

        List<String> Task = new ArrayList<String>();
        Task.add("Add Task");
        Task.add("Edit Task");
        Task.add("Delete Task");

        List<String> Master = new ArrayList<String>();
        Master.add("State Master");
        Master.add("District Master");
        Master.add("Taluka Master");

        listDataChild.put(listDataTitle.get(0), Project);
        listDataChild.put(listDataTitle.get(1),Client);
        listDataChild.put(listDataTitle.get(2),Architecture);
        listDataChild.put(listDataTitle.get(3),Task);
        listDataChild.put(listDataTitle.get(4), Master);
    }

    public class FetchRolesAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            rolesResponseResult = GetDataWebService.FetchRoles(userName, userPassword, userRoleWebMethod);
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if(rolesResponseResult.equals("Invalid UserName & Password")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                builder.setMessage("Unable to fetch details");
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
            else if(rolesResponseResult.equals("No Network Found")) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                builder.setTitle("Result");
                builder.setMessage("Unable to login. Please try again later.");
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
                    JSONArray jArr = new JSONArray(rolesResponseResult);
                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject obj = jArr.getJSONObject(count);
                        userRole = obj.getString("menuName");
                        prepareDrawerDetails();
                      //  requestcode = 1;
                      //  sessionManager.createRequestCode(requestcode);
                    }
                    listAdapter.notifyDataSetChanged();
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }
    }

    private void prepareDrawerDetails() {

        if(userRole.equals("Project")){
            listDataTitle.add("Project");
            projectPosition = listDataTitle.indexOf("Project");
        }
        else if(userRole.equals("Architect")){
            listDataTitle.add("Architecture");
            architectPosition = listDataTitle.indexOf("Architecture");
        }
        else if(userRole.equals("Client")){
            listDataTitle.add("Client");
            clientPosition = listDataTitle.indexOf("Client");
        }
        else if(userRole.equals("Task")){
            listDataTitle.add("Task");
            taskPosition = listDataTitle.indexOf("Task");
        }
        else if(userRole.equals("User")){
            listDataTitle.add("User");
            userPosition = listDataTitle.indexOf("User");
        }
        else if(userRole.equals("Master")){
            listDataTitle.add("Master");
            masterPosition = listDataTitle.indexOf("Master");
        }

        if(projectPosition != 0){
            if (userRole.equals("Add Project")) {
                Project.add("Add Project");
            }
            else if (userRole.equals("Edit Project")) {
                Project.add("Edit Project");
            }
            else if (userRole.equals("View Project")) {
                Project.add("Delete Project");
            }
            else if (userRole.equals("AddSubStage")) {
                Project.add("Add SubStage");
            }
            else if (userRole.equals("ViewSubStage")) {
                Project.add("Edit SubStage");
                Project.add("Delete SubStage");
            }
            listDataChild.put(listDataTitle.get(projectPosition), Project);
        }

        if(architectPosition != 0){
            if(userRole.equals("Add Architect")){
                Architecture.add("Add Architect");

            }else if(userRole.equals("View Architect")){
                Architecture.add("Edit Architect");
            }
            else if(userRole.equals("View Architect")){
                Architecture.add("Delete Architect");
            }
            listDataChild.put(listDataTitle.get(architectPosition),Architecture);
        }

        if(clientPosition != 0) {
            if (userRole.equals("Add Client")) {
                Client.add("Add Client");
                listDataChild.put(listDataTitle.get(clientPosition), Client);
            } else if (userRole.equals("Edit Client")) {
                Client.add("Edit Client");
                listDataChild.put(listDataTitle.get(clientPosition), Client);
            } else if (userRole.equals("Delete Client")) {
                Client.add("Delete Client");
                listDataChild.put(listDataTitle.get(clientPosition), Client);
            }
        }

        if(userPosition != 0) {
            if (userRole.equals("Add User")) {
                User.add("Add User");
            }
            /*else  if(userRole.equals("Edit User")) {
                User.add("Edit User");
            }*/
            listDataChild.put(listDataTitle.get(userPosition), User);
        }

        if(taskPosition != 0){
            if(userRole.equals("Add Task")){
                Task.add("Add Task");
            }
            else if(userRole.equals("Edit Task")){
                Task.add("Edit Task");
            }
            else if(userRole.equals("View Task")){
                Task.add("Delete Task");
            }
            listDataChild.put(listDataTitle.get(taskPosition),Task);
        }

        if(masterPosition != 0){
            if(userRole.equals("Role Master")){
                Master.add("State Master");
            }
            else if(userRole.equals("District Master")){
                Master.add("District Master");
            }
            else if(userRole.equals("Taluka Master")){
                Master.add("Taluka Master");
            }
            listDataChild.put(listDataTitle.get(masterPosition),Master);
        }
    }
}
