package com.exam.resultprocess;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.exam.resultprocess.model.BatchSetup;
import com.exam.resultprocess.model.TeacherSetup;

import java.util.List;

public class TeacherListviewAdapter extends ArrayAdapter<TeacherSetup> {

    public TeacherListviewAdapter(Context context, List<TeacherSetup> teacherSetups) {
        super(context, 0, teacherSetups);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TeacherSetup teacherSetup = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_batch, parent, false);
        }
        // Lookup view for data population
        TextView batchName = (TextView) convertView.findViewById(R.id.listViewBatchName);

        // Populate the data into the template view using the data object
        batchName.setText("Batch - " + teacherSetup.getBatchCode() + " : " + teacherSetup.getSubjectCode());

        // Return the completed view to render on screen
        return convertView;
    }
}
