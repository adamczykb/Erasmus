package zsp.erasmus.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.shirwa.simplistic_rss.RssItem;
import com.shirwa.simplistic_rss.RssReader;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import zsp.erasmus.Menu_Updates;
import zsp.erasmus.R;
import zsp.erasmus.RSSdetail;

public class journal_scrj extends Fragment {
    ArrayList<RssItem> dataModels, infoModels;
    ListView listView;
    private static RRSadapter adapter;
    FloatingActionButton floatingActionButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.journal_scr, container, false);
        Menu_Updates f = new Menu_Updates();
        Toast.makeText(getContext(), "Loading feeds...", Toast.LENGTH_SHORT).show();
        listView = (ListView) rootView.findViewById(R.id.listv);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fltbut);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.frgmntbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Journal Updates");
        }
        try {
            dataModels = new ArrayList<>();
            infoModels = new ArrayList<>();
            Menu_Updates mYourActiviy = (Menu_Updates) getActivity();

            new GetRssFeed().execute("http://www.erasmus.zspwrzesnia.pl/feed/");
            // (Menu_Updates)getActivity().getApplicationInfo().

            adapter = new RRSadapter(dataModels, infoModels, getActivity().getApplicationContext());

            listView.setAdapter(adapter);
        } catch (Exception E) {
            Log.e("f", E.toString());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RssItem item = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), RSSdetail.class);
                final String[] s = new String[5];
                s[0] = item.getTitle();
                s[1] = item.getDescription();
                s[2] = item.getPubDate();
                s[3] = item.getImageUrl();
                s[4] = item.getLink();
                intent.putExtra("data", s);
                startActivity(intent);

            }
        });
        floatingActionButton.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((1 == Integer.parseInt(((Menu_Updates) getActivity()).data[6])) || (1 == Integer.parseInt(((Menu_Updates) getActivity()).data[7]))) {
                    Intent testIntent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:?subject=" + "//choose your title//" + "&body=" + "//body of your post//" + "&to=" + "huje795fotu@post.wordpress.com");
                    testIntent.setData(data);
                    startActivity(testIntent);
                } else {
                    Toast.makeText(getContext(), "You're not a teacher", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }


    private class GetRssFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {

                RssReader rssReader;
                rssReader = new RssReader(params[0]);

                for (RssItem item : rssReader.getItems()) {
                    dataModels.add(new RssItem(item.getTitle(), item.getPubDate().toString()));
                    infoModels.add(new RssItem(item.getTitle(), item.getPubDate().toString(), item.getDescription(), item.getImageUrl(), item.getLink()));
                }
            } catch (Exception e) {
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }

    }
}





/*


    private ListView mList;
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.journal_scr, container, false);

        mList = (ListView) rootView.findViewById(R.id.listv);

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.basic_list_item);
        getActivity().setTitle("Journal Updates");
        new GetRssFeed().execute("http://erasmus.zspwrzesnia.pl/feed");
        mList.setClickable(true);
        return rootView;
    }

private class GetRssFeed extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        try {
            RssReader rssReader;
            rssReader = new RssReader(params[0]);

            for (RssItem item : rssReader.getItems()) {
                adapter.add(item.getTitle());
            }
        } catch (Exception e) {
            Log.v("Error Parsing Data", e + "");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.notifyDataSetChanged();
        mList.setAdapter(adapter);
    }


}*/