package com.ajitesh.android.hattibus;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.app.Activity;
import android.content.Context;

/**
 * Created by Divya on 11/29/2014.
 */
public class UpdateSQLiteFromWebservice extends  AsyncTask<String, String, String> {
    InputStream is = null;


    String line = null;

    private Context context;
    ProgressDialog dialog;

    public UpdateSQLiteFromWebservice(Context cxt) {
        context = cxt;
        //dialog = new ProgressDialog(context);
    }
/*
    @Override
    protected void onPreExecute() {
        dialog.setTitle("Please wait");
        dialog.show();
    }
*/


    @Override
    protected String doInBackground(String... params) {
        String updateInfoURL = "http://hattibuswebservice-hkyuvaraj.rhcloud.com/service/updateinfo/";
        String busstopsInfoURL = "http://hattibuswebservice-hkyuvaraj.rhcloud.com/service/busstops/";
        String busrutesInfoURL = "http://hattibuswebservice-hkyuvaraj.rhcloud.com/service/busroutes/";
        String bustimingsInfoURL = "http://hattibuswebservice-hkyuvaraj.rhcloud.com/service/bustimings/";

        String updateInfo = getHTTPResponseFromURL(updateInfoURL);
        String busstopsInfo = getHTTPResponseFromURL(busstopsInfoURL);
        String busrutesInfo = getHTTPResponseFromURL(busrutesInfoURL);
        String bustimingsInfo = getHTTPResponseFromURL(bustimingsInfoURL);

        try {
            MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(context);
            mySQLiteHelper.updateUpdateInfoTable(updateInfo);
            mySQLiteHelper.updateBusStopsTable(busstopsInfo);
        }
        catch (Exception e){

        }
            return null;
    }

public String getHTTPResponseFromURL(String url) {
    String responseFromURL = null;
    try {
        Log.i("getHTTPResponseFromURL 1", "Trying http connection:"+ url);
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
    }
    catch (Exception e) {
        Log.e("getHTTPResponseFromURL 2", e.toString());
    }
    try {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();

        while((line = reader.readLine()) != null) {

            sb.append(line + "\n");
        }

        is.close();
        responseFromURL = sb.toString();
    }
    catch (Exception e) {
        Log.e("getHTTPResponseFromURL 3", e.toString());
    }
    Log.i("getHTTPResponseFromURL 4", "Response is  " + responseFromURL);
    return responseFromURL;
}
}

