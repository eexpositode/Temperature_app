package com.task.eexposito.temperature_app.tests;

import android.content.pm.ActivityInfo;
import android.support.v7.internal.view.menu.MenuView;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.task.eexposito.temperature_app.R;
import com.task.eexposito.temperature_app.activities.MainActivity;

/**
 *
 * Created by EExposito on 08/02/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mActivity;
    MenuView.ItemView reloadMenuBttn;
    TextView berlin_header, paris_header, la_header;
    TextView berlin_weather, paris_weather, la_weather;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        reloadMenuBttn = (MenuView.ItemView) mActivity.findViewById(R.id.action_reload);
        berlin_header = (TextView) mActivity.findViewById(R.id.txt_berlin_header);
        paris_header = (TextView) mActivity.findViewById(R.id.txt_paris_header);
        la_header = (TextView) mActivity.findViewById(R.id.txt_los_angeles_header);

        berlin_weather = (TextView) mActivity.findViewById(R.id.txt_berlin_weather);
        paris_weather = (TextView) mActivity.findViewById(R.id.txt_paris_weather);
        la_weather = (TextView) mActivity.findViewById(R.id.txt_los_angeles_weather);
    }

    public void testPreconditions() throws Exception {
        assertTrue(reloadMenuBttn != null);
        assertTrue(berlin_header != null);
        assertTrue(paris_header != null);
        assertTrue(la_header != null);
        assertTrue(berlin_weather != null);
        assertTrue(paris_weather != null);
        assertTrue(la_weather != null);
    }

    public void testScreenRotation() throws Exception {
        String prev_belinTemp = berlin_weather.getText().toString(),
        prev_parisTemp = paris_weather.getText().toString(),
        prev_laTemp = la_weather.getText().toString();

        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mActivity = getActivity();
        String act_berlinTemp = berlin_weather.getText().toString(),
                act_parisTemp = paris_weather.getText().toString(),
                act_laTemp = la_weather.getText().toString();

        assertTrue(prev_belinTemp.equals(act_berlinTemp));
        assertTrue(prev_parisTemp.equals(act_parisTemp));
        assertTrue(prev_laTemp.equals(act_laTemp));
    }
}
