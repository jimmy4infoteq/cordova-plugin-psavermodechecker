package org.igs.cordova.psavermodechecker;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * CordovaPlugin Class
 */
public class psavermodechecker extends CordovaPlugin {
    private static final String PKGTAG = "psavermodechecker";
    static final int IGS_IGNORE_OPTIMIZATION_REQUEST = 11;
    private Context context;
    private CallbackContext requestWaveOptimizeCallback = null;

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
            this.appLevelCheck(callbackContext);
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
            this.requestIgnoringPowerSaving(callbackContext);
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
     * @param callbackContext
     * @return boolean to the callbackContext
     */
    public void appLevelCheck(CallbackContext callbackContext) {
        boolean ispowersaveron = false;
        Activity activity = cordova.getActivity();
        String pName = activity.getPackageName();
        Log.i(PKGTAG, pName);
        try {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (powerManager.isIgnoringBatteryOptimizations(pName)) {
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

    public void requestIgnoringPowerSaving(CallbackContext callbackContext) {
        requestWaveOptimizeCallback = callbackContext;
        Activity activity = cordova.getActivity();
        Intent intent = new Intent();
        String pkgName = activity.getPackageName();
        Log.i(PKGTAG, "requestIgnoringPowerSaving - " + pkgName);
        PowerManager pm = (PowerManager) cordova.getActivity().getSystemService(Context.POWER_SERVICE);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (pm.isIgnoringBatteryOptimizations(pkgName))
            return;

        intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + pkgName));
        cordova.setActivityResultCallback(this);
        cordova.getActivity().startActivityForResult(intent, IGS_IGNORE_OPTIMIZATION_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IGS_IGNORE_OPTIMIZATION_REQUEST) {
            PowerManager pm = (PowerManager) cordova.getActivity().getSystemService(Context.POWER_SERVICE);
            Activity activity = cordova.getActivity();
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
            boolean isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(activity.getPackageName());
            if (isIgnoringBatteryOptimizations) {
                if (requestWaveOptimizeCallback != null) {
                    requestWaveOptimizeCallback.success("true");
                }
            } else {
                if (requestWaveOptimizeCallback != null) {
                    requestWaveOptimizeCallback.error("false");
                }
            }
        }
    }
}