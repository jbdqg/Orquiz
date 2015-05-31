package com.daam.orquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daam.orquiz.business.Utils;
import com.daam.orquiz.data.Answer;
import com.daam.orquiz.data.Participation;
import com.daam.orquiz.data.ParticipationQuestion;
import com.daam.orquiz.data.Question;
import com.daam.orquiz.data.Quiz;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final String PREFS_NAME = "UserData";
    public static final int PARTICIPANT_ID = 1;
    public static int QUIZ_ID = 1;
    private static CallbackManager callbackManager;
    private static ProfileTracker profileTracker;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Button buttonQuizSelection;


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Facebook Initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {

                ImageView profileImage = (ImageView) findViewById(R.id.image);
                TextView profileName = (TextView) findViewById((R.id.username));

                if ( currentProfile != null ) {
                    if ( profileImage != null ) {
                        Uri uri = currentProfile.getProfilePictureUri(profileImage.getWidth(), profileImage.getHeight());
                        profileImage.setImageURI(uri);
                    }

                    if ( profileName != null )
                        profileName.setText(currentProfile.getName());
                } else {
                    if ( profileImage != null )
                        profileImage.setImageResource(R.drawable.user_default);

                    if ( profileName != null )
                        profileName.setText(R.string.no_username);
                }
            }
        };

        //armazenar os dados do utilizador
        //SharedPreferences userData = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = userData.edit();
        //editor.putInt("participant_id", 1);
        //editor.putString("password", password_txt.getText().toString());
        //editor.putString("email", email_txt.getText().toString());
        //editor.commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();

        //TODO:code here
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.drawer_optitle_splashpage);
                break;
            case 2:
                mTitle = getString(R.string.drawer_optitle_my_results);
                break;
            case 3:
                mTitle = getString(R.string.drawer_optitle_my_data);
                break;
            case 4:
                mTitle = getString(R.string.drawer_optitle_import);
                break;
            case 5:
                mTitle = getString(R.string.drawer_optitle_share);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.select_quiz:
                selectQuiz(this);
                return true;
            default:

        }

        return super.onOptionsItemSelected(item);
    }

    private void selectQuiz(final Context context) {
        // Get All Available Quizes
        DatabaseHandler connDatabase = new DatabaseHandler(context);
        final List<Quiz> listOfQuizes = connDatabase.getAllQuiz();
        if ( listOfQuizes == null || listOfQuizes.isEmpty() ) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.title_error)
                    .setMessage(R.string.no_quiz_found)
                    .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();

            return;
        }

        String[] arrayOfNames = new String[listOfQuizes.size()];
        for (int i = 0; i < listOfQuizes.size(); i++) {
            arrayOfNames[i] = listOfQuizes.get(i).getFieldName();
        }

        AlertDialog.Builder chooseQuizDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.title_choose_quiz)
                .setSingleChoiceItems(arrayOfNames, -1 /*no selection*/, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Don't do nothing
                    }
                })
                .setPositiveButton(R.string.button_select_quiz, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedOption = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        if ( selectedOption == -1 ) {
                            Toast.makeText(context, R.string.no_quiz_selected, Toast.LENGTH_SHORT).show();
                        } else {
                            Quiz quiz = listOfQuizes.get(((AlertDialog) dialog).getListView().getCheckedItemPosition()); // Quiz to share

                            //SharedPreferences userData = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = userData.edit();
                            //editor.putInt("QUIZ_ID", quiz.getFieldId());
                            //editor.commit();
                            QUIZ_ID = quiz.getFieldId();
                        }
                    }
                });

        AlertDialog alertDialog = chooseQuizDialog.show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    //public static class PlaceholderFragment extends Fragment {
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

        private File mPath = new File(Environment.getExternalStorageDirectory() + "/orquiz/quizes/");

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.view_startquiz, container, false);

            final Integer selected_option = getArguments().getInt(ARG_SECTION_NUMBER);

            ViewGroup header = null;

            final DatabaseHandler db = new DatabaseHandler(container.getContext());

            if (selected_option == 1) {

                header = (ViewGroup) inflater.inflate(R.layout.view_splashpage, container, false);

                Quiz quiz = db.getQuiz(MainActivity.QUIZ_ID);

                if (quiz.getFieldId() != null){

                    if (quiz.getFieldDescription() instanceof String){

                        final TextView text = (TextView) header.findViewById(R.id.text);
                        text.setText(quiz.getFieldDescription());

                    }

                    if (quiz.getFieldUrl() instanceof String){

                        final ImageView image = (ImageView) header.findViewById(R.id.image);

                        if(QUIZ_ID != 1){
                            File imgFile = new File(quiz.getFieldUrl());
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            image.setImageBitmap(myBitmap);
                        }else{

                            try {
                                InputStream is = MyApplication.getAppContext().getAssets().open(quiz.getFieldUrl());
                                Bitmap bmp = BitmapFactory.decodeStream(is);
                                image.setImageBitmap(bmp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                }

                final Button start_quiz_bt = (Button) header.findViewById(R.id.button);
                start_quiz_bt.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(container.getContext(), QuizActivity.class);
                        //para passar o identificador do quiz que está ativo
                        intent.putExtra("QUIZ_ID", MainActivity.QUIZ_ID);
                        startActivity(intent);

                    }
                });

            } else if (selected_option == 2) {

                header = (ViewGroup) inflater.inflate(R.layout.view_myresults, container, false);

                Participation participation = null;

                Bundle extras = getActivity().getIntent().getExtras();
                if (extras != null && extras.getBoolean("LAST_PARTICIPATION")) {

                    getActivity().getIntent().removeExtra("LAST_PARTICIPATION");
                    participation = db.getParticipation(MainActivity.QUIZ_ID, "last");

                }else{

                    participation = db.getParticipation(MainActivity.QUIZ_ID, "best");

                }

                if(participation.getFieldId() != null){
                    List<ParticipationQuestion> participationquestions_list = db.getActiveParticipationQuestions(participation);

                    final TextView totalquestions_text = (TextView) header.findViewById(R.id.textViewNTotal);
                    totalquestions_text.setText(((Integer) participationquestions_list.size()).toString());

                    final TextView totaltime_text = (TextView) header.findViewById(R.id.textViewNTotalTime);
                    totaltime_text.setText(participation.getFieldTotaltime().toString());

                    int questions_right = 0;
                    int questions_wrong = 0;
                    int questions_unanswered = 0;
                    int total_time = 0;
                    int quiz_points = 0;

                    for (ParticipationQuestion pq : participationquestions_list) {

                        try {
                            JSONObject answersJson = new JSONObject(pq.getFieldAnswersjson());

                            if (answersJson.getBoolean("answered") == false) {
                                questions_unanswered++;
                            } else if (answersJson.getBoolean("answered") == true) {
                                if (answersJson.getBoolean("correct") == true) {
                                    questions_right++;
                                } else if (answersJson.getBoolean("correct") == false) {
                                    questions_wrong++;
                                }
                            }


                            quiz_points += answersJson.getInt("points");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    final TextView points_text = (TextView) header.findViewById(R.id.textViewNPoints);
                    points_text.setText(((Integer) quiz_points).toString());

                    final TextView rightquestions_text = (TextView) header.findViewById(R.id.textViewNRight);
                    rightquestions_text.setText(((Integer) questions_right).toString());

                    final TextView wrongquestions_text = (TextView) header.findViewById(R.id.textViewNWrong);
                    wrongquestions_text.setText(((Integer) questions_wrong).toString());

                    final TextView unansweredquestions_text = (TextView) header.findViewById(R.id.textViewNUnanswered);
                    unansweredquestions_text.setText(((Integer) questions_unanswered).toString());

                    final Button try_again_bt = (Button) header.findViewById(R.id.button);
                    try_again_bt.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Intent intent = new Intent(container.getContext(), QuizActivity.class);
                            //para passar o identificador do quiz que está ativo
                            intent.putExtra("QUIZ_ID", MainActivity.QUIZ_ID);
                            startActivity(intent);

                        }
                    });
                }

                // Facebook
                LoginButton loginFacebookButton = (LoginButton) header.findViewById(R.id.login_button);
                loginFacebookButton.setReadPermissions("public_profile");

                // Callback registration
                loginFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {

                    }
                });
            } else if (selected_option == 4){

            } else if (selected_option == 5){

                header = (ViewGroup) inflater.inflate(R.layout.view_import, container, false);

                final Button start_quiz_bt = (Button) header.findViewById(R.id.button);
                start_quiz_bt.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        String[] jsonFilesInPath = Utils.getQuizUploadList(mPath);

                        AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext())
                                .setTitle("Choose your file")
                                .setSingleChoiceItems(jsonFilesInPath, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ListView lw = ((AlertDialog) dialog).getListView();
                                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());

                                        File quizContent = new File(mPath + "/" + checkedItem.toString());

                                        JSONObject quizJsonObject = Utils.getFileJsonContent(quizContent);

                                        if (quizJsonObject != null) {

                                            try {
                                                Utils.uploadJsonQuiz(db, quizJsonObject);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //ir para a main page

                                    }
                                });

                AlertDialog a = builder.show();

                    }
                });

            } else if ( selected_option == 5) { // Share Option

                // Bluetooth Local
                BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                if ( btAdapter == null ) { // Device without bluetooth?
                    new AlertDialog.Builder(container.getContext())
                        .setTitle(R.string.title_error)
                        .setMessage(R.string.no_bluetooth)
                        .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

                    return null;
                }

                if ( !btAdapter.isEnabled() ) { // Isn't bluetooth on?
                    new AlertDialog.Builder(container.getContext())
                            .setTitle(R.string.title_warning)
                            .setMessage(R.string.no_bluetooth_active)
                            .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();

                    return null;
                }

                // Obter os Quiz
                final List<Quiz> listOfQuiz = db.getAllQuiz();
                if ( listOfQuiz == null || listOfQuiz.isEmpty() ) {
                    new AlertDialog.Builder(container.getContext())
                            .setTitle(R.string.title_error)
                            .setMessage(R.string.no_quiz_found)
                            .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();

                    return null;
                }

                String[] arrayOfNames = new String[listOfQuiz.size()];
                for (int i = 0; i < listOfQuiz.size(); i++) {
                    arrayOfNames[i] = listOfQuiz.get(i).getFieldName();
                }

                AlertDialog.Builder chooseQuizDialog = new AlertDialog.Builder(container.getContext())
                        .setTitle(R.string.title_choose_quiz)
                        .setSingleChoiceItems(arrayOfNames, -1 /*no selection*/, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Don't do nothing
                            }
                        })
                        .setPositiveButton(R.string.button_share, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                int selectedOption = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                                if ( selectedOption == -1 ) {
                                    Toast.makeText(container.getContext(), R.string.no_quiz_selected, Toast.LENGTH_SHORT).show();
                                } else {
                                    Quiz quiz = listOfQuiz.get(((AlertDialog) dialog).getListView().getCheckedItemPosition()); // Quiz to share

                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND);
                                    intent.setType("text/plain"); // File type
                                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(quiz.getFieldReference()))); // The file

                                    // List of apps that can handle our intent
                                    PackageManager pm = container.getContext().getPackageManager();
                                    List<ResolveInfo> appsList = pm.queryIntentActivities(intent, 0);

                                    if (appsList.size() > 0) {
                                        // Select bluetooth
                                        String packageName = null;
                                        String className = null;
                                        boolean found = false;

                                        for (ResolveInfo info : appsList) {
                                            packageName = info.activityInfo.packageName;

                                            if (packageName.equals("com.android.bluetooth")) {
                                                className = info.activityInfo.name;
                                                found = true;

                                                break; // found
                                            }
                                        }

                                        if (!found) {
                                            Toast.makeText(container.getContext(), R.string.no_bluetooth, Toast.LENGTH_SHORT).show();
                                        }

                                        // Launch Bluetooth
                                        intent.setClassName(packageName, className);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });

                AlertDialog alertDialog = chooseQuizDialog.show();


            } else {
                header = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
            }

            return header;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            if (this.getClass().getSimpleName() == "MainActivity"){
                ((MainActivity) activity).onSectionAttached(
                        getArguments().getInt(ARG_SECTION_NUMBER));
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static class old_MyListCheckboxAdapter extends ArrayAdapter<Answer> {

        private ArrayList<Answer> answerList;

        public old_MyListCheckboxAdapter(Context context, int textViewResourceId,
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

}
