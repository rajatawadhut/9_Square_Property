package com.nine_square.property.Avtivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nine_square.property.R;
import com.nine_square.property.Utils.Endpoints;
import com.nine_square.property.Utils.SmsData;
import com.nine_square.property.Utils.StorageSome;
import com.nine_square.property.Utils.VolleySingleton;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Buyourservices extends AppCompatActivity implements PaymentResultListener {


    Button payment;
    ImageView back;
    private ProgressDialog progressDialog;


    Button btn1500,btn2400,btn3000;
    String getsubscription= "";
    int valueInt;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);


        btn1500 = findViewById(R.id.btn_1500);
        btn2400 = findViewById(R.id.btn_2400);
        btn3000 = findViewById(R.id.btn_3000);



        btn1500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startpayment("1500");
                valueInt = 100;
            }
        });

        btn2400.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startpayment("2400");
                valueInt = 50;


            }
        });

        btn3000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpayment("3000");
                valueInt = 20;

            }
        });


    }

    public void startpayment(String value){


        Checkout checkout = new Checkout();
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("name", "9 Square Property"); // amount in the smallest currency unit
            orderRequest.put("description", "App Payment"); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
//            String amountValue = amount.getText().toString().trim();

            double total = Double.parseDouble(value);
            total = total * 100;
            orderRequest.put("theme.color","#008B8B");
            orderRequest.put("amount", total);
            orderRequest.put("image", "https://im2.ezgif.com/tmp/ezgif-2-36c3ad5df0.png");
            orderRequest.put("prefill.email", VolleySingleton.getInstance(getApplicationContext()).email());
            orderRequest.put("prefill.contact",VolleySingleton.getInstance(getApplicationContext()).mobile());

            checkout.open(this, orderRequest);




        }
        catch (Exception e){

        }
    }


    private void getsubscription() {
        final String userid = VolleySingleton.getInstance(getApplicationContext()).id();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.getsubscription, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("response :: " +response);
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getBoolean("error")){
                        showmessage(obj.getString("message"));
                    }else{
                        String result = obj.getString("subscriptionplan");
//                        propertylistno.setText(result);
                        StorageSome.getInstance(getApplicationContext()).setsubsription(result);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Buyourservices.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SmsData smsData = new SmsData();
                Map<String, String> params = new HashMap<>();
                params.put("header", smsData.token);
                params.put("userid", userid);
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void showmessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void sendsubscription() {

        int getsub = Integer.parseInt(StorageSome.getInstance(getApplicationContext()).getsubsription());
        int a = getsub + valueInt;

        getsubscription = String.valueOf(a);



        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.sendsubscription, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("response ::::: "+ response);
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getBoolean("error")){
                        showmessage(obj.getString("message"));
                    }else{
//                        getsubscription();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buyourservices.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", String.valueOf(error));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SmsData smsData = new SmsData();
                Map<String, String> params = new HashMap<>();
                params.put("header", smsData.token);
                params.put("userid", VolleySingleton.getInstance(getApplicationContext()).id());
                params.put("subscription", getsubscription);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(Buyourservices.this).addToRequestQueue(stringRequest);


    }




    @Override
    public void onPaymentSuccess(String s) {
//        sendsubscription();
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "Payment Failed!!", Toast.LENGTH_SHORT).show();

    }
}

