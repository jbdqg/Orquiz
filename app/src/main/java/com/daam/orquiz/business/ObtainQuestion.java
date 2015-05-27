package com.daam.orquiz.business;

import android.provider.ContactsContract;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.data.Participation;
import com.daam.orquiz.data.ParticipationQuestion;
import com.daam.orquiz.data.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 25-05-2015.
 */
public class ObtainQuestion {

   public ObtainQuestion(){
    }

   public Map retrieveNextQuestion(DatabaseHandler db, int quiz_id){

       //o participant_id deveria ser variável global da aplicação
       int participant_id = 1;
       Map nextQuestion = null;

       Participation activeParticipation = db.getLastActiveParticipation(participant_id);

       //obter as perguntas que já foram respondidas pela participação
       List<ParticipationQuestion> answeredQuestions = db.getActiveParticipationQuestions(activeParticipation);

       int questionsNotClosed = 0;
       int questionsNotAnswered = 0;
       int questionsAnswered = 0;
       int lastquestionAnswered = 0;
       InputStreamReader questionsAnswered_is = null;

       if (!answeredQuestions.isEmpty()){

           //fazer depois e colocar as perguntas já respondidas na InputStreamReader

           lastquestionAnswered = answeredQuestions.get(answeredQuestions.size() -1).getFieldQuestionid().intValue();

       }

       nextQuestion = db.getNextQuestion(quiz_id, questionsAnswered_is, lastquestionAnswered);
       //questionsAnswered é o nr de respostas que já foram respondidas e que corresponde à posição em que o fragment tem que iniciar
       nextQuestion.put("questionsAnswered", questionsAnswered);

       //no retorno devem ser devolvidos dados da participação, da pergunta e das respostas, sem no entanto enviar dados das respostas certas para o interface
       return nextQuestion;

    }


}
