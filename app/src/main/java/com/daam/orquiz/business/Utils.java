package com.daam.orquiz.business;

import com.daam.orquiz.DatabaseHandler;

/**
 * Created by johnny on 26-05-2015.
 */
public class Utils {

    public Utils() {
    }

    public int retrieveQuizQuestions(DatabaseHandler db, int quiz_id) {
        int quizQuestionsNumber;
        quizQuestionsNumber = db.getQuizQuestionsNumber(quiz_id);
        return quizQuestionsNumber;
    }

}
