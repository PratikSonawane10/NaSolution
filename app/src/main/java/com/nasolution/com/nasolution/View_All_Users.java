package com.nasolution.com.nasolution;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.Adapter.ViewAllUsersAdapter;
import com.nasolution.com.nasolution.Connectivity.Fetch_AllUsers;
import com.nasolution.com.nasolution.Model.AllUsersItems;
import com.nasolution.com.nasolution.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class View_All_Users  extends BaseActivity {

    public List<AllUsersItems> allUsersItems = new ArrayList<AllUsersItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;

    int user_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_users);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        user_id = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        recyclerView = (RecyclerView) findViewById(R.id.viewAllUsers);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new ViewAllUsersAdapter(allUsersItems);
        recyclerView.setAdapter(reviewAdapter);

        try {
            Fetch_AllUsers fetch_AllUsers = new Fetch_AllUsers(View_All_Users.this);
            fetch_AllUsers.Fetch_AllUsersDetails(allUsersItems,recyclerView, reviewAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

