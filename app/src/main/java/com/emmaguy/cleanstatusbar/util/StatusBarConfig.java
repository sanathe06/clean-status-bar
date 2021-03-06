package com.emmaguy.cleanstatusbar.util;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.emmaguy.cleanstatusbar.MainActivity;
import com.emmaguy.cleanstatusbar.R;

public class StatusBarConfig {
    private static final String RESOURCE_NAME_STATUS_BAR_HEIGHT = "status_bar_height";

    private final int mApiLevel;
    private final boolean mIsKitKatGradientEnabled;

    private final Resources mResources;
    private final AssetManager mAssetManager;

    public StatusBarConfig(int apiLevel, boolean isKitKatGradientEnabled, Resources r, AssetManager a) {
        mApiLevel = apiLevel;
        mIsKitKatGradientEnabled = isKitKatGradientEnabled;
        mResources = r;
        mAssetManager = a;
    }

    public int getStatusBarHeight() {
        return getInternalDimensionSize(mResources, RESOURCE_NAME_STATUS_BAR_HEIGHT);
    }

    public boolean shouldDrawGradient() {
        return mIsKitKatGradientEnabled && mApiLevel == Build.VERSION_CODES.KITKAT;
    }

    public int getForegroundColour() {
        int colourResId = R.color.android_jellybean_status_bar;

        switch (mApiLevel) {
            case Build.VERSION_CODES.KITKAT:
                colourResId = R.color.android_kitkat_status_bar;
                break;
            case MainActivity.VERSION_CODE_L:
                colourResId = R.color.android_l_status_bar;
                break;
        }

        return mResources.getColor(colourResId);
    }

    public Typeface getFont() {
        if (mApiLevel == MainActivity.VERSION_CODE_L) {
            return Typeface.createFromAsset(mAssetManager, "fonts/Roboto-Medium.ttf");
        }

        return null;
    }

    private int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public float getFontSize() {
        return 16.0f;
    }

    public Drawable get3gDrawable() {
        return getTintedDrawable(mResources, R.drawable.stat_sys_signal_4_fully, getForegroundColour());
    }

    public Drawable getWifiDrawable() {
        return getTintedDrawable(mResources, R.drawable.stat_sys_wifi_signal_4_fully, getForegroundColour());
    }

    public Drawable getTintedDrawable(Resources res, int drawableResId, int colour) {
        Drawable drawable = res.getDrawable(drawableResId);
        drawable.setColorFilter(colour, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
}
