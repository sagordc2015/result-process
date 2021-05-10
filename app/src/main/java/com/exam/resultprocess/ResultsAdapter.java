package com.exam.resultprocess;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.exam.resultprocess.model.Results;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends ArrayAdapter<Results> {

    private Session session;
    final String secretKey = "ssshhhhhhhhhhh!!!!";

    public ResultsAdapter(Context context, List<Results> results) {
        super(context, 0, results);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Results results = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            session = new Session(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }
        // Lookup view for data population
        TextView roll = (TextView) convertView.findViewById(R.id.resultRoll);
        TextView subjectCode = (TextView) convertView.findViewById(R.id.resultSubjectCode);
        TextView attendance = (TextView) convertView.findViewById(R.id.resultAttendance);
        TextView assignment = (TextView) convertView.findViewById(R.id.resultAssignment);
        TextView presentation = (TextView) convertView.findViewById(R.id.resultPresentation);
        TextView midTerm = (TextView) convertView.findViewById(R.id.resultMidTerm);
        TextView finalMarks = (TextView) convertView.findViewById(R.id.resultFinalMarks);
        TextView total = (TextView) convertView.findViewById(R.id.resultTotal);
        TextView cgpa = (TextView) convertView.findViewById(R.id.resultCgpa);
        TextView grade = (TextView) convertView.findViewById(R.id.resultGrade);
        TextView remarks = (TextView) convertView.findViewById(R.id.resultRemarks);

        // Populate the data into the template view using the data object
//        System.out.println(results.getStudentId());
        roll.setText(results.getStudentId());
        subjectCode.setText(results.getSubjectCode());
        attendance.setText(EncryptAndDecrypt.decrypt(results.getAttendance(), secretKey));
        assignment.setText(EncryptAndDecrypt.decrypt(results.getAssignment(), secretKey));
        presentation.setText(EncryptAndDecrypt.decrypt(results.getPresentation(), secretKey));
        midTerm.setText(EncryptAndDecrypt.decrypt(results.getMidTerm(), secretKey));
        finalMarks.setText(EncryptAndDecrypt.decrypt(results.getFinalMarks(), secretKey));
        total.setText(EncryptAndDecrypt.decrypt(results.getTotal(), secretKey));
        cgpa.setText(EncryptAndDecrypt.decrypt(results.getCgpa(), secretKey));
        grade.setText(results.getGrade());
        remarks.setText(results.getRemarks());

        if(session.get("userid").equals("123456") || session.get("type").equals("Student")){
            roll.setVisibility(convertView.GONE);
        }
        // Return the completed view to render on screen
        return convertView;
    }

}