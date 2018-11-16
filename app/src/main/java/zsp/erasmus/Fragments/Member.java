package zsp.erasmus.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import zsp.erasmus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Member extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Member_datamodel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private static Member_datamodel contact[];

    public Member() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_member, container, false);
        final Context context = getContext();
        //myOnClickListener = new MyOnClickListener(getContext());

        recyclerView = view.findViewById(R.id.frg_memb_rlv);
        recyclerView.setHasFixedSize(true);
        //SearchView searchView = (SearchView)view.findViewById(R.id.searchview1);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        EditText search = (EditText)view.findViewById(R.id.membersearch);
        data = new ArrayList<Member_datamodel>();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.frgmntbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Member list");
        }
        final Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    contact = new Member_datamodel[json.length()];
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject c = json.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("Name");
                        String Surname = c.getString("Surname");
                        String Country = c.getString("Country");
                        String Email = c.getString("Email");
                        String Teach = c.getString("Teach");
                        String Team = c.getString("Team");
                        String Desc = c.getString("Desc");
                        String admin = c.getString("Admin");


                        // tmp hash map for single contact
                        contact[i] = new Member_datamodel(id, name, Surname, Country, Email, Teach, Team, Desc,admin);
                        Log.i("Cardv", contact[i].getTeam());
                        data.add(contact[i]);
                    }
                    adapter = new AdapterCrdv(data);
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(getContext(),MemberDetail.class);
                                    intent.putExtra("dane",contact[position].getId_());
                                    Log.i("fupa",contact[position].getId_());
                                    startActivity(intent);
                                }
                            }) {
                                @Override
                                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                                    Toast.makeText(getContext(),"Fatal error",Toast.LENGTH_LONG);
                                }
                            }
                    );
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        };
        Getter getter = new Getter("", response);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(getter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Getter getter = new Getter(search.getText().toString(), response);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(getter);
            }
        });

        return view;
    }

    /*  public class RowViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
          private RecyclerViewClickListener mListener;

          RowViewHolder(View v,RecyclerViewClickListener listener) {
              super(v);
              mListener=listener;
              v.setOnClickListener(this);
          }

          @Override
          public void onClick(View view) {
              mListener.onClick(view,getAdapterPosition());

          }
      }
  */

    private abstract static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener{
        public void onItemClick(View view, int position);
        }
        GestureDetector mGestureDetector;
        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
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
    }

    class Getter extends StringRequest {
        static final String REGISTER_REQUEST_URL = "http://serwer1727017.home.pl/2ti/ErasmusAPP/MemberGetter.php";
        Map<String, String> params;


        public Getter(String search, Response.Listener<String> listener) {
            super(Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("search",search);
        }

        public Map<String, String> getParams() {
            return params;
        }
    }
}