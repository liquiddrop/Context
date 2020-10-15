package com.curtis.context;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.curtis.context.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class AdUtil
{
    private static final String TAG = "AdUtil";

    public static void initAds(Context context) {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    public static void setUpAdView(Activity activityToSetup) {
        AdView mAdView;
        mAdView = activityToSetup.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
