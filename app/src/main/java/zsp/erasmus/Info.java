package zsp.erasmus;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Info extends Fragment {


    public Info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_info, container, false);
        TextView info = (TextView) v.findViewById(R.id.info_ja);
        ImageButton butt = (ImageButton) v.findViewById(R.id.butt_wrz);
        ImageButton butt2 = (ImageButton) v.findViewById(R.id.butt_Zsp);
        TextView stach = (TextView) v.findViewById(R.id.stach);
        Button butt3= (Button)v.findViewById(R.id.features_butt);
        stach.setText(R.string.fragment_info_stachu);
        info.setText(R.string.fragment_info_ja);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.frgmntbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("About");
        }
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BrowserWWW.class);
                intent.putExtra("link","http://www.wrzesnia.pl/");
                startActivity(intent);
            }
        });
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),BrowserWWW.class);
                intent.putExtra("link","http://zspwrzesnia.pl/");
                startActivity(intent);
            }
        });
        butt3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                final android.support.v7.app.AlertDialog alertDialog2 = new android.support.v7.app.AlertDialog.Builder(getActivity()).create();
                alertDialog2.setTitle("Features");
                alertDialog2.setMessage("We are going to repair 'journal' and add pushup notofications. Also we would like to add other languages");

                alertDialog2.show();

            }
        });
        return v;
    }

}
