package com.daam.orquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.daam.orquiz.data.Answer;
import com.daam.orquiz.data.Participation;
import com.daam.orquiz.data.ParticipationQuestion;
import com.daam.orquiz.data.Question;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final String PREFS_NAME = "UserData";
    public static final int PARTICIPANT_ID = 1;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

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

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //armazenar os dados do utilizador
        //SharedPreferences userData = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = userData.edit();
        //editor.putInt("participant_id", 1);
        //editor.putString("password", password_txt.getText().toString());
        //editor.putString("email", email_txt.getText().toString());
        //editor.commit();

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
                mTitle = getString(R.string.drawer_optitle_start_quiz);
                break;
            case 3:
                mTitle = getString(R.string.drawer_optitle_my_results);
                break;
            case 4:
                mTitle = getString(R.string.drawer_optitle_my_data);
                break;
            case 5:
                mTitle = getString(R.string.drawer_optitle_import);
                break;
            case 6:
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

            DatabaseHandler db = new DatabaseHandler(container.getContext());

            if (selected_option == 1) {
                header = (ViewGroup) inflater.inflate(R.layout.view_splashpage, container, false);

                final Button start_quiz_bt = (Button) header.findViewById(R.id.button);
                start_quiz_bt.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(container.getContext(), QuizActivity.class);
                        //para passar o identificador do quiz que está ativo
                        intent.putExtra("QUIZ_ID", 1);
                        startActivity(intent);

                    }
                });

            } else if (selected_option == 2) {
                header = (ViewGroup) inflater.inflate(R.layout.view_startquiz, container, false);

                final ProgressBar progressBarWidget = (ProgressBar) header.findViewById(R.id.progressBar);

                final Button but = (Button) header.findViewById(R.id.button);
                //but.setOnClickListener();
                progressBarWidget.getProgress();

            } else if (selected_option == 4) {

                header = (ViewGroup) inflater.inflate(R.layout.view_myresults, container, false);

                Bundle extras = getActivity().getIntent().getExtras();
                if (extras != null) {
                    if (extras.getBoolean("LAST_PARTICIPATION") == true){

                        Participation participation = db.getLastParticipation();

                        List<ParticipationQuestion> participationquestions = db.getActiveParticipationQuestions(participation);

                        if (participation.getFieldId() != null){
                            Log.d("a", "b");
                        }else{
                            Log.d("c", "e");
                        }
                    }
                }



            } else if (selected_option == 10) {

                header = (ViewGroup) inflater.inflate(R.layout.view_multiplechoice, container, false);

                    /*
                    //progress bar for resource em drawable (progress_bar.xml -> http://www.learn-android-easily.com/2013/05/custom-progress-bar-in-android.html)
                    ProgressDialog progressBar;
                    progressBar = new ProgressDialog(container.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("Downloading File...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    // Get the Drawable custom_progressbar
                    Drawable customDrawable= getResources().getDrawable(R.drawable.progress_bar);
                    // set the drawable as progress drawavle
                    progressBar.setProgressDrawable(customDrawable);
                    */

                    //progressbar enquanto elemento do layout
                    final ProgressBar progressBar = (ProgressBar) header.findViewById(R.id.progressBar);
                    int number_of_questions = 10;
                    progressBar.setProgress((5*100)/number_of_questions);

                    final TextView question_text = (TextView) header.findViewById(R.id.text);
                    final ListView answersLv = (ListView) header.findViewById(R.id.answerslv);

                    int question_id = 1;



                    Question question = db.getQuestion(question_id);

                    question_text.setText(question.getFieldText());

                    int count = db.getAllQuestionAnswersCount(question_id);
                    String[] values = new String[count];
                    List<Answer> answers_list = db.getAllQuestionAnswers(question_id, 0);
                    int i = 0;
                    for (Answer qt : answers_list) {
                        String log = "Id: " + qt.getFieldId() + " ,Text: " +
                                qt.getFieldText() + " ,Url: " + qt.getFieldUrl();
                        Log.d("Answer: ", log);
                        //values[i] = qt.getFieldId().toString();
                        values[i] = qt.getFieldText();
                        i++;
                    }

                    //You can get the context by invoking getApplicationContext(), getContext(), getBaseContext() or this (when in the activity class).

                old_MyListCheckboxAdapter listcheckboxadapter = new old_MyListCheckboxAdapter(getActivity().getBaseContext(), R.layout.custom_checkboxlist_layout, answers_list);

                    ArrayAdapter<String> adapter = new
                            ArrayAdapter<String>(getActivity().getBaseContext(),
                            android.R.layout.simple_list_item_1, android.R.id.text1,
                            values);

                    answersLv.setAdapter(listcheckboxadapter);
            }else {
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
