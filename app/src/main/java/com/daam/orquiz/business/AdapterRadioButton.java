package com.daam.orquiz.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.daam.orquiz.R;
import com.daam.orquiz.data.Answer;

import java.util.ArrayList;
import java.util.List;

public class AdapterRadioButton extends ArrayAdapter<Answer> {

    private List<Answer> answerList;

    public AdapterRadioButton(Context context, int textViewResourceId, List<Answer> answerList) {
        super(context, textViewResourceId, answerList);
        this.answerList = new ArrayList<Answer>();
        this.answerList.addAll(answerList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RadioButton radioButton = null;

        if ( convertView == null ) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.custom_radiobuttonlist_layout, null);

            radioButton = (RadioButton) convertView.findViewById(R.id.radioButton1);
            convertView.setTag(radioButton);

            final RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup1);

            boolean checked = false;
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    compoundButton.setChecked(b);
                }
            });
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton selected = (RadioButton) view;

                    Answer answer = (Answer) selected.getTag();
                    answer.setSelected(selected.isChecked());
                }
            });
        } else {
            radioButton = (RadioButton) convertView.getTag();
        }

        Answer answer = answerList.get(position);
        radioButton.setText(answer.getFieldText());
        radioButton.setTag(answer);

        return convertView;
    }

}
