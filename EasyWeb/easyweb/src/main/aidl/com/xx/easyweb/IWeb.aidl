// IWeb.aidl
package com.xx.easyweb;

// Declare any non-default types here with import statements

interface IWeb {

    void loadUrl(String url);

    void postUrl(String url, in byte[] bytes);

    void loadData(String data, String mimeType, String encoding);

    void closeAll();

    void setContent(in RemoteViews remoteViews);

    void setWebClass(String webclassName);
    void setIntentService(String intentServiceName);

    /**
     * 重复赋值static
     */
    boolean setStaticString(String className, String fieldName, String value);

    /**
     * 重复赋值static
     */
    boolean setStaticBoolean(String className, String fieldName, boolean value);

    /**
     * 重复赋值static
     */
    boolean setStaticInteger(String className, String fieldName, int value);
}
