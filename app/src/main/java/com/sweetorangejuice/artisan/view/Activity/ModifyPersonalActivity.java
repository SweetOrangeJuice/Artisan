package com.sweetorangejuice.artisan.view.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.model.PersonalBean;

public class ModifyPersonalActivity extends AppCompatActivity {
    private EditText genderEditText;
    private EditText ageEditText;
    private EditText schoolEditText;
    private EditText tagEditText;
    private Button confirmButton;
    private String genderString;
    private String ageString;
    private String schoolString;
    private String tagString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal);
        genderEditText = (EditText) findViewById(R.id.fragment_sub_personal_profiles_gender_edit);
        ageEditText = (EditText) findViewById(R.id.fragment_sub_personal_profiles_age_edit);
        schoolEditText = (EditText) findViewById(R.id.fragment_sub_personal_profiles_school_edit);
        tagEditText = (EditText) findViewById(R.id.fragment_sub_personal_profiles_tag_edit);
        confirmButton = (Button)findViewById(R.id.fragment_personal_modify_profiles);
        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                genderString = genderEditText.getText().toString();
                ageString = ageEditText.getText().toString();
                schoolString = schoolEditText.getText().toString();
                tagString = tagEditText.getText().toString();
                PersonalBean personalBean = new PersonalBean();
                personalBean.setGender(genderString);
                personalBean.setAge(ageString);
                personalBean.setSchool(schoolString);
                personalBean.setTag(tagString);
            }
        });
    }
}
