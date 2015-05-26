package com.daam.orquiz;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daam.orquiz.data.Question;

/**
 * Created by johnny on 25-05-2015.
 */
//retirado de https://github.com/codepath/android_guides/wiki/ViewPager-with-FragmentPagerAdapter
public class QuizQuestionFragment extends Fragment {
    // Store instance variables
    private Question question = new Question();

    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static QuizQuestionFragment newInstance(int page, String title, int question_id) {
        QuizQuestionFragment fragmentFirst = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_first, container, false);
        View view = inflater.inflate(R.layout.view_multiplechoice, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);
        TextView tvLabel = (TextView) view.findViewById(R.id.text);
        tvLabel.setText(page + " -- " + title);
        return view;
    }
}
