package com.nasolution.com.nasolution.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nasolution.com.nasolution.AddProject;
import com.nasolution.com.nasolution.AddSubStage;
import com.nasolution.com.nasolution.AddTask;
import com.nasolution.com.nasolution.Architecture;
import com.nasolution.com.nasolution.StageForm;
import com.nasolution.com.nasolution.DeleteSubStage;
import com.nasolution.com.nasolution.UpdateStageForm;
import com.nasolution.com.nasolution.UpdateSubStage;
import com.nasolution.com.nasolution.UpdateTask;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter {

    public CustomSpinnerAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CustomSpinnerAdapter(Architecture architecture, int district_spinneritem, List<String> professionNameList) {
        super(architecture, district_spinneritem, professionNameList);
    }

    public CustomSpinnerAdapter(AddProject addProject, int spinner_item, List<String> companyNameList) {
        super(addProject, spinner_item, companyNameList);
    }

    public CustomSpinnerAdapter(StageForm stageForm, int spinner_item, List<String> stageNameList) {
        super(stageForm, spinner_item, stageNameList);
    }

    public CustomSpinnerAdapter(AddSubStage addSubStage, int spinner_item, List<String> projectNameList) {
        super(addSubStage, spinner_item, projectNameList);
    }

    public CustomSpinnerAdapter(DeleteSubStage deleteSubStage, int spinner_item, List<String> subStageNameList) {
        super(deleteSubStage, spinner_item, subStageNameList);
    }

    public CustomSpinnerAdapter(UpdateSubStage updateSubStage, int spinner_item, List<String> projectNameList) {
        super(updateSubStage, spinner_item, projectNameList);
    }

    public CustomSpinnerAdapter(AddTask addTask, int simple_spinner_item, List<String> userNameList) {
        super(addTask, simple_spinner_item, userNameList);
    }

    public CustomSpinnerAdapter(UpdateTask updateTask, int simple_spinner_item, List<String> spinnerArray) {
        super(updateTask, simple_spinner_item, spinnerArray);
    }

    public CustomSpinnerAdapter(UpdateStageForm updateStageForm, int spinner_item, List<String> stageNameList) {
        super(updateStageForm, spinner_item, stageNameList);
    }

    @Override
    public boolean isEnabled(int position) {
        if(position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if(position == 0){
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        else {
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        return view;
    }
}
