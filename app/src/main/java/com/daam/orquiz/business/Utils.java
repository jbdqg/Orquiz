package com.daam.orquiz.business;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.MainActivity;
import com.daam.orquiz.MyApplication;
import com.daam.orquiz.R;
import com.daam.orquiz.data.Answer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
                //holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.code = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Answer answer = (Answer) cb.getTag();
                        /*Toast.makeText(getContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        answer.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Answer answer = answerList.get(position);
            holder.code.setText(answer.getFieldText());
            //holder.name.setText(answer.getFieldText());
            holder.name.setChecked(answer.isSelected());
            holder.name.setTag(answer);

            return convertView;

        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void copyPrivateResourceToPublicAccess(String filename) {

        String file_location = null;

        InputStream inputStream;
        try {

            inputStream = MyApplication.getAppContext().getAssets().open(filename);

            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                try {
                    File storagePath = Environment.getExternalStorageDirectory();
                    file_location = storagePath + "/orquiz/quizes/ " + filename;
                    try (OutputStream output = new FileOutputStream(file_location)) {
                        try {
                            byte[] buffer = new byte[1024];
                            int bytesRead = 0;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                output.write(buffer, 0, bytesRead);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } finally {
                            try {
                                output.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            Toast.makeText(MyApplication.getAppContext(), "The File was uploaded to " + file_location,
                    Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.custom_radiobuttonlist_layout, null);

                holder = new ViewHolder();
                //holder.code = (TextView) convertView.findViewById(R.id.code);
                //holder.name = (RadioButton) convertView.findViewById(R.id.radioButton1);
                holder.code = (RadioButton) convertView.findViewById(R.id.radioButton1);
                holder.name = (RadioButton) convertView.findViewById(R.id.radioButton1);
                convertView.setTag(holder);

                //RadioGroup rg = (RadioGroup) convertView.findViewById(R.id.radioGroup1);
                //rg.clearCheck();

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {

                        RadioButton rb = (RadioButton) v;

                        Answer answer = (Answer) rb.getTag();

                        answer.setSelected(rb.isChecked());

                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Answer answer = answerList.get(position);
            holder.code.setText(answer.getFieldText());
            //holder.name.setText(answer.getFieldText());
            //holder.name.setChecked(answer.isSelected());
            holder.name.setTag(answer);

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
                    //return (filename.contains(".json") && filename.contains("encrypted_")) || sel.isDirectory();
                    return filename.contains(".json") || sel.isDirectory();
                }

            };
            mFileList = mPath.list(filter);
        } else {
            mFileList = new String[0];
        }

        return mFileList;

    }

    public static String[] getQuizShareList (File mPath) {

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
                    return (filename.contains(".json") && !filename.contains("encrypted_")) || sel.isDirectory();
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

            //Utils.AES encryptInstance = new Utils.AES();
            //quizJsonContent = encryptInstance.decrypt(buffer, encryptInstance.encryptionKey);

            quizJsonContent = new String(buffer, "UTF-8");

            quizJsonObject = new JSONObject(quizJsonContent);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quizJsonObject;

    }

    public static boolean uploadJsonQuiz (DatabaseHandler db, JSONObject quizJsonObject) throws JSONException {

        Boolean quizUploaded = false;



            new UploadQuizTask().execute(quizJsonObject);

            //db.uploadJsonQuizIntoTables(quizJsonObject);

        return quizUploaded;

    }

    public static void  checkOrCreateDir(File mPath){

        if (!mPath.exists()) {

            try{
                mPath.mkdirs();
            }
            catch(SecurityException se){
                //handle it
            }
        }
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

    public static class AES {
        public String IV = "AAAAAAAAAAAAAAAA";
        public String encryptionKey = "0123456789abcdef";

        public AES(){
        }

        public byte[] encrypt(String plainText, String encryptionKey) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            //cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText.getBytes("UTF-8"));
        }

        public String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            //cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(cipherText),"UTF-8");
        }
    }

    public static void encryptFileContent(File quizJsonFile) {

        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(quizJsonFile));

            int size = in.available();

            byte[] buffer = new byte[size];

            if (in != null) {
                in.read(buffer);
                in.close();
            }

            //String quizJsonContent = new String(buffer, "UTF-8");

            Utils.AES encryptInstance = new Utils.AES();

            //String quizJsonContent = encryptInstance.encrypt(new String(buffer, "UTF-8"), encryptInstance.encryptionKey);

            FileOutputStream fooStream = new FileOutputStream(quizJsonFile, false); // true to append
            // false to overwrite.
            byte[] myBytes = encryptInstance.encrypt(new String(buffer, "UTF-8"), encryptInstance.encryptionKey);;
            try {
                fooStream.write(myBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fooStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }



}
