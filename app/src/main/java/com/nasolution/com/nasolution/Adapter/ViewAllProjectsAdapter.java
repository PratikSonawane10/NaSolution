package com.nasolution.com.nasolution.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasolution.com.nasolution.Model.AllProjectItems;
import com.nasolution.com.nasolution.R;

import java.util.List;

public class ViewAllProjectsAdapter extends RecyclerView.Adapter<ViewAllProjectsAdapter.ViewHolder> {

    List<AllProjectItems> AllProjectLists;
    View v;
    ViewAllProjectsAdapter.ViewHolder viewHolder;

    public ViewAllProjectsAdapter(List<AllProjectItems> AllProjectLists) {
        this.AllProjectLists = AllProjectLists;

    }

    @Override
    public ViewAllProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_all_project_items, viewGroup, false);
        viewHolder = new ViewAllProjectsAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewAllProjectsAdapter.ViewHolder viewHolder, int i) {
        AllProjectItems AllProjectListsItems = AllProjectLists.get(i);
        viewHolder.bindPetList(AllProjectListsItems);
    }

    @Override
    public int getItemCount() {
        return AllProjectLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView projectName;
        public TextView projectArea;
        public TextView projectDescription;
        public TextView ProjectCode;
        public TextView projectfileNumber;
        public TextView projectNewSurveyNo;
        public TextView projectoldSurveyNo;
        public TextView projectVillageName;
        public View cardView;

        private AllProjectItems AllProjectListsItems;

        public ViewHolder(View itemView) {
            super(itemView);

            projectName = (TextView) itemView.findViewById(R.id.projectTitle);
            projectArea = (TextView) itemView.findViewById(R.id.projectArea);
            projectDescription = (TextView) itemView.findViewById(R.id.projectDescription);
            ProjectCode = (TextView) itemView.findViewById(R.id.ProjectCode);
            projectfileNumber = (TextView) itemView.findViewById(R.id.projectfileNumber);
            projectNewSurveyNo = (TextView) itemView.findViewById(R.id.projectNewSurveyNo);
            projectoldSurveyNo = (TextView) itemView.findViewById(R.id.projectoldSurveyNo);
            projectVillageName = (TextView) itemView.findViewById(R.id.projectVillageName);

            cardView = itemView;
            cardView.setOnClickListener(this);

        }

        public void bindPetList(AllProjectItems AllProjectListsItems) {
            this.AllProjectListsItems = AllProjectListsItems;

            projectName.setText(AllProjectListsItems.gettitle());
            projectArea.setText(AllProjectListsItems.getProjectArea());
            projectDescription.setText(AllProjectListsItems.getdescription());
            ProjectCode.setText(AllProjectListsItems.getProjectCode());
            projectfileNumber.setText(AllProjectListsItems.getfileNumber());
            projectNewSurveyNo.setText(AllProjectListsItems.getNewSurveyNo());
            projectoldSurveyNo.setText(AllProjectListsItems.getoldSurveyNo());
            projectVillageName.setText(AllProjectListsItems.getVillageName());
        }

        @Override
        public void onClick(View v) {
//            if (this.AllProjectListsItems != null) {
//                Intent petFullInformation = new Intent(v.getContext(), MyTask_Details.class);
//                petFullInformation.putExtra("TaskAssignMasterId", AllProjectListsItems.gettaskAssignMasterId());
//                petFullInformation.putExtra("projectname", AllProjectListsItems.getprojectname());
//                petFullInformation.putExtra("title ", AllProjectListsItems.gettitle());
//                petFullInformation.putExtra("projectname", AllProjectListsItems.getprojectname());
//                petFullInformation.putExtra("stagepercent", AllProjectListsItems.getstagepercent());
//                petFullInformation.putExtra("stageMasterId", AllProjectListsItems.getstageMasterId());
//                petFullInformation.putExtra("assignby", AllProjectListsItems.getassignby());
//                petFullInformation.putExtra("submiton", AllProjectListsItems.getsubmiton());
//                petFullInformation.putExtra("subStageId", AllProjectListsItems.getsubStageId());
//                petFullInformation.putExtra("count", AllProjectListsItems.getcount());
//                petFullInformation.putExtra("status", AllProjectListsItems.getstatus());
//                v.getContext().startActivity(petFullInformation);
//            }
        }
    }
}
