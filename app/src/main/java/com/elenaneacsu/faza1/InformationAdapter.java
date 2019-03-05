package com.elenaneacsu.faza1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class InformationAdapter extends ArrayAdapter<Information> {

    private int mResource;
    private Context mContext;

    static class ViewHolder {
        TextView textViewTitle;
        TextView textViewFact;
    }

    public InformationAdapter(@NonNull Context context, int resource, @NonNull List<Information> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Information information = getItem(position);
        ViewHolder viewHolder;
        if(convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = convertView.findViewById(R.id.title);
            viewHolder.textViewFact = convertView.findViewById(R.id.fact_info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewTitle.setText(information.getTitle());
        viewHolder.textViewFact.setText(information.getFact());

        return convertView;
    }
}
