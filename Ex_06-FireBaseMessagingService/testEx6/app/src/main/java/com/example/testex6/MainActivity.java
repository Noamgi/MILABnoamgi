package com.example.testex6;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity  {
    private final static String TAG = "MainActivity";
    static String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToken();
    }

    public void fetchStockPrice (final View view) {
        Log.d(TAG, "Fetch button clicked");
        final StockPriceFetcher fetcher = new StockPriceFetcher(view.getContext());
        final String stock = ((EditText)findViewById(R.id.edit_stock)).getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching " + stock + " stock price ...");
        progressDialog.show();

        fetcher.dispatchRequest(stock, MainActivity.token, new StockPriceFetcher.StockPriceResponseListener() {

            @Override
            public void onResponse(StockPriceFetcher.PriceResponse response) {
                progressDialog.hide();

                if (response.isError) {
                    Toast.makeText(view.getContext(), "Error while fetching weather", Toast.LENGTH_LONG);
                    return;
                }
//
            }
        });
    }


                public void getToken() {
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    // Get new Instance ID token
                                    MainActivity.token  = task.getResult().getToken();

                                    // Log and toast
//                                    String msg = getString(R.string.msg_token_fmt, MainActivity.token);
//                                    Log.d(TAG, msg);
//                                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "the token is:" + MainActivity.token);
                                }
                            });

                    }


    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.testex6.onMessageReceived");
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String symbol = extras.getString("symbol");
            Log.d(TAG, "symbol got from firebase: " + symbol);
            String price = extras.getString("price");
            Log.d(TAG, "price got from FireBase: " + price);
            updateView(symbol, price);
        }

        /**
         * This method updates the fields in the app according to the data retrieved from the
         * server
         * @param symbol The share name
         * @param price The price
         */
        private void updateView(String symbol, String price) {
            ((TextView)MainActivity.this.findViewById(R.id.text_symbol)).setText(symbol);
            ((TextView)MainActivity.this.findViewById(R.id.text_price)).setText("$" + price);
        }
    }
}