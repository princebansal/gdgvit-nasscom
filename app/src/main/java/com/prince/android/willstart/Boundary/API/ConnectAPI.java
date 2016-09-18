
/*
 * Copyright (c) 2016. Created by Prince Bansal on 16-08-2016.
 */

package com.prince.android.willstart.Boundary.API;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prince.android.willstart.Boundary.Manager.DataHandler;
import com.prince.android.willstart.Entity.Instances.SearchResult;
import com.prince.android.willstart.Entity.Instances.SuggestionResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConnectAPI {

    //Constants
    public static final int FETCH_COMPANIES_CODE = 1;
    public static final int FETCH_SUGGESTIONS_CODE = 2;
    private static final String TAG = ConnectAPI.class.getSimpleName();


    //Declared URLs
    private final String fetchUrl = "http://54.186.169.29:1337/startups/";
    private final String suggestionsUrl="http://54.186.169.29:1337/startups/features/";

    private AppController appController;
    private ServerAuthenticateListener mServerAuthenticateListener;
    private DataHandler dataHandler;
    private long REQUEST_TIMEOUT = 30;

    public ConnectAPI() {
        appController = AppController.getInstance();
        dataHandler = DataHandler.getInstance(appController.getApplicationContext());
    }

    /*public void refresh() {
        if (mServerAuthenticateListener != null) {
            mServerAuthenticateListener.onRequestInitiated(FETCH_COMPANIES_CODE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    fetchUrl.trim(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response != null) {

                        Log.d("refresh", "response");

                        if (validateResponse(response)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Gson gson = new Gson();
                                ArrayList<Message> messageList = gson.fromJson(jsonObject.getJSONArray("messages").toString(), new TypeToken<ArrayList<Message>>() {}.getType());
                                dataHandler.saveConversation(messageList);
                                mServerAuthenticateListener.onRequestCompleted(FETCH_COMPANIES_CODE);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            mServerAuthenticateListener.onRequestError(FETCH_COMPANIES_CODE, ErrorDefinitions.ERROR_RESPONSE_INVALID);
                        }

                    } else {
                        mServerAuthenticateListener.onRequestError(FETCH_COMPANIES_CODE, ErrorDefinitions.ERROR_RESPONSE_NULL);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    mServerAuthenticateListener.onRequestError(FETCH_COMPANIES_CODE, error.getMessage());
                }
            }) {

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.i(TAG, "parseNetworkResponse: " + response.toString());
                    return super.parseNetworkResponse(response);
                }

                @Override
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    Log.i(TAG, "parseNetworkError: " + volleyError.getCause());
                    ;
                    return super.parseNetworkError(volleyError);
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(16000,
                    2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            appController.addToRequestQueue(stringRequest, "refreshrequest");
        } else {
            return;
        }
    }*/


    public void fetchMessages(String keyword) {

        String url=fetchUrl+keyword;
        mServerAuthenticateListener.onRequestInitiated(FETCH_COMPANIES_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Gson gson = new Gson();
                            List<SearchResult> searchResultList = gson.fromJson(jsonObject.get("market").toString(), new TypeToken<List<SearchResult>>() {
                            }.getType());
                            mServerAuthenticateListener.onRequestCompleted(FETCH_COMPANIES_CODE, searchResultList);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v("err", "error");
                mServerAuthenticateListener.onRequestError(FETCH_COMPANIES_CODE, error.getMessage());
            }
        });

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    public void fetchSuggestions(final List<String> servicesList,String category){

        String url=suggestionsUrl+category;
        mServerAuthenticateListener.onRequestInitiated(FETCH_SUGGESTIONS_CODE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                             JSONObject jsonObject=new JSONObject(response);
                            Gson gson = new Gson();
                            //List<SuggestionResult> suggestionResults = gson.fromJson(response, new TypeToken<List<SuggestionResult>>() {}.getType());
                            List<String> suggestionResults = gson.fromJson(jsonObject.get("results").toString(), new TypeToken<List<String>>() {
                            }.getType());
                            mServerAuthenticateListener.onRequestCompleted(FETCH_SUGGESTIONS_CODE, suggestionResults);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.v("err", "error");
                mServerAuthenticateListener.onRequestError(FETCH_SUGGESTIONS_CODE, error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("services",new Gson().toJson(servicesList,new TypeToken<List<String>>(){}.getType()));
                return map;
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }


    private boolean validateResponse(String response) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        try {
            JSONObject j = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public void setServerAuthenticateListener(ServerAuthenticateListener listener) {
        mServerAuthenticateListener = listener;
    }


    public interface ServerAuthenticateListener {
        void onRequestInitiated(int code);

        void onRequestCompleted(int code, Object searchResultList);

        void onRequestError(int code, String message);

    }

   /* public void follow(final Context context, String blogger_id)
    {
        final ProgressDialog dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait ..");
        dialog.show();

        SharedPreferences preferences=context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);


        final String data;

        HashMap<String,Object> registerparams=new HashMap<>();
        registerparams.put("cmd","follow_request_blog");
        registerparams.put("cmd","follow_request_blog");
        registerparams.put("client_os","A");
        registerparams.put("client_os_ver","6.0");
        registerparams.put("client_app_ver","vb.01.01.001");
        registerparams.put("app_secure_key","b2ff398f8db492c19ef89b548b04889c");
        registerparams.put("blogger_id",blogger_id);
        registerparams.put("action","F");
        registerparams.put("user_secure_key",preferences.getString("user_secure_key",null));
        registerparams.put("iv_user_id",preferences.getString("iv_user_id",null));

        data= new Gson().toJson(registerparams);
        Log.v("data",data);



        StringRequest postRequest = new StringRequest(Request.Method.POST,fetchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("res called","res");
                        Log.v("response",response);
                        Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("err","error");
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("data",data);
                return params;
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy(30000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }
    public void unfollow(final Context context, String blogger_id)
    {
        final ProgressDialog dialog=new ProgressDialog(context);
        dialog.setMessage("Please wait ..");
        dialog.show();

        SharedPreferences preferences=context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);


        final String data;

        HashMap<String,Object> registerparams=new HashMap<>();
        registerparams.put("cmd","follow_request_blog");
        registerparams.put("cmd","follow_request_blog");
        registerparams.put("client_os","A");
        registerparams.put("client_os_ver","6.0");
        registerparams.put("client_app_ver","vb.01.01.001");
        registerparams.put("app_secure_key","b2ff398f8db492c19ef89b548b04889c");
        registerparams.put("blogger_id","17198561");
        registerparams.put("action","G");
        registerparams.put("user_secure_key",preferences.getString("user_secure_key",null));
        registerparams.put("iv_user_id",preferences.getString("iv_user_id",null));

        data= new Gson().toJson(registerparams);
        Log.v("data",data);



        StringRequest postRequest = new StringRequest(Request.Method.POST,fetchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("res called","res");
                        Log.v("response",response);
                        Toast.makeText(context,response,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("err","error");
                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();
                params.put("data",data);
                return params;
            }
        };

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy(30000000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        postRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(postRequest);
    }*/


}