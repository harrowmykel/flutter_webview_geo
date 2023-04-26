### changes 


´_flutter_android_2/android/src/main/java/io/flutter/plugins/webviewflutter/WebSettingsHostApiImpl.java´
 
 replace

 ´´´
    @NonNull
    public WebSettings createWebSettings(@NonNull WebView webView) {
      return webView.getSettings();
    }


    @NonNull
    public WebSettings createWebSettings(@NonNull WebView webView) {
      WebSettings webSettings = webView.getSettings();
      webSettings.setGeolocationEnabled(true);
      return webSettings;
    }
    ´´´

---


webview_flutter_android/android/src/main/java/io/flutter/plugins/webviewflutter/WebViewHostApiImpl.java
Add


```
  @Override
  public void create(@NonNull Long instanceId) {
    //...
    final WebView webView = webViewProxy.createWebView(context, binaryMessenger, instanceManager);
    webView.setVerticalScrollBarEnabled(false);
    webView.setHorizontalScrollBarEnabled(false);

    //...
  }
  ```