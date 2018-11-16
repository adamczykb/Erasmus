package zsp.erasmus.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import zsp.erasmus.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class admin extends Fragment {


    public admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String cos =preferences.getString("edit_text_preference_3","2");
        Log.e("ysunrame",cos);
        Toast.makeText(this.getContext(),cos,Toast.LENGTH_LONG);

        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

}
