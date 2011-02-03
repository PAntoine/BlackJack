package com.peter;

import android.app.Activity;
import android.os.Bundle;
import com.peter.ProgramGridView;

public class ProgramGrid extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new ProgramGridView(this));
    }
}
