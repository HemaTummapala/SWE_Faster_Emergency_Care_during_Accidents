package com.example.betterandfasteremergency.view;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.dao.DAO;
import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Constants;
import com.example.betterandfasteremergency.util.MapUtil;
import com.example.betterandfasteremergency.util.Session;
public class AdminListUser extends AppCompatActivity {
    Button button;
    ListView listView;
    RadioButton radioButton;
    RadioGroup radioGroup;
    ImageView i1;
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(AdminListUser.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_user);
        this.listView = (ListView) findViewById(R.id.AdminUsersList);
        this.i1=(ImageView) findViewById(R.id.back);
        new DAO().setDataToAdapterList(this.listView, User.class, Constants.USER_DB, "all");
        final Session s = new Session(getApplicationContext());
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("in list action perform ", "in list action perform");
                String item = AdminListUser.this.listView.getItemAtPosition(i).toString();
                Intent intent = new Intent(AdminListUser.this.getApplicationContext(), ViewUser.class);
                intent.putExtra("userid", MapUtil.stringToMap(s.getViewMap()).get(item));
                AdminListUser.this.startActivity(intent);
                finish();
            }
        });
        this.i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /*Back press handling*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminListUser.this, AdminHome.class);
        startActivity(intent);
        finish();
    }
}