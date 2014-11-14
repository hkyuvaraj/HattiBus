package com.ajitesh.android.hattibus;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;
import android.view.View;

/**
 * Created by Divya on 11/1/2014.
 */
public class MyViewHelper extends Activity{

    private final Context myContext;

    MyViewHelper (Context context) {
       this.myContext = context;
    }

    public void showProgressBar() {
        ProgressBar progressBar = (ProgressBar)this.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

    }
}
