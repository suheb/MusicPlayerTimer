package com.kabouzeid.gramophone.timer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.kabouzeid.appthemehelper.ThemeStore;
import com.kabouzeid.gramophone.R;
import com.kabouzeid.gramophone.timer.utils.Utils;
import com.kabouzeid.gramophone.ui.activities.MainActivity;
import com.kabouzeid.gramophone.ui.activities.base.AbsBaseActivity;

public class TimerActivity extends AbsBaseActivity {

    private Fragment mFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ThemeStore.primaryColor(this));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }


    private void showFragment(Bundle savedInstanceState) {
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            if (com.kabouzeid.gramophone.timer.TimerService.SERVICE_FLAG)
                mFragment = new com.kabouzeid.gramophone.timer.TimerFragment();
            else
                mFragment = new StartFragment();

            Utils.changeFragment(mFragment, this);
        }
    }
}