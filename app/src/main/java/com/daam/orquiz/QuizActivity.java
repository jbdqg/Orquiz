package com.daam.orquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.daam.orquiz.business.AdapterViewPager;
import com.daam.orquiz.business.ParticipationServices;
import com.daam.orquiz.business.Utils;
import com.daam.orquiz.data.Participation;

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
            adapterViewPager = new AdapterViewPager(getSupportFragmentManager(), db, quizQuestionsNumber, quizIdentificator, questionsToAnswer);
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
        int participationPoints = 0;

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

            int questionPoints = oq.registerQuestionAnswers(db, questionAnswersData);

            participationPoints += questionPoints;

            i++;
        }
        //fecha-se a participação
        String whereClause = " participation_id = " + activeParticipation.getFieldId();
        Long participationEnd = System.currentTimeMillis();
        Long participationTime = ((participationEnd - activeParticipation.getFieldStart()) / 1000);
        activeParticipation.setFieldEnd(participationEnd);
        activeParticipation.setFieldTotaltime(participationTime.intValue());
        activeParticipation.setFieldTotaltime(participationTime.intValue());
        if (sumbmit_button_pressed == true) {
                activeParticipation.setFieldStatus("completed");
        }
        db.updateTableRecord("Participation", activeParticipation.getContentValues(), whereClause, null);

    }

}