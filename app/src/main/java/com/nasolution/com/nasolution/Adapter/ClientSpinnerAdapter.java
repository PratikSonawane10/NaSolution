package com.nasolution.com.nasolution.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nasolution.com.nasolution.ClientContact;

import java.util.List;

public class ClientSpinnerAdapter extends ArrayAdapter {

    public ClientSpinnerAdapter(Context context, int resource, List<String> list, List objects) {
        super(context, resource, objects);
    }

    public ClientSpinnerAdapter(ClientContact clientContact, int project_spinner_item, List<String> projectNameList) {
        super(clientContact, project_spinner_item, projectNameList);
    }

    public ClientSpinnerAdapter(Context baseContext, int client_spinner_item, List<String> clientNameList) {
        super(baseContext, client_spinner_item, clientNameList);
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
