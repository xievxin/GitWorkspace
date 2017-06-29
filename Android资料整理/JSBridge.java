1、通过prompt函数通讯
webView.setWebChromeClient(new MyWebChromeClient());
class MyWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            try {
                JsBridge.callJava(message);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                result.confirm();
            }
            return true;
        }
    }

2、通过register函数
public class JsBridge {

    private static Map<String, Map<String, Method>> jsMethods=null;

    static {
        jsMethods = new HashMap<>();
        register("bridgeImpl", BridgeImpl.class);
    }

    /**
     * 把每个类中的函数存储进去
     **/
    private static void register(String exposedName, Class cls) {
        if(jsMethods.containsKey(exposedName))
            return;
        Map<String, Method> methodMap = new HashMap<>();
        for(Method method : cls.getDeclaredMethods()) {
//            if(method.getModifiers()= Modifier.STATIC)
//                continue;
            Class[] parameterTypes = method.getParameterTypes();
            if(parameterTypes!=null && parameterTypes.length==1 && parameterTypes[0]== String.class) {
                methodMap.put(method.getName(), method);
            }
        }
        jsMethods.put(exposedName, methodMap);
    }

    /**
     * Js调用Java方法
     * @param uriString (Ex:http://bridgeImpl/showToast?{param:\'toast content\'})
     */
    public static void callJava(String uriString) throws Exception{
        Uri uri = Uri.parse(uriString);
        String exposedName = uri.getHost();                 //bridgeImpl
        String methodName = uri.getPath().replace("/", ""); //showToast
        String param = uri.getQuery();                      //{param:'toast content'}
        if(jsMethods.containsKey(exposedName)) {
            Map<String, Method> methodMap = jsMethods.get(exposedName);
            if(methodMap.containsKey(methodName)) {
                Method method = methodMap.get(methodName);
                method.invoke(method.getDeclaringClass().newInstance(), param);
            }
        }
    }
}

3、最好做一个interface，BridgeImpl去实现它，getDeclaredMethods仅获得实现方法，加快遍历速度(过滤wait,notify...)
public interface IBridge {
    public  void showToast(String txt);
}

public class BridgeImpl implements IBridge {

    @Override
    public void showToast(String txt) {
        int xievxin = 1;
        Log.e("~", "BridgeImpl.showToast" + xievxin);
    }
}

4、不要忘了加上混淆代码
-keepclassmembers public class com.ckjr.model.BridgeImpl {
    *;
}

5、Js使用方式
<button onclick="prompt('http://bridgeImpl/showToast?{param:\'toast content\'}','')" />