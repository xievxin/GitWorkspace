package art.xx.com.testapp.databinding;
import art.xx.com.testapp.R;
import art.xx.com.testapp.BR;
import android.view.View;
public class SecBdBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    private final android.widget.LinearLayout mboundView0;
    private final android.widget.Button mboundView2;
    public final android.widget.Button toastBtn;
    // variables
    private art.xx.com.testapp.BdActivity mActivity;
    private art.xx.com.testapp.BdActivity.MyPOJO mPojo;
    // values
    // listeners
    private OnClickListenerImpl mActivityOnClickAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers

    public SecBdBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        final Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.Button) bindings[2];
        this.mboundView2.setTag(null);
        this.toastBtn = (android.widget.Button) bindings[1];
        this.toastBtn.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
            case BR.activity :
                setActivity((art.xx.com.testapp.BdActivity) variable);
                return true;
            case BR.pojo :
                setPojo((art.xx.com.testapp.BdActivity.MyPOJO) variable);
                return true;
        }
        return false;
    }

    public void setActivity(art.xx.com.testapp.BdActivity Activity) {
        this.mActivity = Activity;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.activity);
        super.requestRebind();
    }
    public art.xx.com.testapp.BdActivity getActivity() {
        return mActivity;
    }
    public void setPojo(art.xx.com.testapp.BdActivity.MyPOJO Pojo) {
        updateRegistration(0, Pojo);
        this.mPojo = Pojo;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.pojo);
        super.requestRebind();
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
        art.xx.com.testapp.BdActivity activity = mActivity;
        art.xx.com.testapp.BdActivity.MyPOJO pojo = mPojo;
        android.view.View.OnClickListener activityOnClickAndroidViewViewOnClickListener = null;
        java.lang.String pojoName = null;

        if ((dirtyFlags & 0x6L) != 0) {



                if (activity != null) {
                    // read activity::onClick
                    activityOnClickAndroidViewViewOnClickListener = (((mActivityOnClickAndroidViewViewOnClickListener == null) ? (mActivityOnClickAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mActivityOnClickAndroidViewViewOnClickListener).setValue(activity));
                }
        }
        if ((dirtyFlags & 0x5L) != 0) {



                if (pojo != null) {
                    // read pojo.name
                    pojoName = pojo.getName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, pojoName);
        }
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            this.toastBtn.setOnClickListener(activityOnClickAndroidViewViewOnClickListener);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private art.xx.com.testapp.BdActivity value;
        public OnClickListenerImpl setValue(art.xx.com.testapp.BdActivity value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onClick(arg0);
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static SecBdBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static SecBdBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<SecBdBinding>inflate(inflater, art.xx.com.testapp.R.layout.sec_bd, root, attachToRoot, bindingComponent);
    }
    public static SecBdBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static SecBdBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(art.xx.com.testapp.R.layout.sec_bd, null, false), bindingComponent);
    }
    public static SecBdBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static SecBdBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/sec_bd_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new SecBdBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): pojo
        flag 1 (0x2L): activity
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}