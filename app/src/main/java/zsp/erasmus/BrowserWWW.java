package zsp.erasmus;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserWWW extends AppCompatActivity {
    public WebView mWebView;
    private ValueCallback<Uri[]> afterLolipop;
    private ValueCallback<Uri> mUploadMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_www);
        String link =getIntent().getStringExtra("link");
        mWebView = (WebView) findViewById(R.id.webvv);
        mWebView.loadUrl(link);
        setTitle("Erasmus+ WebBrowser");
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {

                    Uri result = intent == null || resultCode != RESULT_OK ? null
                            : intent.getData();
                    if (mUploadMessage != null) {
                        mUploadMessage.onReceiveValue(result);
                    } else if (afterLolipop != null) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            afterLolipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                            afterLolipop = null;
                        }
                    }
                    mUploadMessage = null;
                }
        }

    }
}
