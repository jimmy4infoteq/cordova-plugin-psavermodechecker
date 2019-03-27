package org.igs.cordova.psavermodechecker;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Build;
import android.provider.Settings;

/**
 * CordovaPlugin Class
 */
public class psavermodechecker extends CordovaPlugin {
    private static final String PKGTAG = "psavermodechecker";
    private Context context;

    /**
     * Plugin Initializer reloaded
     * 
     * @param cordova
     * @param webView
     */
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        context = super.cordova.getActivity().getApplicationContext();
    }

    /**
     * The endpoint that communicates with javascript
     * 
     * @param action
     * @param callbackContext
     * 
     * @return boolean
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("deviceLevelCheck")) {
            this.deviceLevelCheck(callbackContext);
            return true;
        } else if (action.equals("appLevelCheck")) {
            String pkgName = args.getString(0);
            this.appLevelCheck(pkgName, callbackContext);
            return true;
        } else if (action.equals("openAppLevelSettings")) {
            // String pkgName = args.getString(0);
            this.openAppLevelBatterySaverSettings(callbackContext);
            return true;
        } else if (action.equals("openDeviceLevelSettings")) {
            // String pkgName = args.getString(0);
            this.openDeviceLevelBatterySaverSettings(callbackContext);
            return true;
        } else if (action.equals("requestWhitelistApp")) {
            String pkgName = args.getString(0);
            this.requestIgnoringPowerSaving(pkgName, callbackContext);
            return true;
        }
        return false;
    }

    /**
     * Device level power/battery saver status probing
     * 
     * @param callbackContext
     * 
     * @return boolean to the callbackContext
     */
    public void deviceLevelCheck(CallbackContext callbackContext) {
        boolean ispowersaveron = false;
        try {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (powerManager.isPowerSaveMode()) {
                    ispowersaveron = true;
                }
            }
            callbackContext.success(String.valueOf(ispowersaveron));
        } catch (Exception e) {
            LOG.d(PKGTAG, "Error :- " + e);
            callbackContext.error("Failed to Fetch DeviceLevel Settings");
        }
    }

    /**
     * App level power/battery saver status probing
     * 
     * @param packageName
     * @param callbackContext
     * @return boolean to the callbackContext
     */
    public void appLevelCheck(String pkgName, CallbackContext callbackContext) {
        boolean ispowersaveron = false;
        try {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (powerManager.isIgnoringBatteryOptimizations(pkgName)) {
                    ispowersaveron = false;
                } else {
                    ispowersaveron = true;
                }
            }
            callbackContext.success(String.valueOf(ispowersaveron));
        } catch (Exception e) {
            LOG.d(PKGTAG, "Error :- " + e);
            callbackContext.error("Failed to Fetch AppLevel Settings");
        }
    }

    public void openDeviceLevelBatterySaverSettings(CallbackContext callbackContext) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cordova.getActivity()
                    .startActivityForResult(new Intent(android.provider.Settings.ACTION_BATTERY_SAVER_SETTINGS), 0);
        }
    }

    public void openAppLevelBatterySaverSettings(CallbackContext callbackContext) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cordova.getActivity().startActivityForResult(
                    new Intent(android.provider.Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS), 0);
        }
    }

    public void requestIgnoringPowerSaving(String packageName, CallbackContext callbackContext) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cordova.getActivity()
                    .startActivityForResult(new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                            .setData(Uri.parse("package:" + packageName)), 0);
        }
    }
}