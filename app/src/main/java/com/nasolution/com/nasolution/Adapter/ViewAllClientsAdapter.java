package com.nasolution.com.nasolution.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasolution.com.nasolution.Model.AllClientsItems;
import com.nasolution.com.nasolution.MyTask_Details;
import com.nasolution.com.nasolution.R;

import java.util.List;

public class ViewAllClientsAdapter extends RecyclerView.Adapter<ViewAllClientsAdapter.ViewHolder> {

    List<AllClientsItems> AllClientsLists;
    View v;
    ViewHolder viewHolder;

    public ViewAllClientsAdapter(List<AllClientsItems> AllClientsLists) {
        this.AllClientsLists = AllClientsLists;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_all_clients_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewAllClientsAdapter.ViewHolder viewHolder, int i) {
        AllClientsItems AllClientsListsItems = AllClientsLists.get(i);
        viewHolder.bindPetList(AllClientsListsItems);
    }

    @Override
    public int getItemCount() {
        return AllClientsLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView clientName;
        public TextView clientAddress;
        public TextView clientCity;
        public TextView clientEmail;
        public View cardView;

        private AllClientsItems AllClientsListsItems;

        public ViewHolder(View itemView) {
            super(itemView);

            clientName = (TextView) itemView.findViewById(R.id.clientName);
            clientAddress = (TextView) itemView.findViewById(R.id.clientAddress);
            clientCity = (TextView) itemView.findViewById(R.id.clientCity);
            clientEmail = (TextView) itemView.findViewById(R.id.clientEmail);

            cardView = itemView;
            cardView.setOnClickListener(this);

        }

        public void bindPetList(AllClientsItems AllClientsListsItems) {
            this.AllClientsListsItems = AllClientsListsItems;

            clientName.setText(AllClientsListsItems.getclientname());
            clientAddress.setText(AllClientsListsItems.getaddres());
            clientCity.setText(AllClientsListsItems.getcity());
            clientEmail.setText(AllClientsListsItems.getemail());
        }

        @Override
        public void onClick(View v) {
//            if (this.AllClientsListsItems != null) {
//                Intent petFullInformation = new Intent(v.getContext(), MyTask_Details.class);
//                petFullInformation.putExtra("TaskAssignMasterId", AllClientsListsItems.gettaskAssignMasterId());
//                petFullInformation.putExtra("clientname", AllClientsListsItems.getclientname());
//                petFullInformation.putExtra("title ", AllClientsListsItems.gettitle());
//                petFullInformation.putExtra("projectname", AllClientsListsItems.getprojectname());
//                petFullInformation.putExtra("stagepercent", AllClientsListsItems.getstagepercent());
//                petFullInformation.putExtra("stageMasterId", AllClientsListsItems.getstageMasterId());
//                petFullInformation.putExtra("assignby", AllClientsListsItems.getassignby());
//                petFullInformation.putExtra("submiton", AllClientsListsItems.getsubmiton());
//                petFullInformation.putExtra("subStageId", AllClientsListsItems.getsubStageId());
//                petFullInformation.putExtra("count", AllClientsListsItems.getcount());
//                petFullInformation.putExtra("status", AllClientsListsItems.getstatus());
//                v.getContext().startActivity(petFullInformation);
//            }
        }
    }
}
