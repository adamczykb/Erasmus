package zsp.erasmus;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zsp.erasmus.Fragments.MemberDetail;

public class Forum_post_detail extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Forum_post_detail.forum_datamodel> comment;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private static Forum_post_detail.forum_datamodel contact[];
    String[] data;
    String[] info;
    Button author;
    Integer color=0;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forum_post_detail);
        Intent intent = getIntent();
        data = intent.getStringArrayExtra("dane");
        TextView title = (TextView) findViewById(R.id.title_post);
        TextView desc = (TextView) findViewById(R.id.desc_post);
        TextView date = (TextView) findViewById(R.id.date_forum);
        score = (TextView) findViewById(R.id.score_post);
        Button author = (Button) findViewById(R.id.name_post);
        Button minus = (Button) findViewById(R.id.minus_post);
        Button plus = (Button) findViewById(R.id.plus_post);
        info = intent.getStringArrayExtra("data");
        recyclerView = (RecyclerView) findViewById(R.id.for_comm_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        comment = new ArrayList<Forum_post_detail.forum_datamodel>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.comment_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(Forum_post_detail.this).create();
                alertDialog.setTitle("Type the comment");
                EditText text= new EditText(Forum_post_detail.this);
                text.setInputType(InputType.TYPE_CLASS_TEXT);
                text.setHint("Type there");
                alertDialog.setView(text);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sent", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                    Response.Listener<String> responselistener=new Response.Listener<String>(){

                                        @Override
                                        public void onResponse(String response3) {
                                            try {
                                                JSONObject jsonObject =new JSONObject(response3);
                                              if(jsonObject.getBoolean("success")) {
                                                  Toast.makeText(Forum_post_detail.this, "Sending complete", Toast.LENGTH_LONG).show();
                                                  Forum_post_detail.Gettter getter = new Forum_post_detail.Gettter(data[0], response2);
                                                  RequestQueue queue = Volley.newRequestQueue(Forum_post_detail.this);
                                                  queue.add(getter);
                                              }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    SendComm registerRequest=new SendComm(text.getText().toString(),responselistener);
                                    RequestQueue queue = Volley.newRequestQueue(Forum_post_detail.this);
                                    queue.add(registerRequest);

                                }
                            });


                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialog.show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setTitle("Forum");
        }
        score.setText(data[2]);
        desc.setText(data[5]);
        title.setText(data[1]);
        date.setText(data[6]);
        author.setText(data[7] + " " + data[8]);
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Forum_post_detail.this, MemberDetail.class);
                intent1.putExtra("dane", data[3]);
                startActivity(intent1);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(1);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(0);
            }
        });
        Forum_post_detail.Gettter getter = new Forum_post_detail.Gettter(data[0], response2);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getter);
    }

    void send(int numer_op) {
        final Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        if (numer_op == 0)
                            score.setText((Integer.valueOf(score.getText().toString()) + 1) + "");
                        if (numer_op == 1)
                            score.setText((Integer.valueOf(score.getText().toString()) - 1) + "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        };

        Forum_post_detail.Send send = new Forum_post_detail.Send(numer_op, data[0], info[0], response);
        RequestQueue queue = Volley.newRequestQueue(Forum_post_detail.this);
        queue.add(send);

    }

    class Send extends StringRequest {
        static final String REGISTER_REQUEST_URL = "http://serwer1727017.home.pl/2ti/ErasmusAPP/forum_rater.php";
        Map<String, String> params;

        public Send(int numer_op, String ID, String usr_id, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("op", (String.valueOf(numer_op)));
            params.put("ID", ID);
            params.put("usr_id", usr_id);

        }

        public Map<String, String> getParams() {
            return params;
        }
    }

    final Response.Listener<String> response2 = new Response.Listener<String>() {

        @Override
        public void onResponse(String response2) {
            try {
                JSONArray json = new JSONArray(response2);
                contact = new Forum_post_detail.forum_datamodel[json.length()];
                for (int i = 0; i < json.length(); i++) {
                    JSONObject c = json.getJSONObject(i);

                    String ID = c.getString("ID");
                    String Descr = c.getString("Descr");
                    String date = c.getString("date");
                    String name = c.getString("name");
                    String surname = c.getString("surname");
                    String country = c.getString("team");


                    // tmp hash map for single contact
                    //comment.clear();
                    contact[i] = new Forum_post_detail.forum_datamodel(ID, Descr, date, name, surname, country);
                    Log.i("Cardv", contact[i].date);
                    comment.add(contact[i]);
                }
                adapter = new Forum_post_detail.FrumCrdv(comment);

                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    };


     /*   private abstract static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
            private Forum_post_detail.RecyclerItemClickListener.OnItemClickListener mListener;

            public interface OnItemClickListener{
                public void onItemClick(View view, int position);
            }
            GestureDetector mGestureDetector;
            public RecyclerItemClickListener(Context context, Forum_post_detail.RecyclerItemClickListener.OnItemClickListener listener) {
                mListener = listener;
                mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && this.mListener != null && mGestureDetector.onTouchEvent(e)) {
                    this.mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
            }
        }*/
        class Gettter extends StringRequest {
            static final String REGISTER_REQUEST_URL = "http://serwer1727017.home.pl/2ti/ErasmusAPP/comm_downloader.php";
            Map<String, String> params;

            public Gettter(String ID_post, Response.Listener<String> listener) {
                super(Method.POST, REGISTER_REQUEST_URL, listener, null);
                params = new HashMap<>();
                params.put("id", ID_post);
            }

            public Map<String, String> getParams() {
                return params;
            }
        }


        class forum_datamodel {
            String ID;
            String Descr;
            String date;
            String name;
            String surname;
            String country;

            forum_datamodel(String ID, String Descr, String date, String name, String surname,String country) {
                this.ID = ID;

                this.Descr = Descr;
                this.date = date;
                this.name = name;
                this.surname=surname;
                this.country=country;
            }

            public String getID() {
                return ID;
            }



            public String getDescr() {
                return Descr;
            }

            public String getdate() {
                return date;
            }

            public String getname() {
                return name;
            }

            public String getsurname() {
                return surname;
            }

            public String getteam() {
                return country;
            }

            public String[] getData() {
                String[] str = new String[6];
                str[0] = ID;
                str[1] = Descr;
                str[2] = date;
                str[3] = name;
                str[4] = surname;
                str[5] = country;
                return str;
            }

        }


        public class FrumCrdv extends RecyclerView.Adapter<zsp.erasmus.Forum_post_detail.FrumCrdv.MyViewHolder> {

            private ArrayList<Forum_post_detail.forum_datamodel> dataSet;

            public  class MyViewHolder extends RecyclerView.ViewHolder {

                TextView textdesc;
                TextView textTeam;
                ImageView imageView;
                CardView mCardViewBottom;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    this.textdesc = (TextView) itemView.findViewById(R.id.comment_descc);
                    this.textTeam = (TextView) itemView.findViewById(R.id.comment_who);
                    this.mCardViewBottom = (CardView) itemView.findViewById(R.id.card_view3);
                    this.imageView=(ImageView) itemView.findViewById(R.id.comment_img);

                }
            }


            public FrumCrdv(ArrayList<Forum_post_detail.forum_datamodel> comment) {
                this.dataSet = comment;
            }

            @Override
            public Forum_post_detail.FrumCrdv.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.crd_comment, parent, false);

                view.setOnClickListener(myOnClickListener);

                Forum_post_detail.FrumCrdv.MyViewHolder myViewHolder = new Forum_post_detail.FrumCrdv.MyViewHolder(view);
                return myViewHolder;
            }


            @SuppressLint("ResourceType")
            @Override
            public void onBindViewHolder(Forum_post_detail.FrumCrdv.MyViewHolder holder, int position) {
                TextView name = holder.textdesc;
                TextView team = holder.textTeam;
                ImageView cardView = holder.imageView;
           /* if(dataSet.get(position).Title.length()>=23){
                name.setText(dataSet.get(position).Title.substring(0,23)+"...");
            }else {*/
                name.setText(dataSet.get(position).Descr);
                RelativeLayout.LayoutParams params = new
                        RelativeLayout.LayoutParams(CardView.LayoutParams.MATCH_PARENT,
                        CardView.LayoutParams.WRAP_CONTENT);
                // Set the height by params
                //params.height=1700;
                // set height of RecyclerView
                holder.mCardViewBottom.setLayoutParams(params);
                if(color%2==0){
                    holder.mCardViewBottom.setCardBackgroundColor(Color.parseColor("#f7f7f7"));}
                else{
                    holder.mCardViewBottom.setCardBackgroundColor(Color.parseColor("#e8e8e8"));
                }
                color++;
                team.setText(dataSet.get(position).date.toString()+" "+dataSet.get(position).name+" "+dataSet.get(position).surname);
                switch(dataSet.get(position).country) {
                    case "1":
                        cardView.setImageResource(R.drawable.pl);
                        break;
                    case "2":
                        cardView.setImageResource(R.drawable.fr);
                        break;
                    case "3":
                        cardView.setImageResource(R.drawable.cz);
                        break;
                    case "4":
                        cardView.setImageResource(R.drawable.sp);
                        break;
                    case "5":
                        cardView.setImageResource(R.drawable.ge);
                        break;
                    default:
                        cardView.setImageResource(R.drawable.eu);
                        break;
                }
            }




            @Override
            public int getItemCount() {
                return dataSet.size();
            }
        }
    public class SendComm extends StringRequest {
        static final String REGISTER_REQUEST_URL="http://serwer1727017.home.pl/2ti/ErasmusAPP/add_comment.php";
        Map<String,String> params;
        public SendComm(String txt,Response.Listener<String> listener){
            super(Method.POST,REGISTER_REQUEST_URL,listener,null);
            params = new HashMap<>();
            params.put("ID_post",data[0]);
            params.put("txt",txt);
            params.put("id_usr",info[0]);
            comment.clear();
        }
        public Map<String,String> getParams(){
            return params;
        }
    }
    }






