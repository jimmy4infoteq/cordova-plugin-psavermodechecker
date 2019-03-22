package org.igs.cordova.psavermodechecker;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.PowerManager;
import android.os.Build;

/**
 * The endpoint communicates with javascript
 */
public class psavermodechecker extends CordovaPlugin {
    private static final String PKGTAG = "psavermodechecker";
    private Context context;
    private CallbackContext changedEventCallback = null;

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

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("deviceLevelCheck")) {
            this.deviceLevelCheck(callbackContext);
            return true;
        } else if (action.equals("appLevelCheck")) {
            String pkgName = args.getString(0);
            this.appLevelCheck(pkgName, callbackContext);
            return true;
        }
        return false;
    }

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
}
