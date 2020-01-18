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
    private static final String TAG = "StockPriceFetcher";
    private final static String REQUEST_URL = "http://10.0.2.2:8080/stockprice";
    private RequestQueue _queue;

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


    public StockPriceFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    public interface StockPriceResponseListener {
        public void onResponse(PriceResponse response);
    }

    private PriceResponse createErrorResponse() {
        return new PriceResponse(true, null, null);
    }

    /**
     * This method dispatches a request to our server
     * @param shareName the requested share
     * @param token the token of the device
     * @param listener a listener
     */
    public void dispatchRequest(final String shareName, final String token, final StockPriceResponseListener listener) {
        JSONObject getBody = new JSONObject();
        try {
            getBody.put("shareName", shareName);
            getBody.put("token", token);
        }

        catch (JSONException e) {
            listener.onResponse(createErrorResponse());
            return;
        }

        Log.d(TAG, "The JSON object is " + getBody.toString());

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, REQUEST_URL, getBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PriceResponse res = new PriceResponse(false, shareName, response.getJSONObject("Global Quote").getString("05. price"));
                            listener.onResponse(res);
                            Log.d(TAG, "Response is " + res);
                        }  catch (Exception e) { //(JSONException e) {
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