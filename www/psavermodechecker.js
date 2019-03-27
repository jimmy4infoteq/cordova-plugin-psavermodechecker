var exec = require( 'cordova/exec' );

exports.deviceLevelCheck = function ( success, error ) {
    exec( success, error, 'psavermodechecker', 'deviceLevelCheck', [] );
};

exports.appLevelCheck = function ( pkgName, success, error ) {
    exec( success, error, 'psavermodechecker', 'appLevelCheck', [ pkgName ] );
};

exports.openAppLevelSettings = function ( pkgName, success, error ) {
    exec( function () {}, function () {}, 'psavermodechecker', 'openAppLevelSettings', [] );
};

exports.openDeviceLevelSettings = function ( pkgName, success, error ) {
    exec( function () {}, function () {}, 'psavermodechecker', 'openDeviceLevelSettings', [] );
};

exports.requestWhitelistApp = function ( pkgName, success, error ) {
    exec( function () {}, function () {}, 'psavermodechecker', 'requestWhitelistApp', [ pkgName ] );
};