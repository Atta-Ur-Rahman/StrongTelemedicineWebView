package com.techease.strongtelemedicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class WebViewActivity extends AppCompatActivity {

    private String url;
    private TextView tvNoInternetConnection;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        tvNoInternetConnection = findViewById(R.id.tv_back_no_internet_connection);
        progressBar = findViewById(R.id.pb_web_view);

        url = Utilities.getSharedPreferences(this).getString("url", "");

        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.loadUrl(url);

        if (!Utilities.CheckNetwork.isInternetAvailable(WebViewActivity.this)) {

            tvNoInternetConnection.setVisibility(View.VISIBLE);

            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(WebViewActivity.this, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setConfirmText("Refresh")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(new Intent(WebViewActivity.this, WebViewActivity.class));
                            finish();
                        }
                    })
                    .setTitleText("Oops...")
                    .setContentText("No internet connection!")
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            finish();
                            return false;
                        }
                    });

            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();


        }

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setVisibility(View.VISIBLE);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {


                tvNoInternetConnection.setVisibility(View.VISIBLE);

                final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(WebViewActivity.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setConfirmText("Refresh")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(WebViewActivity.this, WebViewActivity.class));
                                finish();
                            }
                        })
                        .setTitleText("This page can’t be reached")
                        .setContentText("Try\n" +
                                "\n" +
                                "Checking the  connection")
                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                finish();
                                return false;
                            }
                        });

                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();
            }
        });


    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (!Utilities.CheckNetwork.isInternetAvailable(WebViewActivity.this)) {
                tvNoInternetConnection.setVisibility(View.VISIBLE);

                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(WebViewActivity.this, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setConfirmText("Refresh")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(WebViewActivity.this, WebViewActivity.class));
                                finish();
                            }
                        })
                        .setTitleText("Oops...")
                        .setContentText("No internet connection!")
                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                finish();
                                return false;
                            }
                        });

                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.show();
            }

            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Toast.makeText(WebViewActivity.this, "error in network connection", Toast.LENGTH_SHORT).show();
            super.onReceivedError(view, request, error);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}