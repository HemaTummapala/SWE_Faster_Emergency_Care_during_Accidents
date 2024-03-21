package com.example.betterandfasteremergency.view; 
import android.content.Intent; 
import android.database.sqlite.SQLiteDatabase; 
import androidx.appcompat.app.AppCompatActivity; 
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Toast; 
import com.example.betterandfasteremergency.MainActivity; 
import com.example.betterandfasteremergency.R; 
import com.example.betterandfasteremergency.util.Constants; 
import com.example.betterandfasteremergency.util.Session; 
public class LoginActivity extends AppCompatActivity { 
SQLiteDatabase db; 
private Session session; 
EditText e1,e2; 
Button b1; 
@Override 
protected void onCreate(Bundle savedInstanceState) { 
super.onCreate(savedInstanceState); 
session = new Session(getApplicationContext()); 
setContentView(R.layout.activity_login); 
e1=(EditText)findViewById(R.id.loginPhone); 
e2=(EditText)findViewById(R.id.loginPass); 
b1=(Button)findViewById(R.id.loginConfirm); 
b1.setOnClickListener(new View.OnClickListener() { 
@Override 
public void onClick(View view) { 
final String username=e1.getText().toString(); 
final String password=e2.getText().toString(); 
if(username==null|| password==null || username.length()<=0|| password.length()<=0) 
{ 
Toast.makeText(getApplicationContext(),"Please Enter UserName and 
Password",Toast.LENGTH_SHORT).show(); 
} 
else { 
if (username.equals("admin") && password.equals("admin")) { 
session.setusename("admin"); 
session.setRole("admin"); 
Intent i = new Intent(getApplicationContext(), AdminHome.class); 
startActivity(i); 
} else { 
session.setusename(username); 
session.setRole("user"); 
db=openOrCreateDatabase(Constants.sqLiteDatabase, MODE_PRIVATE, null); 
db.execSQL("create table if not exists login(username varchar)"); 
db.execSQL("insert into login values('"+username+"')"); 
Intent i = new Intent(getApplicationContext(), MainActivity.class); 
startActivity(i); 
Toast.makeText(getApplicationContext(),"Please Enter UserName and 
Password",Toast.LENGTH_SHORT).show(); 
} 
} 
} 
}); 
} 
} 
