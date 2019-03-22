# cordova-plugin-psavermodechecker

- This plugin is to check whether PowerSaver Mode is on either at device level or App level

## Get it set up

    `cordova plugin add https://github.com/jimmy4infoteq/cordova-plugin-psavermodechecker`

## How to Remove it

    `cordova plugin remove cordova-plugin-psavermodechecker`

## How this can be used in Javascript

1. To get the Device Level PowerSave mode status
   cordova.plugins.psavermodechecker.deviceLevelCheck(function(flag){ if(flag=='true'){ alert("powerSaver-ON"); } else { alert("powerSaver-OFF"); } },function(err){ alert(err); });
2. To get the App Level PowerSave mode status
   `cordova.plugins.psavermodechecker.appLevelCheck('packageName.reverse.domain', function(flag){ if(flag=='true'){ alert("powerSaver-ON"); } else { alert("powerSaver-OFF"); } },function(err){ alert(err); });`
