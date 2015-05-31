package com.daam.orquiz.business;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.QuizQuestionFragment;
import com.daam.orquiz.SmartFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
public class AdapterViewPager extends SmartFragmentStatePagerAdapter {

    private static int NUM_ITEMS = 0;
    private static int QUIZ_IDENTIFICATOR = 0;
    private static List<Map> QUIZ_QUESTIONS;
    private ParticipationServices oq = new ParticipationServices();
    private DatabaseHandler DB = null;
    private List<Integer> retrievedQuestions = new ArrayList<Integer>();

    public AdapterViewPager(FragmentManager fragmentManager, DatabaseHandler db, int questionsNumber, int quizIdentificator, List<Map> questionsToAnswer) {
        super(fragmentManager);
        this.NUM_ITEMS = questionsNumber + 1;
        this.QUIZ_IDENTIFICATOR = quizIdentificator;
        this.QUIZ_QUESTIONS = questionsToAnswer;
        this.DB = db;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        Map fragmentContent;

        if (position != this.NUM_ITEMS - 1){
            fragmentContent = this.QUIZ_QUESTIONS.get(position);
            //insere-se a participação ou vai-se buscar a que existe. a invocação do obtainquestion resolve essa questão
            //fragmentContent = oq.retrieveNextQuestion(this.DB, this.QUIZ_IDENTIFICATOR);

            //if(fragmentContent.size() != 0) {
            //    retrievedQuestions.add(((Question) fragmentContent.get("question")).getFieldId());
            //    Log.d("Answer: ", ((Question) fragmentContent.get("question")).getFieldText());
            //}
            //validar porque na 2a iteração dá erro quando já não se pode responder mais (testar comentando o onStop())
        }else{
            fragmentContent = new HashMap<String, String>();
            fragmentContent.put("title", "Submit Fragment");
            fragmentContent.put("content", "Confirm Content");
        }

        return QuizQuestionFragment.newInstance(position, fragmentContent);

        /*switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return QuizQuestionFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return QuizQuestionFragment.newInstance(1, "Page # 2");
            case 2: // Fragment # 1 - This will show SecondFragment
                return QuizQuestionFragment.newInstance(2, "Page # 3");
            case 3: // Fragment # 1 - This will show SecondFragment
                return QuizQuestionFragment.newInstance(3, "Page # 4");
            case 4: // Fragment # 1 - This will show SecondFragment
                return QuizQuestionFragment.newInstance(4, "Page # 5");
            default:
                return null;
        }*/
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {

        CharSequence title_top = null;

        if (position == (NUM_ITEMS - 1)){
            title_top = "Submit Quiz";
        }else{
            title_top = "Question " + (position + 1) + " / " + (NUM_ITEMS - 1);
        }

        return title_top;

    }

}
