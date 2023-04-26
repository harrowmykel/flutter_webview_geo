package io.flutter.plugins.webviewflutter;

import android.net.Uri;
import android.os.Build;
import android.webkit.PermissionRequest;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GeoLocationPermissionRequest extends PermissionRequest {

    private final String origin;
    private final GeoLocationPermissionRequestInterface callback;


    public GeoLocationPermissionRequest(String origin, GeoLocationPermissionRequestInterface callback){
        this.origin = origin;
        this.callback = callback;
    }
    /**
     * Call this method to get the origin of the web page which is trying to access
     * the restricted resources.
     *
     * @return the origin of web content which attempt to access the restricted
     *         resources.
     */
    public  Uri getOrigin(){
        return Uri.parse(origin);
    }

    /**
     * Call this method to get the resources the web page is trying to access.
     *
     * @return the array of resources the web content wants to access.
     */
    public String[] getResources(){
        return new String[] { "geolocation" };
    }


    /**
     * Call this method to grant origin the permission to access the given resources.
     * The granted permission is only valid for this WebView.
     *
     * @param resources the resources granted to be accessed by origin, to grant
     *        request, the requested resources returned by {@link #getResources()}
     *        must be equals or a subset of granted resources.
     *        This parameter is designed to avoid granting permission by accident
     *        especially when new resources are requested by web content.
     */
    public void grant(String[] resources){
        if(resources.length>1){
            boolean allow = resources[0]=="allow"||resources[1]=="allow";
            boolean retain = resources[0]=="retain"||resources[1]=="retain";
            callback.onGrant(allow, retain);
            return;
        }else
        if(resources.length==1){
            boolean retain = resources[0]=="retain";
            callback.onGrant(true, retain);return;
        }
        callback.onGrant(true, false);
    }

    /**
     * Call this method to deny the request.
     */
    public void deny(){
        callback.onDeny();
    }
}
