package com.daam.orquiz.business;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.MainActivity;
import com.daam.orquiz.R;
import com.daam.orquiz.data.Answer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public static String[] getQuizUploadList (File mPath) {

        String[] mFileList;

        try {
            mPath.mkdirs();
        } catch (SecurityException e) {
            Log.e("TAG", "unable to write on the sd card " + e.toString());
        }
        if (mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    return filename.contains(".json") || sel.isDirectory();
                }

            };
            mFileList = mPath.list(filter);
        } else {
            mFileList = new String[0];
        }

        return mFileList;

    }

    public static JSONObject getFileJsonContent (File quizJsonFile){

        JSONObject quizJsonObject = null;

        InputStream in = null;
        String quizJsonContent = null;
        try {
            in = new BufferedInputStream(new FileInputStream(quizJsonFile));

            int size = in.available();

            byte[] buffer = new byte[size];

            if (in != null) {
                in.read(buffer);
                in.close();
            }

            quizJsonContent = new String(buffer, "UTF-8");

            quizJsonObject = new JSONObject(quizJsonContent);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return quizJsonObject;

    }

    public static boolean uploadJsonQuiz (DatabaseHandler db, JSONObject quizJsonObject) throws JSONException {

        Boolean quizUploaded = false;

            db.uploadJsonQuizIntoTables(quizJsonObject);

        return quizUploaded;

    }

    public static Bitmap getBitmap(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap d = BitmapFactory.decodeStream(is);
            is.close();
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
