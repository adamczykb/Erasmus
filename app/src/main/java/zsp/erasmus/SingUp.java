package zsp.erasmus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class SingUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onButton(View v) {
        int bld = 0;
        boolean correct = true;
        EditText name = (EditText) findViewById(R.id.Name);
        EditText surnam = (EditText) findViewById(R.id.Surnamenav);
        EditText email = (EditText) findViewById(R.id.Email);
        EditText team = (EditText) findViewById(R.id.TeamNav);
        EditText passwd = (EditText) findViewById(R.id.passwd);
        EditText passwdre = (EditText) findViewById(R.id.repasswd);
        Spinner country = (Spinner) findViewById(R.id.countries);
        Switch teacher = (Switch) findViewById(R.id.teacher);
        String[] dane = new String[7];
        dane[0] = name.getText().toString();
        dane[1] = surnam.getText().toString();
        dane[2] = email.getText().toString();
        dane[3] = team.getText().toString();
        dane[4] = passwd.getText().toString();
        dane[5] = passwdre.getText().toString();
        if (-1 == dane[2].indexOf('@')) {
            correct = false;
            email.setError("E-mail is not correct!");
        }
        for (int i = 0; i < 6; i++) {
            if (dane[i].isEmpty()) {
                bld++;
                correct = false;
                switch (i) {
                    case 0:
                        name.setError("This field is empty");
                        break;
                    case 1:
                        surnam.setError("This field is empty");
                        break;
                    case 2:
                        email.setError("This field is empty");
                        break;
                    case 3:
                        team.setError("This field is empty");
                        break;
                    case 4:
                        passwd.setError("This field is empty");
                        break;
                    case 5:
                        passwdre.setError("This field is empty");
                        break;

                }
            }
        }
        if (!Objects.equals(dane[4], dane[5])) {
            correct = false;
            passwdre.setError("This password don't match");
        }
        final AlertDialog alertDialog2 = new AlertDialog.Builder(SingUp.this).create();
        alertDialog2.setTitle("Łączenie");
        alertDialog2.setMessage("Trwa łączenie z serwerem...");
        //String countrynumb = country.getSelectedItem().toString();
        //Toast.makeText(country.getSelectedItem().toString());

        if(correct){
            alertDialog2.show();
            Response.Listener<String> responselistener=new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject =new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success) {
                            alertDialog2.hide();
                            AlertDialog.Builder bu = new AlertDialog.Builder(SingUp.this);
                            bu.setMessage("You have to wait for approve").setNegativeButton("Ok!",b).setTitle("Register Done!").create().show();
                        }else{
                            alertDialog2.hide();
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(SingUp.this);
                            builder3.setMessage("Not possible to connect!").setNegativeButton("Retry",null).setTitle("Possible issue is used e-mail").create().show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest=new RegisterRequest(dane[0],dane[1],dane[2],dane[3],dane[4],country.getSelectedItemPosition(),teacher.isChecked(),responselistener);
            RequestQueue queue = Volley.newRequestQueue(SingUp.this);
            queue.add(registerRequest);

        }

    }
    DialogInterface.OnClickListener b=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent next = new Intent(SingUp.this, MainActivity.class);
            startActivity(next);
        }
    };
}

