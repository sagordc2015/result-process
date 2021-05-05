package com.exam.resultprocess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.exam.resultprocess.model.BatchSetup;
import com.exam.resultprocess.model.Results;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BatchListviewAdapter extends ArrayAdapter<BatchSetup>{

    public BatchListviewAdapter(Context context, List<BatchSetup> batchSetups) {
        super(context, 0, batchSetups);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BatchSetup batchSetup = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_batch, parent, false);
        }
        // Lookup view for data population
        TextView batchName = (TextView) convertView.findViewById(R.id.listViewBatchName);

        // Populate the data into the template view using the data object
        batchName.setText(batchSetup.getBatchName());

        // Return the completed view to render on screen
        return convertView;
    }
}
