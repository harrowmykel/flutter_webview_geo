package io.flutter.plugins.webviewflutter;

public interface GeoLocationPermissionRequestInterface {

    public abstract void onGrant(boolean allow, boolean retain);
    public abstract void onDeny();
}
