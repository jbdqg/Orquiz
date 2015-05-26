package com.daam.orquiz;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;

import com.daam.orquiz.business.ObtainQuestion;
import com.daam.orquiz.business.Utils;
import com.daam.orquiz.data.Question;

import java.util.List;

/**
 * Created by johnny on 25-05-2015.
 */
public class QuizActivity extends FragmentActivity {

    //FragmentPagerAdapter adapterViewPager;
    private SmartFragmentStatePagerAdapter adapterViewPager;

    private int quizIdentificator = 0;
    private int quizQuestionsNumber = 0;

    private Utils utils = new Utils();
    private ObtainQuestion oq = new ObtainQuestion();
    private DatabaseHandler db = new DatabaseHandler(this);
    //private String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    private String android_id = "id do user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        //setContentView(R.layout.view_startquiz);
        setContentView(R.layout.pager_quiz);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            quizIdentificator = extras.getInt("QUIZ_ID");
        }

        if (quizIdentificator != 0){
            quizQuestionsNumber = utils.retrieveQuizQuestions(db, quizIdentificator);

            ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), quizQuestionsNumber);
            vpPager.setAdapter(adapterViewPager);

            //insere-se a participação ou vai-se buscar a que existe. a invocação do obtainquestion resolve essa questão
            List nextQuestion = oq.getNextQuestion(db, quizIdentificator);


        }else{
            //noquiz
        }

    }

    // Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
        private static int NUM_ITEMS = 0;

        public MyPagerAdapter(FragmentManager fragmentManager, int questionsNumber) {
            super(fragmentManager);
            this.NUM_ITEMS = questionsNumber;
        }

        //public void setQuestionsNumber(int questionsNumber){
        //   this.NUM_ITEMS = questionsNumber;
        //}

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            //ObtainQuestion

            //é necessário passar a question a instanciar pelo fragment. é o fragment que decide qual o tipo de interface que vai mostrar em função da pergunta

            //valida-se a position e se a position for a última tem que se mostrar o resulado do quiz

            return QuizQuestionFragment.newInstance(position, "Page # 1", 1);

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
            return "Page " + position;
        }

    }

}