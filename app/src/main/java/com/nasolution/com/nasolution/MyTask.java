package com.nasolution.com.nasolution;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nasolution.com.nasolution.Adapter.MyTaskListAdapter;
import com.nasolution.com.nasolution.Connectivity.Fetch_MyTask;
import com.nasolution.com.nasolution.SessionManager.SessionManager;
import com.nasolution.com.nasolution.Model.MyTaskListItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTask extends BaseActivity {

    public List<MyTaskListItems> myTaskListItems = new ArrayList<MyTaskListItems>();
    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;

    int user_id =6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_task);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        //user_id = Integer.parseInt(user.get(SessionManager.KEY_USER_ID));

        recyclerView = (RecyclerView) findViewById(R.id.myTaskList);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.smoothScrollToPosition(0);
        reviewAdapter = new MyTaskListAdapter(myTaskListItems);
        recyclerView.setAdapter(reviewAdapter);

        try {
            Fetch_MyTask fetch_MyTask = new Fetch_MyTask(MyTask.this);
            fetch_MyTask.Fetch_MyTaskItems(myTaskListItems,recyclerView, reviewAdapter,user_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

