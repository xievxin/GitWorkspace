package com.ckjr.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射赋值
 * Created by xin on 2016/4/18.
 */
public class InjectUtils {


	/**
	 * 注入Activity
	 * @param activity
	 */
	public static void injectToActivity(Activity activity) {
		inject(activity, activity);
	}

	/**
	 * 注入Fragment
	 * @param fragment
	 * @param viewGroup
	 */
	public static void injectToFragment(Fragment fragment, View viewGroup) {
		inject(fragment, viewGroup);
	}

	private static synchronized void inject(final Object objectCls, Object findViewCls) {
		if(objectCls == null && findViewCls==null)
			throw new NullPointerException("objectCls or findViewCls is null");
		if(!(findViewCls instanceof Activity) && !(findViewCls instanceof View))
			throw new IllegalArgumentException("findViewCls must be Activity or View");

		Map<Integer, View> viewMaps = new HashMap<Integer, View>();
		final Class cls = objectCls.getClass();
		//①变量
		Field[] fields = cls.getDeclaredFields();
		if(fields!=null && fields.length>0)
			for(Field field:fields) {
				if(field.isAnnotationPresent(ViewInject.class)) {	//View注入
					int resId = field.getAnnotation(ViewInject.class).value()[0];
					viewMaps.put(resId, setView(resId, field, objectCls, findViewCls));
				} else if(field.isAnnotationPresent(OnClick.class)) {	//View注入
					int[] ids = field.getAnnotation(OnClick.class).ids();
					if(ids!=null)
						for(int id:ids) {
							if(viewMaps.get(id)==null)
								viewMaps.put(id, objectFindView(findViewCls, id));
							try {
								field.setAccessible(true);
								viewMaps.get(id).setOnClickListener((View.OnClickListener) field.get(objectCls));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
				}
			}
		//②OnClickListener
		Method[] methods = cls.getDeclaredMethods();
		if(methods!=null && methods.length>0)
			for(final Method method:methods) {
				if(method.isAnnotationPresent(OnClick.class)) {
					int[] ids = method.getAnnotation(OnClick.class).ids();
					if(ids==null || ids.length==0)
						continue;
					View.OnClickListener onClickListener = new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							try {
								method.invoke(objectCls, v);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					for(int id:ids) {
						if(viewMaps.get(id)==null)
							viewMaps.put(id, objectFindView(findViewCls, id));
						if(viewMaps.get(id)!=null)
							viewMaps.get(id).setOnClickListener(onClickListener);
					}
				}
			}

		viewMaps.clear();
	}

	public static View setView(int resId, Field field, Object objectCls, Object findViewCls) {
		View view = objectFindView(findViewCls, resId);
		if(view!=null) {
			try {
				field.setAccessible(true);
				field.set(objectCls, view);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return view;
	}

	public static View objectFindView(Object obj, int resId) {
		if(obj==null)
			return null;
		View view = null;
		if(obj instanceof Activity)
			view = ((Activity) obj).findViewById(resId);
		else if(obj instanceof View)
			view = ((View) obj).findViewById(resId);
		return view;
	}
}
