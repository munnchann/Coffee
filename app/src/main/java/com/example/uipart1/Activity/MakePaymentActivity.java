package com.example.uipart1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.uipart1.R;

public class MakePaymentActivity extends AppCompatActivity {
    private ProgressBar progressBar2;
    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        mWebview = (WebView) findViewById(R.id.mWebview);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.setWebViewClient(new WebViewClient()
                                  {
                                      @Override
                                      public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                          super.onPageStarted(view, url, favicon);
                                          mWebview.setVisibility(View.GONE);
                                          progressBar2.setVisibility(View.VISIBLE);
                                      }

                                      @Override
                                      public void onPageFinished(WebView view, String url) {
                                          super.onPageFinished(view, url);
                                          mWebview.setVisibility(View.VISIBLE);
                                          progressBar2.setVisibility(View.GONE);
                                      }
                                  }
        );
        mWebview.loadUrl("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=PX4KZ983J976G");
    }
}