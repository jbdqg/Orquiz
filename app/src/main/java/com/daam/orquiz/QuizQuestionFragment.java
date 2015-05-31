package com.daam.orquiz;

//import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.daam.orquiz.business.Utils;
import com.daam.orquiz.data.Answer;
import com.daam.orquiz.data.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by johnny on 25-05-2015.
 */
//retirado de https://github.com/codepath/android_guides/wiki/ViewPager-with-FragmentPagerAdapter
public class QuizQuestionFragment extends Fragment {
    // Store instance variables
    private Question question = new Question();
    private List<Answer> answers = new ArrayList<Answer>();
    private Boolean hasQuestion = true;
    private Utils.MyListCheckboxAdapter oneCheckboxAdapter = null;
    private Utils.MyListRadiobuttonAdapter oneRadiobuttonAdapter = null;

    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    //public static QuizQuestionFragment newInstance(int page, String title, Map questionData) {
    public static QuizQuestionFragment newInstance(int page, Map fragmentData) {
        QuizQuestionFragment fragmentFirst = new QuizQuestionFragment();

        if(fragmentData.size() != 0 && fragmentData.get("question") instanceof Question){
            fragmentFirst.question = (Question) fragmentData.get("question");
            fragmentFirst.answers = (List<Answer>) fragmentData.get("answers");

            Bundle args = new Bundle();
            //args.putInt("someInt", fragmentFirst.question.getFieldId());
            args.putInt("someInt", page);
            args.putString("someTitle", fragmentFirst.question.getFieldText());
            fragmentFirst.setArguments(args);
        }else{
            //não há mais perguntas
            fragmentFirst.hasQuestion = false;
            Bundle args = new Bundle();
            args.putInt("someInt", page);
            args.putString("someTitle", fragmentData.get("title").toString());
            fragmentFirst.setArguments(args);
        }

        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        //page = getArguments().getInt("someInt");
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_first, container, false);

        View view = null;
        if(this.hasQuestion == true){
            if(this.question.getFieldType().equalsIgnoreCase("multiplechoice")){
                view = inflater.inflate(R.layout.view_multiplechoice, container, false);

                /*public static Drawable LoadImageFromWebOperations(String url) {
                    try {
                        InputStream is = (InputStream) new URL(url).getContent();
                        Drawable d = Drawable.createFromStream(is, "src name");
                        return d;
                    } catch (Exception e) {
                        return null;
                    }
                }
                */

                ListView answersLv = (ListView) view.findViewById(R.id.answerslv);

                //Utils.MyListCheckboxAdapter listCheckboxAdapter = new Utils.MyListCheckboxAdapter(this.getActivity().getBaseContext(), R.layout.custom_checkboxlist_layout, this.answers);
                oneCheckboxAdapter = new Utils.MyListCheckboxAdapter(this.getActivity().getBaseContext(), R.layout.custom_checkboxlist_layout, this.answers);

                int count = this.answers.size();
                String[] values = new String[count];
                int i = 0;
                for (Answer qt : this.answers) {
                    //String log = "Id: " + qt.getFieldId() + " ,Text: " + qt.getFieldText() + " ,Url: " + qt.getFieldUrl();
                    //Log.d("Answer: ", log);
                    //values[i] = qt.getFieldId().toString();
                    values[i] = qt.getFieldText();
                    i++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
                answersLv.setAdapter(oneCheckboxAdapter);

                //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                //tvLabel.setText(page + " -- " + title);
                TextView tvLabel = (TextView) view.findViewById(R.id.text);
                tvLabel.setText(page + " -- " + title);

                ImageView image = (ImageView) view.findViewById(R.id.image);
                image.setImageResource(R.drawable.orange_small);

            }else if(this.question.getFieldType().equalsIgnoreCase("uniquechoice")){
                view = inflater.inflate(R.layout.view_uniquechoice, container, false);

                ListView answersLv = (ListView) view.findViewById(R.id.answerslv);

                oneRadiobuttonAdapter = new Utils.MyListRadiobuttonAdapter(this.getActivity().getBaseContext(), R.layout.custom_checkboxlist_layout, this.answers);

                int count = this.answers.size();
                String[] values = new String[count];
                int i = 0;
                for (Answer qt : this.answers) {
                    //String log = "Id: " + qt.getFieldId() + " ,Text: " + qt.getFieldText() + " ,Url: " + qt.getFieldUrl();
                    //Log.d("Answer: ", log);
                    //values[i] = qt.getFieldId().toString();
                    values[i] = qt.getFieldText();
                    i++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity().getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
                answersLv.setAdapter(oneRadiobuttonAdapter);

                //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                //tvLabel.setText(page + " -- " + title);
                TextView tvLabel = (TextView) view.findViewById(R.id.text);
                tvLabel.setText(page + " -- " + title);
            }
        }else{
            view = inflater.inflate(R.layout.view_submitquiz, container, false);

            final Button submit_quiz_bt = (Button) view.findViewById(R.id.button);
            submit_quiz_bt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                //para que quando se guarda a participação se saiba que o user premiu o botão para submeter;
                ((QuizActivity)getActivity()).sumbmit_button_pressed = true;

                Intent intent = new Intent(container.getContext(), MainActivity.class);
                //ir para a view de resultados. obter depois os resultados do último quiz submetido para mostrar no interface
                intent.putExtra("NEXT_DRAWER_POSITION", 2);
                intent.putExtra("LAST_PARTICIPATION", true);

                startActivity(intent);

                }
            });

        }


        return view;
    }

    public Question getQuestion(){
        return this.question;
    }

    public List<Answer> getAnswers(){
        return this.answers;
    }

    public Utils.MyListCheckboxAdapter getCheckBoxAdapter(){
        return this.oneCheckboxAdapter;
    }

    public Utils.MyListRadiobuttonAdapter getRadiobuttonAdapter(){
        return this.oneRadiobuttonAdapter;
    }

}
