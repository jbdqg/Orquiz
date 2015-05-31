package com.daam.orquiz;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.daam.orquiz.business.ParticipationServices;
import com.daam.orquiz.business.Utils;
import com.daam.orquiz.data.Answer;
import com.daam.orquiz.data.Participation;
import com.daam.orquiz.data.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 25-05-2015.
 */
public class QuizActivity extends FragmentActivity {

    //FragmentPagerAdapter adapterViewPager;
    private SmartFragmentStatePagerAdapter adapterViewPager;

    private int quizIdentificator = 0;
    private int quizQuestionsNumber = 0;
    public boolean sumbmit_button_pressed = false;

    private Utils utils = new Utils();
    private ParticipationServices oq = new ParticipationServices();
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

            int i = 0;
            List<Map> questionsToAnswer = new ArrayList<Map>();
            do {

                questionsToAnswer.add(i, oq.retrieveNextQuestion(db, quizIdentificator));

                i++;
            }while (i != quizQuestionsNumber);

            //houve erro no uso do quiz, por isso tem que se terminar a participação que está aberta
            //TODO: remover se possível
            if (quizQuestionsNumber == questionsToAnswer.size() && questionsToAnswer.get(0).isEmpty()){

                Participation activeParticipation = db.getLastActiveParticipation();

                //fecha-se a participação
                String whereClause = " participation_id = " + activeParticipation.getFieldId();
                Long participationEnd = System.currentTimeMillis();
                Long participationTime = ((participationEnd - activeParticipation.getFieldStart()) / 1000);
                activeParticipation.setFieldEnd(participationEnd);
                activeParticipation.setFieldTotaltime(participationTime.intValue());
                if (sumbmit_button_pressed == true) {
                    activeParticipation.setFieldStatus("completed");
                }
                db.updateTableRecord("Participation", activeParticipation.getContentValues(), whereClause, null);

                i = 0;
                questionsToAnswer = new ArrayList<Map>();
                do {

                    questionsToAnswer.add(i, oq.retrieveNextQuestion(db, quizIdentificator));

                    i++;
                }while (i != quizQuestionsNumber);

            }

            ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
            adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), db, quizQuestionsNumber, quizIdentificator, questionsToAnswer);
            vpPager.setAdapter(adapterViewPager);

            //insere-se a participação ou vai-se buscar a que existe. a invocação do obtainquestion resolve essa questão
            //Map nextQuestion = oq.retrieveNextQuestion(db, quizIdentificator);

            //indica em que posição é que vai iniciar
            //vpPager.setCurrentItem(Integer.parseInt(nextQuestion.get("questionsAnswered").toString()) + 1);

            //obtem-se as perguntas para cada posição e instancia-se um HashMap com todas as que vão ser repondidas


        }else{
            //noquiz
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        int i = 0;

        //obtem-se a participação que está ativa
        Participation activeParticipation = db.getLastActiveParticipation();

        //registam-se as respostas dadas e fechar participação
        while (i != this.quizQuestionsNumber){

            QuizQuestionFragment oneFragment = (QuizQuestionFragment) adapterViewPager.getItem(i);
            //QuizQuestionFragment oneFragment = (QuizQuestionFragment) adapterViewPager.getRegisteredFragment(i);

            Map<String, Object> questionAnswersData = new HashMap<>();
            questionAnswersData.put("participation", activeParticipation);
            questionAnswersData.put("question", oneFragment.getQuestion());
            questionAnswersData.put("answers", oneFragment.getAnswers());

            oq.registerQuestionAnswers(db, questionAnswersData);


            i++;
        }
        //fecha-se a participação
        String whereClause = " participation_id = " + activeParticipation.getFieldId();
        Long participationEnd = System.currentTimeMillis();
        Long participationTime = ((participationEnd - activeParticipation.getFieldStart()) / 1000);
        activeParticipation.setFieldEnd(participationEnd);
        activeParticipation.setFieldTotaltime(participationTime.intValue());
        if (sumbmit_button_pressed == true) {
                activeParticipation.setFieldStatus("completed");
        }
        db.updateTableRecord("Participation", activeParticipation.getContentValues(), whereClause, null);

    }

    // Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
        private static int NUM_ITEMS = 0;
        private static int QUIZ_IDENTIFICATOR = 0;
        private static List<Map> QUIZ_QUESTIONS;
        private ParticipationServices oq = new ParticipationServices();
        private DatabaseHandler DB = null;
        private List<Integer> retrievedQuestions = new ArrayList<Integer>();

        public MyPagerAdapter(FragmentManager fragmentManager, DatabaseHandler db, int questionsNumber, int quizIdentificator, List<Map> questionsToAnswer) {
            super(fragmentManager);
            this.NUM_ITEMS = questionsNumber + 1;
            //this.NUM_ITEMS = questionsNumber;
            this.QUIZ_IDENTIFICATOR = quizIdentificator;
            this.QUIZ_QUESTIONS = questionsToAnswer;
            this.DB = db;
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

            Map fragmentContent;

            //if(fragmentContent.size() != 0) {
            //    retrievedQuestions.add(((Question) fragmentContent.get("question")).getFieldId());
            //    Log.d("Answer: ", ((Question) fragmentContent.get("question")).getFieldText());

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
            return "Question " + position;
        }

    }

}