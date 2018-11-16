package zsp.erasmus.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import zsp.erasmus.R;

public class MemberDetail extends AppCompatActivity {
    String id;
    String namee;
    String Surname;
    String Country;
    String Email;
    String Teach;
    String Team;
    String Desc;
    String admin;
    String data;
    String fb;
    TextView name;
    TextView email;
    TextView desc;
    TextView funct;
    ImageView imageViewCountry;
    ImageView fbproff;
    private void setup(String namee, String Surname, String Email, String Team, String Desc, String Country,String fb){
        name.setText(namee+" "+Surname);
        email.setText(Email);
        funct.setText(Team);
        desc.setText(Desc);
        fbproff.setVisibility(View.VISIBLE);
        Log.e("fb",fb.toString());
        if(fb== "null"||fb==""){
            fbproff.setVisibility(View.INVISIBLE);
        }
        setTitle("About "+namee);
        switch(Country) {
            case "1":
                imageViewCountry.setImageResource(R.drawable.pl);
                break;
            case "2":
                imageViewCountry.setImageResource(R.drawable.fr);
                break;
            case "3":
                imageViewCountry.setImageResource(R.drawable.cz);
                break;
            case "4":
                imageViewCountry.setImageResource(R.drawable.sp);
                break;
            case "5":
                imageViewCountry.setImageResource(R.drawable.ge);
                break;
            default:
                imageViewCountry.setImageResource(R.drawable.eu);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Intent intent= getIntent();
        data=  intent.getStringExtra("dane");
         name = (TextView)findViewById(R.id.memb_name);
         email = (TextView)findViewById(R.id.memb_email);
         desc = (TextView) findViewById(R.id.memb_descr);
         funct = (TextView)findViewById(R.id.memb_team);
         imageViewCountry =(ImageView)findViewById(R.id.imageView3);
         fbproff= (ImageView)findViewById(R.id.fbprof);
        final Response.Listener<String> response =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);

                    JSONObject jsonObject = json.getJSONObject(0);
                     id = jsonObject.getString("id");
                     namee = jsonObject.getString("Name");
                     Surname = jsonObject.getString("Surname");
                     Country = jsonObject.getString("Country");
                     Email = jsonObject.getString("Email");
                     Teach = jsonObject.getString("Teach");
                     Team = jsonObject.getString("Team");
                     Desc = jsonObject.getString("Desc");
                     fb = jsonObject.getString("fb");
                     admin = jsonObject.getString("Admin");
                    setup(namee,Surname,Email,Team,Desc,Country,fb);

                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        };
        MemberDetGetter memberDetGetter = new MemberDetGetter(data, response);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(memberDetGetter);

        fbproff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString=fb;
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });
    }

    class MemberDetGetter extends StringRequest {
        static final String REGISTER_REQUEST_URL="http://serwer1727017.home.pl/2ti/ErasmusAPP/detailmember.php";
        Map<String,String> params;
        public MemberDetGetter(String datad,Response.Listener<String> listener){
            super(Method.POST,REGISTER_REQUEST_URL,listener,null);
            params = new HashMap<>();
            params.put("id",data);
        }
        public Map<String,String> getParams(){
            return params;
        }
    }
}

