package com.ajitesh.android.hattibus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.view.Gravity;

import android.util.Log;
import android.widget.Toast;

import com.ajitesh.android.hattibus.views.ProgressDialogPopup;

/**
 * Created by Divya on 10/26/2014.
 */
public class DisplaySearchResults  extends Activity {

    private TextView searchResult;
    private Button backBtn;
    private TextView searchResultTableData;

    String origin_selected;
    String destination_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("tag","before progress bar...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);
        //progressDialog = ProgressDialog.show(DisplaySearchResults.this,null,"Searching Please wait...");

        Bundle bundle = getIntent().getExtras();
         origin_selected = bundle.getString("user_selected_origin_key");
         destination_selected = bundle.getString("user_selected_destination_key");

        /*
        this.searchResultTableData = (TextView) this.findViewById(R.id.search_result_table_data);
        searchResultTableData.setTextColor(Color.RED);
        searchResultTableData.setText("Sorry No Bus route available from " + origin_selected + " --> " + destination_selected);
        */

        /*
        this.searchResult = (TextView) this.findViewById(R.id.search_result_header_no);
        searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplaySearchResults.this, DisplayBusTimings.class);
                startActivity(intent);
            }
        });
        */

        this.backBtn = (Button)this.findViewById(R.id.results_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplaySearchResults.this,SearchBusRoutes.class);
                startActivity(intent);
            }
        });

        try {
            Log.i("Oncreate:","About to call buildBusroutesRestulsTable");
            buildBusroutesRestulsTable();
        } catch(Exception e) {

        }
        Log.i("tag","closing progress bar...");
        ProgressDialogPopup.progressDialogSearch.dismiss();
    }


    public void buildBusroutesRestulsTable() throws Exception{
        TableLayout table_layout = (TableLayout)findViewById(R.id.search_results_table_data);
        table_layout.setShrinkAllColumns(true);

        Cursor c = new MySQLiteHelper(this.getApplicationContext()).getDirectRoutes(origin_selected,destination_selected);
        int rows = c.getCount();
        int cols = c.getColumnCount();
        Log.i("buildBusroutesRestulsTable:", "Count= " + rows);
        c.moveToFirst();

        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

          // inner for loop
            for (int j = 1; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.LEFT);
                tv.setTextSize(15);
                //tv.setPadding(0, 5, 0, 5);
                //tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                //tv.setPadding(5, 0,5, 0;
                tv.setText(c.getString(j));

                row.addView(tv);
             }

             final Button btn = new Button(this);
             btn.setText("Show");
             btn.setTextSize(10);
             btn.setTextColor(Color.WHITE);
             btn.setHint(c.getString(0));
             btn.setBackground(getResources().getDrawable(R.drawable.show_timings_btn));
             btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
             //btn.setBackgroundResource(R.drawable.search_buses_btn_shape);
             row.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Show Bus Timing for " + btn.getHint(), Toast.LENGTH_LONG).show();
                }
            });


            c.moveToNext();
            table_layout.addView(row);
        }
    }
}
