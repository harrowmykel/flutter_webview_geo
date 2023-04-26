package com.pichillilorenzo.flutter_inappwebview;

// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.
//FROM https://github.com/flutter/packages/blob/main/packages/webview_flutter/webview_flutter_android/android/src/main/java/io/flutter/plugins/webviewflutter/WebChromeClientHostApiImpl.java#L96

import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.pichillilorenzo.flutter_inappwebview.webview.in_app_webview.InAppWebView;

public class InAppWebViewNewWindowBugFix {


    @Nullable
    WebViewClient webViewClient;

    public InAppWebViewNewWindowBugFix(){
    }

    /**
     * Verifies that a url opened by `Window.open` has a secure url.
     *
     * @param view the WebView from which the request for a new window originated.
     * @param resultMsg the message to send when once a new WebView has been created. resultMsg.obj
     *     is a {@link WebView.WebViewTransport} object. This should be used to transport the new
     *     WebView, by calling WebView.WebViewTransport.setWebView(WebView)
     * @return this method should return true if the host application will create a new window, in
     *     which case resultMsg should be sent to its target. Otherwise, this method should return
     *     false. Returning false from this method but also sending resultMsg will result in
     *     undefined behavior
     */
    public boolean applyOnCreateWindowBugFix(
            @NonNull final InAppWebView view,
            @NonNull Message resultMsg, @Nullable String url) {
        Log.d("westlife", "a");
        Log.d("micheal--l", url==null?"null":url);
        Log.d("westlife", "b");
        if(url != null && !url.isEmpty()){
            Log.d("westlife", "c");
            return false;
        }
        Log.d("westlife", "d");
     // onCreateWindowWebView the temporary WebView used to verify the url is secure
        @Nullable
        InAppWebView onCreateWindowWebView = new InAppWebView(view.getContext());

        Log.d("westlife", "e");
        // WebChromeClient requires a WebViewClient because of a bug fix that makes
        // calls to WebViewClient.requestLoading/WebViewClient.urlLoading when a new
        // window is opened. This is to make sure a url opened by `Window.open` has
        // a secure url.
        if (view.inAppWebViewClientCompat != null) {
            webViewClient=    view.inAppWebViewClientCompat;
            Log.d("westlife", "g");
        } else if (view.inAppWebViewClient != null) {
            webViewClient=  view.inAppWebViewClient;
            Log.d("westlife", "h");
        }else{
            return false;
        }
        Log.d("westlife", "f");

        //pretend like there is a new window but every thing opened will be opened in the current window/view
        final WebViewClient windowWebViewClient = new WebViewClient() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public boolean shouldOverrideUrlLoading(
                            @NonNull WebView windowWebView, @NonNull WebResourceRequest request) {
                        Log.d("westlife", "i");
                        Log.d("westlife", request.getUrl().toString());
                        if (!webViewClient.shouldOverrideUrlLoading(view, request)) {
                            Log.d("westlife", "j");
                            view.loadUrl(request.getUrl().toString());
                        }
                        return true;
                    }

                    // Legacy codepath for < N.
                    @Override
                    @SuppressWarnings({"deprecation", "RedundantSuppression"})
                    public boolean shouldOverrideUrlLoading(WebView windowWebView, String url) {
                        Log.d("westlife", "k");
                        Log.d("westlife", url);
                        if (!webViewClient.shouldOverrideUrlLoading(view, url)) {
                            Log.d("westlife", "l");
                            view.loadUrl(url);
                        }
                        return true;
                    }
                };
        Log.d("westlife", "m");
        onCreateWindowWebView.setWebViewClient(windowWebViewClient);

        Log.d("westlife", "n");
        final InAppWebView.WebViewTransport transport = (InAppWebView.WebViewTransport) resultMsg.obj;
        Log.d("westlife", "o");
        transport.setWebView(onCreateWindowWebView);
        resultMsg.sendToTarget();
Log.d("westlife", "p");
        return true;
    }
}
