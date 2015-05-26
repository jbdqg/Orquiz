package com.daam.orquiz.business;

import android.provider.ContactsContract;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.data.Participation;
import com.daam.orquiz.data.ParticipationQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnny on 25-05-2015.
 */
public class ObtainQuestion {

   public ObtainQuestion(){
    }

    public Participation getActiveParticipation(DatabaseHandler db, int participant_id){

        Participation activeParticipation = db.getLastActiveParticipation(participant_id);

        //Integer activeParticipationID = null;

        //try {
        //    activeParticipationID = Integer.valueOf(activeParticipation.getFieldId());
        //}
        //catch (NumberFormatException e) {
            // ...
        //}
        //if (activeParticipationID != null)
        Long insertedId;
        if (activeParticipation.getFieldId() == null){

            activeParticipation = new Participation();
            activeParticipation.setFieldParticipantid(participant_id);
            activeParticipation.setFieldStart(System.currentTimeMillis());
            insertedId = db.insertTableRecord("Participation", activeParticipation.getContentValues());

        }

        return activeParticipation;

    }

    public List getNextQuestion(DatabaseHandler db, int quiz_id){

        //o participant_id deveria ser variável global da aplicação
        int participant_id = 1;

        Participation activeParticipation = getActiveParticipation(db, participant_id);

        //obter as perguntas que já foram respondidas pela participação
        //List<ParticipationQuestion> answeredQuestions = Participation
        //TODO: CONTINUARAQUI

        List questionData = new ArrayList();



        //no retorno devem ser devolvidos dados da participação, da pergunta e das respostas, sem no entanto enviar dados das respostas certas para o interface
        return questionData;

    }


}
