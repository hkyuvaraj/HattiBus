package com.ajitesh.android.hattibus;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Divya on 10/26/2014.
 */
public class DisplayBusTimings extends Activity {
    private Button backBtn;
    private Button homeBtn;
    String route_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bus_timings);

        Bundle bundle = getIntent().getExtras();
        route_id = bundle.getString("user_selected_route_id");

      this.backBtn = (Button)this.findViewById(R.id.timings_back_btn);
      this.homeBtn = (Button)this.findViewById(R.id.timings_home_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayBusTimings.this,DisplaySearchResults.class);
                startActivity(intent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayBusTimings.this,SearchBusRoutes.class);
                startActivity(intent);
            }
        });

        try {
            Log.i("Oncreate:","About to call buildBusTimingsRestulsTable");
            buildBusTimingsRestulsTable();

        } catch(Exception e) {

        }

    }


    public void buildBusTimingsRestulsTable() throws Exception{
        TableLayout table_layout = (TableLayout)findViewById(R.id.bus_timings_table_data);
        table_layout.setShrinkAllColumns(true);

        Cursor c = new MySQLiteHelper(this.getApplicationContext()).getBusTimings(route_id);
        int rows = c.getCount();
        int cols = c.getColumnCount();
        Log.i("buildBusTimingsRestulsTable:", "Count= " + rows);
        c.moveToFirst();

        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j < cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.LEFT);
                tv.setTextSize(15);
                //tv.setPadding(0, 5, 0, 5);
                //tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                //tv.setPadding(5, 0,5, 0;
                tv.setText(c.getString(j));

                row.addView(tv);
            }

            c.moveToNext();
            table_layout.addView(row);
        }
    }

}
