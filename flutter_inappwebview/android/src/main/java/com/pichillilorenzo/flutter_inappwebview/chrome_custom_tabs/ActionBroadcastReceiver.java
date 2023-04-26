package com.pichillilorenzo.flutter_inappwebview.chrome_custom_tabs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.browser.customtabs.CustomTabsIntent;

public class ActionBroadcastReceiver extends BroadcastReceiver {
  protected static final String LOG_TAG = "ActionBroadcastReceiver";
  public static final String KEY_ACTION_ID = "com.pichillilorenzo.flutter_inappwebview.ChromeCustomTabs.ACTION_ID";
  public static final String KEY_ACTION_VIEW_ID = "com.pichillilorenzo.flutter_inappwebview.ChromeCustomTabs.ACTION_VIEW_ID";
  public static final String KEY_URL_TITLE = "android.intent.extra.SUBJECT";

  @Override
  public void onReceive(Context context, Intent intent) {
    int clickedId = intent.getIntExtra(CustomTabsIntent.EXTRA_REMOTEVIEWS_CLICKED_ID, -1);
    String url = intent.getDataString();
    if (url != null) {
      Bundle b = intent.getExtras();
      String viewId = b.getString(KEY_ACTION_VIEW_ID);

      if (clickedId == -1) {
        int id = b.getInt(KEY_ACTION_ID);
        String title = b.getString(KEY_URL_TITLE);

        ChromeCustomTabsActivity browser = ChromeSafariBrowserManager.browsers.get(viewId);
        if (browser != null && browser.channelDelegate != null) {
          browser.channelDelegate.onItemActionPerform(id, url, title);
        }
      } else {
        ChromeCustomTabsActivity browser = ChromeSafariBrowserManager.browsers.get(viewId);
        if (browser != null && browser.channelDelegate != null) {
          browser.channelDelegate.onSecondaryItemActionPerform(browser.getResources().getResourceName(clickedId), url);
        }
      }
    }
  }
}
