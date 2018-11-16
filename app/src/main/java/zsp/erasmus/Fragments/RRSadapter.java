package zsp.erasmus.Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shirwa.simplistic_rss.RssItem;

import zsp.erasmus.MainActivity;
import zsp.erasmus.Menu_Updates;
import zsp.erasmus.Fragments.journal_scrj;
import java.lang.reflect.Array;
import java.util.ArrayList;
import android.support.v4.app.Fragment;

import zsp.erasmus.R;


public class RRSadapter extends ArrayAdapter<RssItem>{

    private ArrayList<RssItem> dataSet;
    private ArrayList<RssItem> dataSettended;
    Context mContext;




    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtdate;

    }

    public RRSadapter(ArrayList<RssItem> data,ArrayList<RssItem> dataex, Context context) {
        super(context, R.layout.basic_list_item, data);
        this.dataSet = data;
        this.dataSettended = dataex;
        this.mContext=context;

    }

  /*  public void onClick(View v) {

       /* int position=(Integer) v.getTag();
        RssItem object= dataSettended.get(position);
        final String[] dane = new String[5];
        dane[0]=object.getTitle();
        dane[1]=object.getDescription();
        dane[2]=object.getImageUrl();
        dane[3]=object.getLink();
        dane[4]=object.getPubDate();
        Snackbar.make(v,dane[3],Snackbar.LENGTH_LONG).show();


    }*/
    public RssItem getItem(int position){
        return dataSettended.get(position);
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RssItem dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.basic_list_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.txtdate = (TextView) convertView.findViewById(R.id.txtDate);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        lastPosition = position;

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtdate.setText(dataModel.getPubDate());


        viewHolder.txtTitle.setTag(position);
        viewHolder.txtdate.setTag(position);

        // Return the completed view to render on screen
        return convertView;
    }
}