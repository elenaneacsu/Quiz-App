package com.elenaneacsu.faza1;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {


    private List<Question> mQuestions;

    public QuestionAdapter(List<Question> questions) {
        mQuestions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View prevView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_item, viewGroup, false);
        return new QuestionAdapter.QuestionViewHolder(prevView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        Question question = mQuestions.get(i);
        if (question != null) {
            questionViewHolder.mTextViewTime.setText(question.getTime());
            questionViewHolder.mTextViewQuestion.setText(question.getText());
            questionViewHolder.mTextViewAnswear1.setText(question.getResponse().get(0));
            questionViewHolder.mTextViewAnswear2.setText(question.getResponse().get(1));
            questionViewHolder.mTextViewAnswear3.setText(question.getResponse().get(2));
            questionViewHolder.mTextViewAnswear4.setText(question.getResponse().get(3));
        }
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTime;
        private TextView mTextViewQuestion;
        private TextView mTextViewAnswear1;
        private TextView mTextViewAnswear2;
        private TextView mTextViewAnswear3;
        private TextView mTextViewAnswear4;
        private LinearLayout mLinearLayout;


        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTime = itemView.findViewById(R.id.textview_questiontime);
            mTextViewQuestion = itemView.findViewById(R.id.textview_question);
            mTextViewAnswear1 = itemView.findViewById(R.id.textview_answear1);
            mTextViewAnswear2 = itemView.findViewById(R.id.textview_answear2);
            mTextViewAnswear3 = itemView.findViewById(R.id.textview_answear3);
            mTextViewAnswear4 = itemView.findViewById(R.id.textview_answear4);
            mLinearLayout = itemView.findViewById(R.id.layout_test);
        }
    }
}
