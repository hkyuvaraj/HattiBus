package com.ajitesh.android.hattibus;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import android.util.Log;

import com.ajitesh.android.hattibus.views.ProgressDialogPopup;


public class SearchBusRoutes extends Activity {
    private static  final int MESSAGE_REQUEST =1;
    private Button searchButton;
    private AutoCompleteTextView origin;
    private AutoCompleteTextView destination;

    ArrayAdapter<String> adapter;

    ArrayList<String> hattilist =  new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus_routes);

        this.origin = (AutoCompleteTextView)this.findViewById(R.id.autocomplete_origin_busstop);
        this.destination = (AutoCompleteTextView)this.findViewById(R.id.autocomplete_destination_busstop);
        try {
         hattilist = new MySQLiteHelper(getApplicationContext()).getAllBusStops();
         } catch (Exception e) {
          Log.e("Exeption caught while creating object",  e.getMessage());
        }
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,hattilist);
        origin.setAdapter(adapter);
        destination.setAdapter(adapter);

        this.searchButton = (Button) this.findViewById(R.id.searchbusesBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialogPopup.progressDialogSearch = ProgressDialog.show(SearchBusRoutes.this,null,"Searching please wait...");

                String user_selected_origin_value = origin.getText().toString();
                String user_selected_destination_value = destination.getText().toString();

                Intent intent = new Intent(SearchBusRoutes.this,DisplaySearchResults.class);
                //Intent intent = new Intent("display_search_result_filter");
                Bundle bundle = new Bundle();
                bundle.putString("user_selected_origin_key",user_selected_origin_value);
                bundle.putString("user_selected_destination_key",user_selected_destination_value);
                intent.putExtras(bundle);
                //progressDialog.dismiss();
                startActivity(intent);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_bus_routes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Under Construction", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_about) {
            Toast.makeText(this, "This app is being developed by Mr.Yuvaraj", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
