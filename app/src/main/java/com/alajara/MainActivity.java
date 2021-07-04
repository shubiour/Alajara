package com.alajara;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    RelativeLayout relativeLayout;
    Button noInternetButton;
    ImageView alajaraLogoSplash;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alajaraLogoSplash = (ImageView) findViewById(R.id.splash1);
        webView = (WebView) findViewById(R.id.webViewId);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://alajara.com/");




        noInternetButton = (Button) findViewById(R.id.btnRetry);
        relativeLayout= (RelativeLayout) findViewById(R.id.noInternet);

        //checkInternetConnection();

        alajaraLogoSplash.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable(){
            public void run() {
                alajaraLogoSplash.setVisibility(View.GONE);
            }
        }, 2000);




        checkInternetConnection();
        noInternetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetConnection();
            }
        });
    }

    public class mywebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }




//Exit promt
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Exit?")
                    .setCancelable(false)
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();

        }
    }


    public void checkInternetConnection() {

        ConnectivityManager con_manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobiledata = con_manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = con_manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mobiledata.isConnected()){
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            webView.reload();

        }
        else if(wifi.isConnected()){
            webView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            webView.reload();
        }
        else {
            webView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            webView.reload();

        }
    }


    }

