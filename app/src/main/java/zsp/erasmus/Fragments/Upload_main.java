package zsp.erasmus.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zsp.erasmus.BrowserWWW;
import zsp.erasmus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Upload_main extends Fragment {


    public Upload_main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_main, container, false);
        FloatingActionButton floatingActionButton = (FloatingActionButton)v.findViewById(R.id.upload_butt);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.frgmntbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Source files");
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(),BrowserWWW.class);
                intent.putExtra("link","http://serwer1727017.home.pl/2ti/ErasmusAPP/upload.php");
                startActivity(intent);
            }
        });
        return v;
    }

}
