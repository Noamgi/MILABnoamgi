package com.example.testex6;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Response.*;

public class StockPriceFetcher {
    private RequestQueue _queue;
    private static final String TAG = "StockPriceFetcher";
    private final static String REQUEST_URL = "http://10.0.2.2:8080/stockprice";


    public class PriceResponse {
        public boolean isError;
        public String symbol;
        public String price;

        public PriceResponse(boolean isError, String symbol, String price) {
            this.isError = isError;
            this.symbol = symbol;
            this.price = price;
        }
    }

    public interface StockPriceResponseListener {
        public void onResponse(PriceResponse response);
    }

    public StockPriceFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }


    private PriceResponse createErrorResponse() {
        return new PriceResponse(true, null, null);
    }


    public void dispatchRequest(final String stockName, final String token, final StockPriceResponseListener listener) {
        JSONObject getBody = new JSONObject();
        try {
            getBody.put("stockName", stockName);
            getBody.put("token", token);
        }

        catch (JSONException e) {
            listener.onResponse(createErrorResponse());
            return;
        }


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL, getBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PriceResponse res = new PriceResponse(false, stockName, response.getJSONObject("Global Quote").getString("05. price"));
                            listener.onResponse(res);
                        }
                        catch (Exception e) { //(JSONException e) {
                            listener.onResponse(createErrorResponse());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(createErrorResponse());
            }
        });

        _queue.add(req);
    }
}