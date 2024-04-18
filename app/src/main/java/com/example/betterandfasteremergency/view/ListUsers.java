package com.example.betterandfasteremergency.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.dao.DAO;
import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Constants;
import com.example.betterandfasteremergency.util.MapUtil;
import com.example.betterandfasteremergency.util.Session;

public class ListUsers extends AppCompatActivity {
    Button button;
    ListView listView;
    RadioButton radioButton;
    RadioGroup radioGroup;
    ImageView i1;
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(ListUsers.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        this.listView = (ListView) findViewById(R.id.UsersList);
        this.button = (Button) findViewById(R.id.viewUsersList);
        this.radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        final Session s = new Session(getApplicationContext());
        this.i1=(ImageView) findViewById(R.id.back);
        this.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int selectedType = ListUsers.this.radioGroup.getCheckedRadioButtonId();
                ListUsers listUsers = ListUsers.this;
                listUsers.radioButton = (RadioButton) listUsers.findViewById(selectedType);
                String type = ListUsers.this.radioButton.getText().toString();
                Log.v("seleted type ", type);
                new DAO().setDataToAdapterList(ListUsers.this.listView, User.class, Constants.USER_DB, type);
            }
        });
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("in list action perform ", "in list action perform");
                String item = ListUsers.this.listView.getItemAtPosition(i).toString();
                Intent intent = new Intent(ListUsers.this.getApplicationContext(), ViewUser.class);
                intent.putExtra("userid", MapUtil.stringToMap(s.getViewMap()).get(item));
                ListUsers.this.startActivity(intent);
                finish();
            }
        });
        this.radioGroup.check(radioGroup.getChildAt(0).getId());
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which RadioButton is checked
                for (int i = 0; i < group.getChildCount(); i++) {
                    View radioButton = group.getChildAt(i);
                    if (radioButton instanceof RadioButton) {
                        RadioButton checkedRadioButton = (RadioButton) radioButton;
                        // Check if this RadioButton is checked
                        if (checkedRadioButton.isChecked()) {
                            // Perform action based on the text of the checked RadioButton
                            String selectedOption = checkedRadioButton.getText().toString();
                            switch (selectedOption) {
                                case "Ambulance":
                                 // Do something when "Ambulance" RadioButton is selected
                                    break;
                                case "Hospital":
                                    // Do something when "Hospital" RadioButton is selected
                                    break;
                                case "Blood Bank":
                                    // Do something when "Blood Bank" RadioButton is selected
                                    break;
                            }
                            // Exit the loop after finding the checked RadioButton
                            break;
                        }
                    }
                }
            }
        });

        this.i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(ListUsers.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
