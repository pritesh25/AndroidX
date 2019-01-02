package com.example.user.androidx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.androidx.library.ItemOffsetDecoration;
import com.example.user.androidx.library.MyObserver;
import com.example.user.androidx.library.UtilityRecyclerViewColumn;
import com.example.user.androidx.speed.ConnectionStateMonitor;
import com.example.user.androidx.speed.ITrafficSpeedListener;
import com.example.user.androidx.speed.TrafficSpeedMeasurer;
import com.example.user.androidx.speed.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ConnectionStateMonitor.NetworkCallBack {

    private  final String TAG = "tag";//MainActivity.class.getSimpleName();
    private RecyclerView recyclerview;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};


    private static final boolean SHOW_SPEED_IN_BITS = false;
    private TrafficSpeedMeasurer mTrafficSpeedMeasurer;
    private TextView tv_network;

    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate Activity");

        List<AppList> installedApps = getInstalledApps();

        //gridViewSetup(installedApps);

        recyclerViewSetup(installedApps);

        networkMonitor();

        //statusBarExample();

        getLifecycle().addObserver(new MyObserver());

    }

    private void statusBarExample() {

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                statusBarHeight,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,   // Allows the view to be on top of the StatusBar
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,    // Keeps the button presses from going to the background window and Draws over status bar
                PixelFormat.TRANSLUCENT);
        parameters.gravity = Gravity.TOP | Gravity.CENTER;

        LinearLayout ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams layoutParameteres = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(layoutParameteres);

        TextView tv = new TextView(this);
        ViewGroup.LayoutParams tvParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(tvParameters);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setText("123");
        ll.addView(tv);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(ll, parameters);

    }

    private void networkMonitor() {

        tv_network = findViewById(R.id.tv_network);
        //check for connection
        new ConnectionStateMonitor(this).enable(getApplicationContext());
        //monitor the speed
        mTrafficSpeedMeasurer = new TrafficSpeedMeasurer(TrafficSpeedMeasurer.TrafficType.ALL);
        mTrafficSpeedMeasurer.startMeasuring();

    }

    private void gridViewSetup(List<AppList> installedApps) {

//        CustomListAdapter adapter = new CustomListAdapter(this, installedApps);
//        GridView gridView = (GridView) findViewById(R.id.gridView);
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // set an Intent to Another Activity
//                //Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                //intent.putExtra("image", logos[position]); // put image data in Intent
//                //startActivity(intent); // start Intent
//            }
//        });

    }

    private void recyclerViewSetup(List<AppList> installedApps) {

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(),R.dimen.ten));
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(),UtilityRecyclerViewColumn.calculateNoOfColumns(getApplicationContext())));


        recyclerViewAdapter = new RecyclerViewAdapter(this,installedApps);
        recyclerview.setAdapter(recyclerViewAdapter);
    }

    private List<AppList> getInstalledApps() {
        List<AppList> res = new ArrayList<>();
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> packs = getPackageManager().queryIntentActivities(mainIntent, 0);

        for (int i = 0; i < packs.size(); i++) {
            ResolveInfo p = packs.get(i);

            String appName      = p.activityInfo.loadLabel(getPackageManager()).toString();
            String appPkgName   = p.activityInfo.packageName;
            Drawable icon       = p.activityInfo.loadIcon(getPackageManager());
            res.add(new AppList(appName,appPkgName,icon));

//            if ((isSystemPackage(p) == true)) {
//                String appName = p.activityInfo.loadLabel(getPackageManager()).toString();
//                Drawable icon = p.activityInfo.loadIcon(getPackageManager());
//                res.add(new AppList(appName, icon));
//            }
        }
        return res;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    @Override
    public void onConnected() {
        //Log.d(TAG, "onConnected");
        Toast.makeText(getApplicationContext(), "onConnected", Toast.LENGTH_LONG).show();
        checkNetworkConnection();
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "onDisconnected");
        Toast.makeText(getApplicationContext(), "onDisconnected", Toast.LENGTH_LONG).show();
    }

    private ITrafficSpeedListener mStreamSpeedListener = new ITrafficSpeedListener() {

        @Override
        public void onTrafficSpeedMeasured(final double upStream, final double downStream) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String upStreamSpeed = Utils.parseSpeed(upStream, SHOW_SPEED_IN_BITS);
                    String downStreamSpeed = Utils.parseSpeed(downStream, SHOW_SPEED_IN_BITS);
                    tv_network.setText("Up Stream Speed: " + upStreamSpeed + "\n" + "Down Stream Speed: " + downStreamSpeed);
                }
            });
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart Activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume Activity");
        mTrafficSpeedMeasurer.registerListener(mStreamSpeedListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause Activity");
        mTrafficSpeedMeasurer.removeListener(mStreamSpeedListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy Activity");
        mTrafficSpeedMeasurer.stopMeasuring();
    }

    private void checkNetworkConnection() {
        // BEGIN_INCLUDE(connect)
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                //Log.d(TAG, getResources().getString(R.string.wifi_connection));
            } else if (mobileConnected) {
                //Log.d(TAG, getResources().getString(R.string.mobile_connection));
            }
        } else {
            Log.i(TAG, getString(R.string.no_wifi_or_mobile));
        }
        // END_INCLUDE(connect)
    }

    private void appUninstall(String packageName)
    {
        try
        {
            Intent intentUninstalled = new Intent(Intent.ACTION_DELETE);
            intentUninstalled.setData(Uri.parse("package:"+packageName));
            startActivity(intentUninstalled);
        }
        catch (Exception e)
        {
            Log.d(TAG,"(onAppLongClick) app uninstall , catch error = "+ e.getMessage());
        }
    }

    private void appInfo(String packageName)
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",packageName, null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void appLaunch(String packageName)
    {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(launchIntent);
    }

    private void appAddToDock()
    {

    }
}