package zsp.erasmus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class RSSdetail extends AppCompatActivity {
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssdetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = getIntent().getStringArrayExtra("data");
        if(data[3]==null){
            data[3]="http://freedesignfile.com/upload/2015/07/Embossment-triangular-blue-background-vector-04.jpg";
        }
        setTitle(data[0]);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        WebView wb = (WebView) findViewById(R.id.wbf);
        wb.loadUrl(data[3]);
        WebSettings webSettings = wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.setClickable(false);
        //wb.setBackground();
        TextView desc = (TextView) findViewById(R.id.rsstxt);
        desc.setText(Html.fromHtml(data[1]+"<br><br>"+data[2]));
        wb.setBackgroundColor(Color.BLUE);
        wb.getSettings().setUserAgentString("Desktop");
        // Force links and redirects to open in the WebView instead of in a browser
        wb.setWebViewClient(new WebViewClient());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RSSdetail.this, BrowserWWW.class);
                intent.putExtra("link", data[4]);
                startActivity(intent);
            }
        });
    }
}
