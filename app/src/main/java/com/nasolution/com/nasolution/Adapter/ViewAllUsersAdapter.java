package com.nasolution.com.nasolution.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasolution.com.nasolution.Model.AllUsersItems;
import com.nasolution.com.nasolution.R;

import java.util.List;

public class ViewAllUsersAdapter extends RecyclerView.Adapter<ViewAllUsersAdapter.ViewHolder> {

    List<AllUsersItems> AllUsersLists;
    View v;
    ViewAllUsersAdapter.ViewHolder viewHolder;

    public ViewAllUsersAdapter(List<AllUsersItems> AllUsersLists) {
        this.AllUsersLists = AllUsersLists;

    }

    @Override
    public ViewAllUsersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_all_users_items, viewGroup, false);
        viewHolder = new ViewAllUsersAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewAllUsersAdapter.ViewHolder viewHolder, int i) {
        AllUsersItems AllUsersListsItems = AllUsersLists.get(i);
        viewHolder.bindPetList(AllUsersListsItems);
    }

    @Override
    public int getItemCount() {
        return AllUsersLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView userName;
        public TextView userAddress;
        public TextView userEmail;
        public TextView userMobile;
        public TextView userempCode;
        public View cardView;

        private AllUsersItems AllUsersListsItems;

        public ViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.userName);
            userAddress = (TextView) itemView.findViewById(R.id.userAddress);
            userEmail = (TextView) itemView.findViewById(R.id.userEmail);
            userMobile = (TextView) itemView.findViewById(R.id.userMobile);
            userempCode = (TextView) itemView.findViewById(R.id.userempCode);

            cardView = itemView;
            cardView.setOnClickListener(this);

        }

        public void bindPetList(AllUsersItems AllUsersListsItems) {
            this.AllUsersListsItems = AllUsersListsItems;

            userName.setText(AllUsersListsItems.getusername());
            userAddress.setText(AllUsersListsItems.getaddres());
            userEmail.setText(AllUsersListsItems.getemail());
            userMobile.setText(AllUsersListsItems.getmobile());
            userempCode.setText(AllUsersListsItems.getemp_code());
        }

        @Override
        public void onClick(View v) {
//            if (this.AllUsersListsItems != null) {
//                Intent petFullInformation = new Intent(v.getContext(), MyTask_Details.class);
//                petFullInformation.putExtra("TaskAssignMasterId", AllUsersListsItems.gettaskAssignMasterId());
//                petFullInformation.putExtra("username", AllUsersListsItems.getusername());
//                petFullInformation.putExtra("title ", AllUsersListsItems.gettitle());
//                petFullInformation.putExtra("projectname", AllUsersListsItems.getprojectname());
//                petFullInformation.putExtra("stagepercent", AllUsersListsItems.getstagepercent());
//                petFullInformation.putExtra("stageMasterId", AllUsersListsItems.getstageMasterId());
//                petFullInformation.putExtra("assignby", AllUsersListsItems.getassignby());
//                petFullInformation.putExtra("submiton", AllUsersListsItems.getsubmiton());
//                petFullInformation.putExtra("subStageId", AllUsersListsItems.getsubStageId());
//                petFullInformation.putExtra("count", AllUsersListsItems.getcount());
//                petFullInformation.putExtra("status", AllUsersListsItems.getstatus());
//                v.getContext().startActivity(petFullInformation);
//            }
        }
    }
}
