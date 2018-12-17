package com.lxldev.relax.news163.tabs.topnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.lxldev.relax.R;
import com.lxldev.relax.network.IOService;
import com.lxldev.relax.news163.tabs.topnews.io.TopNewsIOService;

public class TopNewDetailsDialog extends AppCompatActivity {

    private WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news163_activity_topnews_details);

        webView = this.findViewById(R.id.news163_topnews_webview);

        webView.getSettings().setBlockNetworkImage(true);

        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);



        String url = getIntent().getStringExtra("url");


        new TopNewsIOService().getNewsHtmlContent(url, new IOService.Callback<String>() {
            @Override
            public void onSuccess(String s) {
                webView.loadData(s,"text/html","utf-8");
            }

            @Override
            public void onException(Exception e) {

                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                TopNewDetailsDialog.this.finish();
            }
        });



    }
}
