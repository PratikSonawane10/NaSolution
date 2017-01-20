package com.nasolution.com.nasolution.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasolution.com.nasolution.MyTask_Details;
import com.nasolution.com.nasolution.R;
import com.nasolution.com.nasolution.Model.MyTaskListItems;
import java.util.List;

public class MyTaskListAdapter  extends RecyclerView.Adapter<MyTaskListAdapter.ViewHolder> {

    List<MyTaskListItems> myTaskLists;
    View v;
    ViewHolder viewHolder;

    public MyTaskListAdapter(List<MyTaskListItems> myTaskLists) {
        this.myTaskLists = myTaskLists;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_task_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MyTaskListItems myTaskListsItems = myTaskLists.get(i);
        viewHolder.bindPetList(myTaskListsItems);
    }

    @Override
    public int getItemCount() {
        return myTaskLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView date;
        public TextView projectName;
        public TextView clientName;
        public TextView title;
        public View cardView;

        private MyTaskListItems myTaskListsItems;

        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            projectName = (TextView) itemView.findViewById(R.id.projectName);
            clientName = (TextView) itemView.findViewById(R.id.clientName);
            title = (TextView) itemView.findViewById(R.id.title);
            cardView = itemView;
            cardView.setOnClickListener(this);

        }

        public void bindPetList(MyTaskListItems myTaskListsItems) {
            this.myTaskListsItems = myTaskListsItems;

            date.setText(myTaskListsItems.getsubmiton());
            projectName.setText(myTaskListsItems.getprojectname());
            clientName.setText(myTaskListsItems.getclientname());
            title.setText(myTaskListsItems.gettitle());
            //projectName.setText("Posted By : " + myTaskListsItems.getPetPostOwner());
        }

        @Override
        public void onClick(View v) {
            if (this.myTaskListsItems != null) {
                Intent petFullInformation = new Intent(v.getContext(), MyTask_Details.class);
                petFullInformation.putExtra("TaskAssignMasterId", myTaskListsItems.gettaskAssignMasterId());
                petFullInformation.putExtra("clientname", myTaskListsItems.getclientname());
                petFullInformation.putExtra("title ", myTaskListsItems.gettitle());
                petFullInformation.putExtra("projectname", myTaskListsItems.getprojectname());
                petFullInformation.putExtra("stagepercent", myTaskListsItems.getstagepercent());
                petFullInformation.putExtra("stageMasterId", myTaskListsItems.getstageMasterId());
                petFullInformation.putExtra("assignby", myTaskListsItems.getassignby());
                petFullInformation.putExtra("submiton", myTaskListsItems.getsubmiton());
                petFullInformation.putExtra("subStageId", myTaskListsItems.getsubStageId());
                petFullInformation.putExtra("count", myTaskListsItems.getcount());
                petFullInformation.putExtra("status", myTaskListsItems.getstatus());
                v.getContext().startActivity(petFullInformation);
            }
        }
    }
}
