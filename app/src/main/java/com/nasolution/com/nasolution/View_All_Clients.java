package com.nasolution.com.nasolution;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.Adapter.MyTaskListAdapter;
import com.nasolution.com.nasolution.Adapter.ViewAllClientsAdapter;
import com.nasolution.com.nasolution.Connectivity.Fetch_AllClients;
import com.nasolution.com.nasolution.Connectivity.Fetch_MyTask;
import com.nasolution.com.nasolution.Model.AllClientsItems;
import com.nasolution.com.nasolution.Model.MyTaskListItems;
import com.nasolution.com.nasolution.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class View_All_Clients extends BaseActivity {

    public List<AllClientsItems> allClientsItems = new ArrayList<AllClientsItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;

    int user_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_clients);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        recyclerView = (RecyclerView) findViewById(R.id.viewAllClients);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new ViewAllClientsAdapter(allClientsItems);
        recyclerView.setAdapter(reviewAdapter);

        try {
            Fetch_AllClients fetch_AllClients = new Fetch_AllClients(View_All_Clients.this);
            fetch_AllClients.Fetch_AllClientsDetails(allClientsItems,recyclerView, reviewAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

