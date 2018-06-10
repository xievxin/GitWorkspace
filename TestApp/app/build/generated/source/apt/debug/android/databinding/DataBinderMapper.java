
package android.databinding;
import art.xx.com.testapp.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 14;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case art.xx.com.testapp.R.layout.first_bd:
                    return art.xx.com.testapp.databinding.FirstBdBinding.bind(view, bindingComponent);
                case art.xx.com.testapp.R.layout.sec_bd:
                    return art.xx.com.testapp.databinding.SecBdBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -1299301465: {
                if(tag.equals("layout/first_bd_0")) {
                    return art.xx.com.testapp.R.layout.first_bd;
                }
                break;
            }
            case -1119786010: {
                if(tag.equals("layout/sec_bd_0")) {
                    return art.xx.com.testapp.R.layout.sec_bd;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"
            ,"activity"
            ,"jo"
            ,"pojo"};
    }
}