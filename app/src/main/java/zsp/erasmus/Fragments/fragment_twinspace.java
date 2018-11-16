package zsp.erasmus.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.Toolbar;

import zsp.erasmus.Menu_Updates;
import zsp.erasmus.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_twinspace extends Fragment {


    public fragment_twinspace() {
        // Required empty public constructor
    }

    public WebView mWebView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String link =getArguments().getString("link");
        View v=inflater.inflate(R.layout.fragment_fragment_twinspace, container, false);
        mWebView = (WebView) v.findViewById(R.id.twinspacewb);
        mWebView.loadUrl(link);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.frgmntbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Erasmus+ WebBrowser");
        }
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        return v;
        //return inflater.inflate(R.layout.fragment_fragment_twinspace, container, false);
    }

}
