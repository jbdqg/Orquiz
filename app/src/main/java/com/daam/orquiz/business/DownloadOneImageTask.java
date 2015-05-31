package com.daam.orquiz.business;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import com.daam.orquiz.DatabaseHandler;
import com.daam.orquiz.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by johnny on 30-05-2015.
 */
public class DownloadOneImageTask extends AsyncTask<String, Void, String> {

    String table_name = null;
    String table_key = null;
    String record_id = null;
    String field_name = null;
    String file_url = null;

    DatabaseHandler dbConnector = new DatabaseHandler(
            MyApplication.getAppContext());

    @Override
    protected String doInBackground(String... strings) {
        this.table_name = strings[0];
        this.table_key = strings[1];
        this.record_id = strings[2];
        this.field_name = strings[3];
        this.file_url = strings[4];

        String file_location = download_Image(file_url);

        ContentValues contentValues = new ContentValues();
        contentValues.put(field_name, file_location);

        //agora atualiza-se o registo de quiz;
        dbConnector.updateTableRecord(table_name, contentValues, table_key + " = " + record_id, null);
        return file_location;
    }

    @Override
    protected void onPostExecute(String result) {
        //enviar aviso de que o upload foi feito
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String download_Image(String file_url) {

        String file_location = null;

        try{
            URL ulrn = new URL(file_url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();

            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                try {
                    File storagePath = Environment.getExternalStorageDirectory();
                    file_location = storagePath + "/orquiz/quizes/" + table_key + record_id + ".jpg";
                    try (OutputStream output = new FileOutputStream(file_location)) {
                        try {
                            byte[] buffer = new byte[1024];
                            int bytesRead = 0;
                            while ((bytesRead = is.read(buffer)) != -1) {
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
                        is.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }catch(Exception e){}

        return file_location;

    }
}