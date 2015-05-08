package com.daam.orquiz;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.Answer;
import data.Question;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //View rootView = inflater.inflate(R.layout.view_startquiz, container, false);

            final Integer selected_option = getArguments().getInt(ARG_SECTION_NUMBER);

            ViewGroup header = null;

            if (selected_option == 1) {
                header = (ViewGroup) inflater.inflate(R.layout.view_splashpage, container, false);
            } else if (selected_option == 2) {
                header = (ViewGroup) inflater.inflate(R.layout.view_startquiz, container, false);

                final ProgressBar progressBarWidget = (ProgressBar) header.findViewById(R.id.progressBar);

                final Button but = (Button) header.findViewById(R.id.button);
                //but.setOnClickListener();
                progressBarWidget.getProgress();
            } else {
                if (selected_option == 3) {

                    header = (ViewGroup) inflater.inflate(R.layout.view_multiplechoice, container, false);

                    final TextView question_text = (TextView) header.findViewById(R.id.text);
                    final ListView answersLv = (ListView) header.findViewById(R.id.answerslv);

                    int question_id = 1;

                    DatabaseHandler db = new DatabaseHandler(container.getContext());

                    Question question = db.getQuestion(question_id);

                    question_text.setText(question.getQuestion_text());

                    int count = db.getAllQuestionAnswersCount(question_id);
                    String[] values = new String[count];
                    List<Answer> answers_list = db.getAllQuestionAnswers(question_id);
                    int i = 0;
                    for (Answer qt : answers_list) {
                        String log = "Id: " + qt.getAnswer_id() + " ,Text: " +
                                qt.getAnswer_text() + " ,Url: " + qt.getAnswer_url();
                        Log.d("Answer: ", log);
                        //values[i] = qt.getQuestion_id().toString();
                        values[i] = qt.getAnswer_text();
                        i++;
                    }

                    //You can get the context by invoking getApplicationContext(), getContext(), getBaseContext() or this (when in the activity class).

                    MyListCheckboxAdapter listcheckboxadapter = new MyListCheckboxAdapter(getActivity().getBaseContext(), R.layout.custom_checkboxlist_layout, answers_list);

                    ArrayAdapter<String> adapter = new
                            ArrayAdapter<String>(getActivity().getBaseContext(),
                            android.R.layout.simple_list_item_1, android.R.id.text1,
                            values);

                    answersLv.setAdapter(listcheckboxadapter);

                } else {
                    header = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
                }
            }

            return header;
        }



        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
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
            holder.code.setText(" (" +  answer.getAnswer_text() + ")");
            holder.name.setText(answer.getAnswer_text());
            holder.name.setChecked(answer.isSelected());
            holder.name.setTag(answer);

            return convertView;

        }

    }

}
