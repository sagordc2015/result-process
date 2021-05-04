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
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.exam.resultprocess.model.Results;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseListviewAdapter extends ArrayAdapter<Results> {

    public CourseListviewAdapter(Context context, List<Results> results) {
        super(context, 0, results);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Results results = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_course, parent, false);
        }
        // Lookup view for data population
        CircleImageView profileImage = (CircleImageView) convertView.findViewById(R.id.profile_image_listview);
        TextView fullName = (TextView) convertView.findViewById(R.id.listview_fullName);
        TextView identity = (TextView) convertView.findViewById(R.id.listview_identity);

        File filePath = Environment.getExternalStorageDirectory();
        File dir = new File(filePath.getAbsolutePath()+"/userImages/");
        File file = new File(dir, results.getStudentId()+".jpeg");
        String imagePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        profileImage.setImageBitmap(bitmap);

        // Populate the data into the template view using the data object
        fullName.setText(results.getFullName());
        identity.setText(results.getStudentId());

        // Return the completed view to render on screen
        return convertView;
    }
}
