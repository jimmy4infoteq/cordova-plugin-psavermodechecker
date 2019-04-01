# cordova-plugin-psavermodechecker

- This plugin is to check whether PowerSaver Mode is on either at device level or App level

## Get it set up

```
    cordova plugin add https://github.com/jimmy4infoteq/cordova-plugin-psavermodechecker
```

## How to Remove it

```
    cordova plugin remove org.igs.cordova.psavermodechecker
```

## How this can be used in Javascript

1. To get the Device Level PowerSaver mode status
   ```javascript
   cordova.plugins.psavermodechecker.deviceLevelCheck(
     function(flag) {
       if (flag == "true") {
         alert("Device Level - powerSaver-ON");
       } else {
         alert("Device Level - powerSaver-OFF");
       }
     },
     function(err) {
       alert(err);
     }
   );
   ```
2. To get the App Level PowerSaver mode status
   ```javascript
   cordova.plugins.psavermodechecker.appLevelCheck(
     function(flag) {
       if (flag == "true") {
         alert("App level - powerSaver-ON");
       } else {
         alert("App level - powerSaver-OFF");
       }
     },
     function(err) {
       alert(err);
     }
   );
   ```
3. Request the user to whitelist app from powersaving optimizations

   ```javascript
   cordova.plugins.psavermodechecker.requestWhitelistApp(
     function(flag) {
       console.log("WHITE LISTED - " + flag);
     },
     function(err) {
       console.log("UNABLE TO WHITELIST - " + err);
     }
   );
   ```

4. Open Device Level powersaving settings

```javascript
cordova.plugins.psavermodechecker.openDeviceLevelSettings(
  function(flag) {},
  function(err) {}
);
```

5. Open app level powersaving optimizations settings

```javascript
cordova.plugins.psavermodechecker.openAppLevelSettings(
  function(flag) {},
  function(err) {}
);
```
