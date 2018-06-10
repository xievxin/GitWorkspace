package art.xx.com.testapp.databinding;
import art.xx.com.testapp.R;
import art.xx.com.testapp.BR;
import android.view.View;
public class FirstBdBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toastBtn, 2);
    }
    // views
    private final android.widget.LinearLayout mboundView0;
    private final android.widget.Button mboundView1;
    public final android.widget.Button toastBtn;
    // variables
    private org.json.JSONObject mJo;
    private art.xx.com.testapp.BdActivity mActivity;
    private art.xx.com.testapp.BdActivity.MyPOJO mPojo;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FirstBdBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        final Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.Button) bindings[1];
        this.mboundView1.setTag(null);
        this.toastBtn = (android.widget.Button) bindings[2];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
            case BR.jo :
                setJo((org.json.JSONObject) variable);
                return true;
            case BR.activity :
                setActivity((art.xx.com.testapp.BdActivity) variable);
                return true;
            case BR.pojo :
                setPojo((art.xx.com.testapp.BdActivity.MyPOJO) variable);
                return true;
        }
        return false;
    }

    public void setJo(org.json.JSONObject Jo) {
        this.mJo = Jo;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.jo);
        super.requestRebind();
    }
    public org.json.JSONObject getJo() {
        return mJo;
    }
    public void setActivity(art.xx.com.testapp.BdActivity Activity) {
        this.mActivity = Activity;
    }
    public art.xx.com.testapp.BdActivity getActivity() {
        return mActivity;
    }
    public void setPojo(art.xx.com.testapp.BdActivity.MyPOJO Pojo) {
        this.mPojo = Pojo;
    }
    public art.xx.com.testapp.BdActivity.MyPOJO getPojo() {
        return mPojo;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangePojo((art.xx.com.testapp.BdActivity.MyPOJO) object, fieldId);
        }
        return false;
    }
    private boolean onChangePojo(art.xx.com.testapp.BdActivity.MyPOJO Pojo, int fieldId) {
        switch (fieldId) {
            case BR._all: {
                synchronized(this) {
                        mDirtyFlags |= 0x1L;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        org.json.JSONObject jo = mJo;
        java.lang.String joOptStringMboundView1AndroidStringUrl = null;

        if ((dirtyFlags & 0xaL) != 0) {



                if (jo != null) {
                    // read jo.optString(@android:string/url)
                    joOptStringMboundView1AndroidStringUrl = jo.optString(mboundView1.getResources().getString(R.string.url));
                }
        }
        // batch finished
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, joOptStringMboundView1AndroidStringUrl);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static FirstBdBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static FirstBdBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<FirstBdBinding>inflate(inflater, art.xx.com.testapp.R.layout.first_bd, root, attachToRoot, bindingComponent);
    }
    public static FirstBdBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static FirstBdBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(art.xx.com.testapp.R.layout.first_bd, null, false), bindingComponent);
    }
    public static FirstBdBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static FirstBdBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/first_bd_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new FirstBdBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): pojo
        flag 1 (0x2L): jo
        flag 2 (0x3L): activity
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}