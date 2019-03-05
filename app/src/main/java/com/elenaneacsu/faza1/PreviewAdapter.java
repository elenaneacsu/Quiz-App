package com.elenaneacsu.faza1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder> {
    private static final String TAG = "PreviewAdapter";

    private List<SharedResources> mResources;
    private ItemClickListener mItemClickListener;
    private Context context;

    public PreviewAdapter(List<SharedResources> resources, Context context) {
        mResources = resources;
        this.context = context;
    }

    @NonNull
    @Override
    public PreviewAdapter.PreviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View prevView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.preview_quiz, viewGroup, false);
        Log.d(TAG, "onCreateViewHolder: " + getItemCount());
        return new PreviewAdapter.PreviewViewHolder(prevView);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewAdapter.PreviewViewHolder previewViewHolder, int i) {
        SharedResources resource = mResources.get(i);
        if(resource != null) {
            previewViewHolder.mTextViewAuthor.setText(resource.getAuthor());
            previewViewHolder.mTextViewSubject.setText(resource.getSubject());
            previewViewHolder.mTextViewChapter.setText(resource.getChapter());
            previewViewHolder.mTextViewTime.setText(resource.getRequiredTime());
            previewViewHolder.mTextViewType.setText(resource.getQuizType());
        }
        Log.d(TAG, "onBindViewHolder: " + getItemCount());
    }

    @Override
    public int getItemCount() {
        if (mResources == null){
            return  0;
        } else {
            return mResources.size();
        }
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, SharedResources resources);
    }

    public class PreviewViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mTextViewAuthor;
        private TextView mTextViewSubject;
        private TextView mTextViewChapter;
        private TextView mTextViewTime;
        private TextView mTextViewType;

        public PreviewViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewAuthor = itemView.findViewById(R.id.textview_authorName);
            mTextViewSubject = itemView.findViewById(R.id.textview_quizsubject);
            mTextViewChapter = itemView.findViewById(R.id.textview_quizchapter);
            mTextViewTime = itemView.findViewById(R.id.textview_quiztime);
            mTextViewType = itemView.findViewById(R.id.textview_quiztype);

            itemView.setOnClickListener(this);
        }
//
//        public PreviewViewHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.question_item, parent, false));
//            itemView.setOnClickListener((View v) -> {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, QuestionsActivity.class);
//                context.startActivity(intent);
//            });
//        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null) {
                mItemClickListener.onItemClick(v, mResources.get(getAdapterPosition()));
            }
        }
    }

}

