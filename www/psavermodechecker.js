var exec = require( 'cordova/exec' );

exports.deviceLevelCheck = function ( success, error ) {
    exec( success, error, 'psavermodechecker', 'deviceLevelCheck', [] );
};

exports.appLevelCheck = function ( pkgName, success, error ) {
    exec( success, error, 'psavermodechecker', 'appLevelCheck', [ pkgName ] );
};