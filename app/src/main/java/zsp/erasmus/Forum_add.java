package zsp.erasmus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forum_add extends AppCompatActivity {
    public String title,ID,desc,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_add);
        setTitle("New Thread");
        TextView title = (TextView)findViewById(R.id.add_title);
        TextView desc = (TextView)findViewById(R.id.add_desc);
        TextView cat = (TextView)findViewById(R.id.add_category);
        Intent intent = getIntent();
        String ID=intent.getStringExtra("ID");
        String[] reszta = intent.getStringArrayExtra("data");
        Button button =(Button)findViewById(R.id.add_forum_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Response.Listener<String> response =new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success= jsonObject.getBoolean("success");
                            if(success){
                                final AlertDialog alertDialog2 = new AlertDialog.Builder(Forum_add.this).create();
                                alertDialog2.setMessage("Operation success");
                                alertDialog2.setTitle("Sending success");
                                alertDialog2.setButton(AlertDialog.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();

                                    }
                                });
                                alertDialog2.show();



                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(Forum_add.this);
                                builder.setMessage("Operation got an error").setNegativeButton("Retry",null).setTitle("Sending Failed").create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (Exception ee){
                            ee.printStackTrace();
                        }
                    }
                };
                if(title.getText().toString().isEmpty()||desc.getText().toString().isEmpty()||cat.getText().toString().isEmpty()) {
                    if(title.getText().toString().isEmpty()) title.setError("This field cannot be empty");
                        if(desc.getText().toString().isEmpty()) desc.setError("This field cannot be empty");
                            if(cat.getText().toString().isEmpty()) cat.setError("This field cannot be empty");
                }else{
                    Send send = new Send(ID, title.getText().toString(), desc.getText().toString(), cat.getText().toString(), response);
                    RequestQueue queue = Volley.newRequestQueue(Forum_add.this);
                    queue.add(send);
                }
            }
        });


    }
    class Send extends StringRequest {
        static final String REGISTER_REQUEST_URL="http://serwer1727017.home.pl/2ti/ErasmusAPP/forum_add.php";
        Map<String,String> params;
        public Send(String ID,String title,String desc, String category, Response.Listener<String> listener){
            super(Method.POST,REGISTER_REQUEST_URL,listener,null);
            params = new HashMap<>();
            params.put("ID",ID);
            params.put("title",title);
            params.put("desc",desc);
            params.put("category",category);
        }
        public Map<String,String> getParams(){
            return params;
        }
    }
}

