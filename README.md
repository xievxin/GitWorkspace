`You can make your webview run in another process simply by it.
This is version 0.5.0 beta.It is suggested that you download the source code for transformation.`

Usages:
====
In your Application.onCreate():

```Java
// 提供你自己的webview extends EasyWebview
WebServiceManager.bindWebService(RealEasyWebview.class);

// your own IntentService which could run in main process:
WebServiceManager.bindIntentService(CkEasyIntentService.class);

if(com.xx.util.SysUtil.isEasyWebProcess(this)) {
  return;
}
WebServiceManager.getInstance().init(this, new OnServiceConnectListener() {
  @Override
  public void onServiceConnected(IWeb webService) {
    // All operate are through this.
    // See com.xx.easyweb.WebService in detail
  }
});
```    
        
        
Add EasyWeb to your project:
====
Via Gradle:
----
```Java
  compile 'com.xx:easyweb:0.5.1'
  ```
  
Via Maven:
----
```Java
<dependency>
  <groupId>com.chuangke18</groupId>
  <artifactId>easyweb</artifactId>
  <version>0.5.0</version>
  <type>aar</type>
</dependency>
```


Welcome to my [blog](http://blog.csdn.net/u011511577).
====
