package com.daam.orquiz.business;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.data.Answer;
import com.daam.orquiz.data.Participation;
import com.daam.orquiz.data.ParticipationQuestion;
import com.daam.orquiz.data.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 25-05-2015.
 */
public class ParticipationServices {

   public ParticipationServices(){
    }

    /**obtém a próxima pergunta a que o participante pode responder
    // se não houver participação é criada uma
    // antes de a pergunta ser devolvida é colocado um registo em ParticipationQuestion a indicar que a pergunta saiu
    **/
   public Map retrieveNextQuestion(DatabaseHandler db, int quiz_id){

       Map nextQuestion;

       Participation activeParticipation = db.getLastActiveParticipation();

       //obter as perguntas que já foram respondidas para a participação
       List<ParticipationQuestion> answeredQuestions = db.getActiveParticipationQuestions(activeParticipation);

       int questionsNotClosed = 0;
       int questionsNotAnswered = 0;
       int questionsAnswered = 0;
       int lastquestionAnswered = 0;
       String questionsAnswered_id = "";

       if (!answeredQuestions.isEmpty()){

           //fazer depois e colocar as perguntas já respondidas na InputStreamReader
           int i = 0;
           for (ParticipationQuestion pq : answeredQuestions) {
               if (i != 0){
                   questionsAnswered_id += ", ";
               }

               questionsAnswered_id += pq.getFieldQuestionid();
               i++;
           }

           lastquestionAnswered = answeredQuestions.get(answeredQuestions.size() -1).getFieldQuestionid().intValue();

       }

       nextQuestion = db.getNextQuestion(quiz_id, questionsAnswered_id, lastquestionAnswered);

       if (nextQuestion.size() != 0){

           //vamos inserir a pergunta como estando disponível para responder (inserção em ParticipationQuestion)
           ParticipationQuestion pq = new ParticipationQuestion();
           pq.setFieldParticipationid(activeParticipation.getFieldId());
           pq.setFieldQuestionid(((Question)nextQuestion.get("question")).getFieldId());
           pq.setFieldOrder(answeredQuestions.size() + 1);

           //por enquanto o tempo é instanciado aqui
           pq.setFieldServerstart(System.currentTimeMillis());
           pq.setFieldClientstart(System.currentTimeMillis());

           db.insertTableRecord("ParticipationQuestion", pq.getContentValues());

           //questionsAnswered é o nr de respostas que já foram respondidas e que corresponde à posição em que o fragment tem que iniciar
           nextQuestion.put("questionsAnswered", questionsAnswered);
       }

       //no retorno devem ser devolvidos dados da participação, da pergunta e das respostas, sem no entanto enviar dados das respostas certas para o interface
       return nextQuestion;

    }

    /**regista cada resposta dada em ParticipationQuestion
    // em atributo participationquestion_answersjson são guardados os dados da pergunta feita e os dados das respostas dadas
    // neste registo em ParticipationQuestion fica também registo a pontuação recebida
    **/
    public int registerQuestionAnswers(DatabaseHandler db, Map<String, Object> questionAnswersData){

        int questionPoints = 0;

        if(questionAnswersData.containsKey("participation") && questionAnswersData.containsKey("question") && questionAnswersData.containsKey("answers")) {

            Participation participation = (Participation) questionAnswersData.get("participation");
            Question question = (Question) questionAnswersData.get("question");
            List<Answer> answers = (List<Answer>) questionAnswersData.get("answers");

            //as participationQuestion já foram inseridas quando se acedeu ao quiz, por isso agora são só updates
            ParticipationQuestion oneParticipationQuestion = db.getParticipationQuestion(participation.getFieldId(), question.getFieldId());
            int i = 0;

            String questionJsonData = "{";
            String answersJsonData = "";
            questionJsonData += " \"id\" : " + question.getFieldId() + ", ";
            questionJsonData += " \"text\" : \"" + question.getFieldText() + "\", ";
            questionJsonData += " \"url\" : \"" + question.getFieldUrl() + "\", ";
            answersJsonData += " \"answers\" : [ ";

            boolean question_answered = false;
            boolean question_correct = false;
            for (Answer oneAnswer : answers) {

                if (oneAnswer.isSelected() == true) {

                    question_answered = true;

                    ArrayList answerInfo = db.validateGivenAnswer(oneAnswer.getFieldId());

                    if (answerInfo.size() != 0) {

                        if (i != 0) {
                            answersJsonData += ", ";
                        }
                        answersJsonData += "{";

                        answersJsonData += "\"id\" : " + oneAnswer.getFieldId() + ", ";
                        answersJsonData += "\"text\" : \"" + oneAnswer.getFieldText() + "\",";
                        answersJsonData += "\"url\" : \"" + oneAnswer.getFieldUrl() + "\",";

                        if ((int) answerInfo.get(0) == 1) {
                            answersJsonData += "\"correct\" : true,";
                            question_correct = true;
                        } else if ((int) answerInfo.get(0) == 0) {
                            answersJsonData += "\"correct\" : false,";
                        }
                        answersJsonData += "\"points\" : " + answerInfo.get(1) + ",";
                        int questionAnswerTime = 0; //deveria ser participationquestion_serverend - participationquestion_serverstart tempo que se demorou a responder, mas ainda não está a ser considerado
                        answersJsonData += "\"time\" : \"not being calculated yet\",";

                        /**pontuação recebida por uma pergunta = answer_points * ( 1 / ( tempo resposta + 1 ) ) * 10
                        //pontuação é sempre calculada, pois podem-se dar pontos negativos a respostas erradas
                        **/
                        int answerTotalPoints = (int) answerInfo.get(1) * (1 / (questionAnswerTime + 1)) * 10;
                        answersJsonData += "\"totalpoints\" : " + answerTotalPoints;
                        questionPoints += answerTotalPoints;

                        answersJsonData += "}";

                    }
                    i++;
                }
            }
            answersJsonData += "]";
            questionJsonData += " \"points\" : " + questionPoints + ", ";
            if (question_answered == true) {
                questionJsonData += " \"answered\" : " + true + ", ";
            } else if (question_answered == false) {
                questionJsonData += " \"answered\" : " + false + ", ";
            }
            if (question_correct == true) {
                questionJsonData += " \"correct\" : " + true + ", ";
            } else if (question_correct == false) {
                questionJsonData += " \"correct\" : " + false + ", ";
            }
            questionJsonData += answersJsonData;
            questionJsonData += "}";

            oneParticipationQuestion.setFieldAnswersjson(questionJsonData);
            Long serverEnd = System.currentTimeMillis();
            oneParticipationQuestion.setFieldServerend(System.currentTimeMillis());
            oneParticipationQuestion.setFieldClientend(System.currentTimeMillis());
            if (oneParticipationQuestion.getFieldServerstart() != null){
                Long participationTime = ((serverEnd - oneParticipationQuestion.getFieldServerstart() / 1000));
                oneParticipationQuestion.setFieldAnswertime(participationTime.intValue());
            }

            //já se têm os dados da resposta, atualizam-se os dados da ParticipationQuestion
            db.updateTableRecord("ParticipationQuestion", oneParticipationQuestion.getContentValues(), "participationquestion_id = " + oneParticipationQuestion.getFieldId(), null);

        }

        return questionPoints;

    }

}
