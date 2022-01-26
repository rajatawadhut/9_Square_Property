package com.nine_square.property.Avtivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nine_square.property.R;

import java.util.UUID;

public class Buyourservices extends AppCompatActivity {
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_our_services);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Buy Services");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        String uniqueString =  UUID.randomUUID().toString();



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        Button btn1500 = findViewById(R.id.btn_1500);
        btn1500.setOnClickListener(view -> {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("Amount", "1500");
            startActivity(intent);
        });

//
//        WebView wv = (WebView) findViewById(R.id.webview);
//        WebSettings webSettings = wv.getSettings();
//        wv.getSettings().setJavaScriptEnabled(true);
//        progressDialog.show();
//        wv.setWebViewClient(new MyWebViewClient());
//        webSettings.setBuiltInZoomControls(true);
//        wv.loadUrl("https://www.99acres.com/do/buyourservices/");
////        wv.loadUrl("https://www.amazon.com");
//
//    }
//
//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//
//            if (!progressDialog.isShowing()) {
//                progressDialog.show();
//            }
//
//            return true;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            System.out.println("on finish");
//            if (progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//
//        }
    }


}
