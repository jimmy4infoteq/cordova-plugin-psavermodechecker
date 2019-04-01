var exec = require( 'cordova/exec' );

exports.deviceLevelCheck = function ( success, error ) {
    exec( success, error, 'psavermodechecker', 'deviceLevelCheck', [] );
};

exports.appLevelCheck = function ( success, error ) {
    exec( success, error, 'psavermodechecker', 'appLevelCheck', [] );
};

exports.openAppLevelSettings = function ( success, error ) {
    exec( success, error, 'psavermodechecker', 'openAppLevelSettings', [] );
};

exports.openDeviceLevelSettings = function ( success, error ) {
    exec( success, error, 'psavermodechecker', 'openDeviceLevelSettings', [] );
};

exports.requestWhitelistApp = function ( success, error ) {
    exec( success, error, 'psavermodechecker', 'requestWhitelistApp', [] );
};