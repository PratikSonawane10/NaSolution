package com.nasolution.com.nasolution;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.Adapter.ViewAllProjectsAdapter;
import com.nasolution.com.nasolution.Adapter.ViewAllUsersAdapter;
import com.nasolution.com.nasolution.Connectivity.Fetch_AllProjects;
import com.nasolution.com.nasolution.Connectivity.Fetch_AllUsers;
import com.nasolution.com.nasolution.Model.AllProjectItems;
import com.nasolution.com.nasolution.Model.AllUsersItems;
import com.nasolution.com.nasolution.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class View_All_Project extends BaseActivity {

    public List<AllProjectItems> allProjectItems = new ArrayList<AllProjectItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;

    int user_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_project);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        recyclerView = (RecyclerView) findViewById(R.id.viewAllProject);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new ViewAllProjectsAdapter(allProjectItems);
        recyclerView.setAdapter(reviewAdapter);

        try {
            Fetch_AllProjects fetch_AllProjects = new Fetch_AllProjects(View_All_Project.this);
            fetch_AllProjects.Fetch_AllProjects(allProjectItems,recyclerView, reviewAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

