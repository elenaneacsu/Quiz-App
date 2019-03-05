package com.elenaneacsu.faza1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SharedResourcesAdapter extends ArrayAdapter<SharedResources> {
    private int mResource;
    private Context mContext;
    private static final String TAG = "SharedResourcesAdapter";

    static class ViewHolder {
        TextView textviewAuthor;
        TextView textviewSubject;
        TextView textviewChapter;
        TextView textviewTime;
        TextView textviewType;
    }

    public SharedResourcesAdapter(@NonNull Context context, int resource, @NonNull List<SharedResources> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SharedResources sharedResource = getItem(position);
       // Log.d(TAG, "getView: "+sharedResource.getAuthor());
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textviewAuthor = convertView.findViewById(R.id.textview_authorName);
            viewHolder.textviewSubject = convertView.findViewById(R.id.textview_quizsubject);
            viewHolder.textviewChapter = convertView.findViewById(R.id.textview_quizchapter);
            viewHolder.textviewTime = convertView.findViewById(R.id.textview_quiztime);
            viewHolder.textviewType = convertView.findViewById(R.id.textview_quiztype);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (sharedResource != null) {
            viewHolder.textviewAuthor.setText(sharedResource.getAuthor());
            viewHolder.textviewSubject.setText(sharedResource.getSubject());
            viewHolder.textviewChapter.setText(sharedResource.getChapter());
            viewHolder.textviewTime.setText("" + sharedResource.getRequiredTime());
            viewHolder.textviewType.setText(sharedResource.getQuizType());
            Log.d(TAG, "getView: "+viewHolder.textviewAuthor.getText());
        }

        return convertView;
    }
}
