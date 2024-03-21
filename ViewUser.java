package com.example.betterandfasteremergency.view; import android.content.Intent;
import android.location.Address; import android.location.Geocoder;
import androidx.appcompat.app.AppCompatActivity; import android.os.Bundle;
import android.util.Log; import android.view.View; import android.widget.Button;
import android.widget.EditText; import android.widget.TextView;
import com.google.firebase.database.DataSnapshot; import com.google.firebase.database.DatabaseError; import com.google.firebase.database.ValueEventListener;
import java.util.List; import java.util.Locale;
import com.example.betterandfasteremergency.MainActivity; import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.dao.DAO; import com.example.betterandfasteremergency.form.User; import com.example.betterandfasteremergency.util.Constants; import com.example.betterandfasteremergency.util.Session;
public class ViewUser extends AppCompatActivity {
TextView t1,t2,t3,t4,t5; Button request;
Button cancel; Button delete; EditText description;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState); setContentView(R.layout.activity_view_user);
cancel=(Button) findViewById(R.id.viewUserCanel); delete=(Button) findViewById(R.id.viewUserDelete);
final Session session=new Session(getApplicationContext()); final String role=session.getRole();
if(!role.equals("admin")) { delete.setEnabled(false);
}
Log.v("in view user role ",role);
Intent i=getIntent(); savedInstanceState=i.getExtras();
final String userId=savedInstanceState.getString("userid"); Log.v("in view user userid ",userId);
t1=(TextView) findViewById(R.id.textviewname); t2=(TextView)findViewById(R.id.textviewemail); t3=(TextView)findViewById(R.id.textviewmobile); t4=(TextView)findViewById(R.id.textviewaddress); t5=(TextView)findViewById(R.id.textviewdescription);
DAO d=new DAO();
d.getDBReference(Constants.USER_DB).child(userId).addListenerForSingleValueEv ent(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) { User user=dataSnapshot.getValue(User.class);
if(user!=null)
{
if(user.getType().equals("ambulance"))
{
t1.setText("Driver Name : "+user.getName());
}else if(user.getType().equals("hospital"))
{
t1.setText("Hospital Name : "+user.getName());
}else if(user.getType().equals("user"))
{
t1.setText("Patient Name : "+user.getName());
}
else if(user.getType().equals("bloodbank"))
{
t1.setText("Blood Bank Name : "+user.getName());
}
String[] workLocation=user.getAddress().split(","); Geocoder geocoder;
List<Address> addresses;
geocoder = new Geocoder(getApplicationContext(), Locale.getDefault()); String userAddress="";
try {
addresses = geocoder.getFromLocation(new Double(workLocation[0]),new Double(workLocation[1]), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
String city = addresses.get(0).getLocality();
String state = addresses.get(0).getAdminArea(); String country = addresses.get(0).getCountryName(); String postalCode = addresses.get(0).getPostalCode();
String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
if(address!=null)
{
}
userAddress=userAddress+address+"\n";
if(city!=null)
{
userAddress=userAddress+city+"\n";
}
if(state!=null)
{
}
userAddress=userAddress+state+"\n";
if(country!=null)
{
}
userAddress=userAddress+country+"\n";
if(postalCode!=null)
{
userAddress=userAddress+postalCode+"\n";
}
if(knownName!=null)
{
userAddress=userAddress+knownName+"\n";
}
}
catch(Exception e)
{
Log.v("voidmain ","in on succes ");
}
Log.v("voidmai work address ",userAddress);
t2.setText("Email: "+user.getEmail()); t3.setText("Mobile: "+user.getMobile()); t4.setText("Address: "+userAddress); t5.setText("Description : "+user.getDescription());
}
}
@Override
public void onCancelled(DatabaseError databaseError) {
}
});
cancel.setOnClickListener(new View.OnClickListener() { @Override
public void onClick(View view) { Intent i =null;
if(role.equals("admin")) {
i = new Intent(getApplicationContext(),AdminHome.class);
}else {
i = new Intent(getApplicationContext(),MainActivity.class);
}
startActivity(i);
}
});
delete.setOnClickListener(new View.OnClickListener() { @Override
public void onClick(View view) {
DAO d=new DAO();
d.deleteObject(Constants.USER_DB,userId);
}
});
}
}
Intent i = new Intent(getApplicationContext(),AdminHome.class); startActivity(i);
