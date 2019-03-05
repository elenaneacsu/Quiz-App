package com.elenaneacsu.faza1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceFragment extends Fragment {

    private List<String> crAnswers;
    private List<String> incrAnswers;
    private List<String> answers;
    private List<String> selectedAnswers;

    private TextView mTextViewFirstAns;
    private TextView mTextViewSecondAns;
    private TextView mTextViewThirdAns;
    private TextView mTextViewFourthAns;

    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;
    private Button mButtonNext;

    private QuizActivity quizActivity;


    public MultipleChoiceFragment() {
        // Required empty public constructor
    }

    public static MultipleChoiceFragment newInstance(List<String> crAnswers,
                                                     List<String> incrAnswers) {
        MultipleChoiceFragment fragment = new MultipleChoiceFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("CORRECT_ANSWERS", (ArrayList<String>) crAnswers);
        args.putStringArrayList("INCORRECT_ANSWERS", (ArrayList<String>) incrAnswers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crAnswers = getArguments().getStringArrayList("CORRECT_ANSWERS");
        incrAnswers = getArguments().getStringArrayList("INCORRECT_ANSWERS");
        answers = new ArrayList<>();
        answers.addAll(crAnswers);
        answers.addAll(incrAnswers);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        quizActivity = (QuizActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_multiple_choice, container, false);
        mTextViewFirstAns = rootView.findViewById(R.id.textview_firstans);
        mTextViewSecondAns = rootView.findViewById(R.id.textview_secondans);
        mTextViewThirdAns = rootView.findViewById(R.id.textview_thirdans);
        mTextViewFourthAns = rootView.findViewById(R.id.textview_fourthans);

        if (quizActivity.counter < quizActivity.mQuestions.size()-1) {
            Log.d("testtest", "onCreateView: size " + answers.size());
            mTextViewFirstAns.setText(answers.get(0));
            mTextViewSecondAns.setText(answers.get(1));
            mTextViewThirdAns.setText(answers.get(2));
            mTextViewFourthAns.setText(answers.get(3));
        }

        mCheckBox1 = rootView.findViewById(R.id.checkbox_firstans);
        mCheckBox2 = rootView.findViewById(R.id.checkbox_secondans);
        mCheckBox3 = rootView.findViewById(R.id.checkbox_thirdans);
        mCheckBox4 = rootView.findViewById(R.id.checkbox_fourthans);
        mButtonNext = rootView.findViewById(R.id.btn_nextquestion);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
                quizActivity.counter++;
                Log.d("test", "onClick: counter " + quizActivity.counter);
                if (quizActivity.counter < quizActivity.mQuestions.size()-1) {
                    quizActivity.setTitle();
                    setFragment(quizActivity.mQuestions.get(quizActivity.counter-1).getType());
                    Log.d("verif", "onClick: counter "+quizActivity.counter);
                    Log.d("verif", "onClick: questions size "+quizActivity.mQuestions.size());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Scor: " + quizActivity.score, Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    private void checkAnswers() {
        selectedAnswers = new ArrayList<>();
        if (mCheckBox1.isChecked()) {
            selectedAnswers.add(mTextViewFirstAns.getText().toString());
        }
        if (mCheckBox2.isChecked()) {
            selectedAnswers.add(mTextViewSecondAns.getText().toString());
        }
        if (mCheckBox3.isChecked()) {
            selectedAnswers.add(mTextViewThirdAns.getText().toString());
        }
        if (mCheckBox4.isChecked()) {
            selectedAnswers.add(mTextViewFourthAns.getText().toString());
        }

        if (selectedAnswers.size() == crAnswers.size()) {
            if (crAnswers.containsAll(selectedAnswers)) {
                quizActivity.score++;
            }
        }
    }


    private void initFragment(List<String> correctAnswers, List<String> incorrectAnswers) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, MultipleChoiceFragment.newInstance(correctAnswers, incorrectAnswers));
        fragmentTransaction.commit();
    }

    private void setFragment(String type) {
        if (type.equals("multiple")) {
            TestQuestion currentTestQuestion = quizActivity.mQuestions.get(quizActivity.counter);
            if (currentTestQuestion.getCorrectAnswers().size() != 0 &&
                    currentTestQuestion.getIncorrectAnswers().size() != 0) {
                initFragment(currentTestQuestion.getCorrectAnswers(), currentTestQuestion.getIncorrectAnswers());
            }
        }
    }

}
