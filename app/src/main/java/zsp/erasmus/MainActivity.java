package zsp.erasmus;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = null;

        try{
            SQLiteOpenHelper sqLiteOpenHelper = new Database(this);
            db = sqLiteOpenHelper.getReadableDatabase();
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Unexceptable error",Toast.LENGTH_LONG);
        }
        final Button login = (Button)findViewById(R.id.loginbutt);
        final TextView email = (TextView)findViewById(R.id.emailBar);
        final TextView passwd = (TextView)findViewById(R.id.passwordBar);
        final CheckBox checkBox = (CheckBox)findViewById(R.id.rememberm);
        final String[] dane = new String[11];
        final SQLiteDatabase dbe = db;
         cursor = db.query("USER",new String[]{"COUNT(*)"},null,null,null,null,null);
        cursor.moveToFirst();
        Cursor cursorl = db.query("SETTINGS", new String[]{"*"},null,null,null,null,null);
        if(0 == cursorl.getColumnCount()){ ContentValues vall = new ContentValues();
        vall.put("STATUS", 1);
        db.insert("SETTINGS",null,vall);
          AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Welcome!");
            alertDialog.setMessage("TUTAJ NOTKA POWITALNA");
            alertDialog.show();
        }

        if(0 < cursor.getInt(0)){
            cursor = db.query("USER",new String[]{"_id","EMAIL","PASS"},null,null,null,null,"_id DESC","1");
            cursor.moveToFirst();
            email.setText(cursor.getString(1));
            passwd.setText(cursor.getString(2));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    login.performClick();
                }
            }, 1);
        }
        setTitle("EHoTFACTS Erasmus+");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String em= email.getText().toString();
                final String paswde= passwd.getText().toString();
                final AlertDialog alertDialog2 = new AlertDialog.Builder(MainActivity.this).create();
                if(0 > cursor.getInt(0)) {
                    alertDialog2.setTitle("Connecting...");
                    alertDialog2.setMessage("Trwa łączenie z serwerem...");
                    alertDialog2.show();
                }

                final Response.Listener<String> response =new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success= jsonObject.getBoolean("success");
                            if(success){

                                Intent next = new Intent(MainActivity.this,Menu_Updates.class);
                                dane[0]= jsonObject.getString("ID");
                                dane[1]=jsonObject.getString("Name");
                                dane[2]=jsonObject.getString("surname");
                                dane[3]=jsonObject.getString("email");
                                dane[4]=jsonObject.getString("team");
                                dane[5]= jsonObject.getString("country");
                                dane[6]= jsonObject.getString("teacher");
                                dane[7]= jsonObject.getString("admin");
                                dane[8]= jsonObject.getString("descr");
                                dane[9]= jsonObject.getString("fb");
                                alertDialog2.hide();
                                next.putExtra("dane",dane);
                                if(checkBox.isChecked()){

                                        ContentValues val = new ContentValues();
                                        val.put("EMAIL", em);
                                        val.put("PASS", paswde);
                                        dbe.insert("USER",null,val);

                                }
                                startActivity(next);

                            }else{
                                alertDialog2.hide();
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Not possible to connect!").setNegativeButton("Retry",null).setTitle("Login Failed").create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (Exception ee){
                            ee.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(em,paswde,response);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }




    public void signUp(View v) {
        Intent in = new Intent(this, SingUp.class);
        startActivity(in);

    }
}
