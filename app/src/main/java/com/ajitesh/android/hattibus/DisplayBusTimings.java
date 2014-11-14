package com.ajitesh.android.hattibus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Divya on 10/26/2014.
 */
public class DisplayBusTimings extends Activity {
    private Button backBtn;
    private Button homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bus_timings);

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

    }
}
