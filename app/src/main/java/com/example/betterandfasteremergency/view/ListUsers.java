package com.example.betterandfasteremergency.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(ListUsers.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        this.listView = (ListView) findViewById(R.id.UsersList);
        this.button = (Button) findViewById(R.id.viewUsersList);
        this.radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        final Session s = new Session(getApplicationContext());
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
            }
        });
    }
}
