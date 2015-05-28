package com.daam.orquiz.business;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.R;
import com.daam.orquiz.data.Answer;

import java.util.ArrayList;
import java.util.List;

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

    public static class MyListCheckboxAdapter extends ArrayAdapter<Answer> {

        private ArrayList<Answer> answerList;

        public MyListCheckboxAdapter(Context context, int textViewResourceId,
                                     List<Answer> answerList) {
            super(context, textViewResourceId, answerList);
            this.answerList = new ArrayList<Answer>();
            this.answerList.addAll(answerList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                /*LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);*/
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.custom_checkboxlist_layout, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Answer answer = (Answer) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        Toast.makeText(getContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        answer.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Answer answer = answerList.get(position);
            holder.code.setText(" (" +  answer.getFieldText() + ")");
            holder.name.setText(answer.getFieldText());
            holder.name.setChecked(answer.isSelected());
            holder.name.setTag(answer);

            return convertView;

        }

    }

    public static class MyListRadiobuttonAdapter extends ArrayAdapter<Answer> {

        private ArrayList<Answer> answerList;

        public MyListRadiobuttonAdapter(Context context, int textViewResourceId,
                                     List<Answer> answerList) {
            super(context, textViewResourceId, answerList);
            this.answerList = new ArrayList<Answer>();
            this.answerList.addAll(answerList);
        }

        private class ViewHolder {
            TextView code;
            RadioButton name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                /*LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);*/
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.custom_radiobuttonlist_layout, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (RadioButton) convertView.findViewById(R.id.radioButton1);
                convertView.setTag(holder);

                //holder.name.setOnClickListener( new View.OnClickListener() {
                //    public void onClick(View v) {
                //        CheckBox cb = (CheckBox) v ;
                //        Answer answer = (Answer) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                //        Toast.makeText(getContext(),
                //               "Clicked on Radiobutton: " + cb.getText() +
                //                        " is " + cb.isChecked(),
                //                Toast.LENGTH_LONG).show();
                //        answer.setSelected(cb.isChecked());
                //    }
                //});
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Answer answer = answerList.get(position);
            holder.code.setText(" (" +  answer.getFieldText() + ")");
            //holder.name.setText(answer.getFieldText());
            //holder.name.setChecked(answer.isSelected());
            //holder.name.setTag(answer);

            return convertView;

        }

    }



}
