package com.feicuiedu.gitdroid.common;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by zhengshujuan on 2016/6/29.
 */
public class ActivityUtils {
    //使用弱引用,避免不恰当的持有Activity或Fragment的引用
    //持有activity的引用会阻止Activity的内存回收
private WeakReference<Activity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;
    private Toast toast;
    public ActivityUtils(Activity activity){
        activityWeakReference=new WeakReference<Activity>(activity);
    }
    public ActivityUtils(Fragment fragment){
        fragmentWeakReference=new WeakReference<Fragment>(fragment);
    }
    public void InterActivity(){

    }
private @Nullable Activity getActivity(){
    if (activityWeakReference!=null){
        return activityWeakReference.get();
    }
    if (fragmentWeakReference!=null){
        Fragment fragment=fragmentWeakReference.get();
        return fragment==null?null:fragment.getActivity();
        }
    return null;
    }
    public void showToast(CharSequence msg){
        Activity activity=getActivity();
        if (activity!=null){
            if (toast==null){
                toast=Toast.makeText(activity,msg,Toast.LENGTH_SHORT);
                toast.setText(msg);
                toast.show();
            }
        }
    }
    @SuppressWarnings("SameparameterValue")
    public void showToast(int resId){
        Activity activity=getActivity();
        if (activity!=null){
            String msg=activity.getString(resId);
            showToast(msg);
        }
    }
    public void startActivity(Class<?extends Activity> clazz){
        Activity activity=getActivity();
        if (activity==null){
            return;
        }
        Intent intent=new Intent(activity,clazz);
        activity.startActivity(intent);
    }
}

