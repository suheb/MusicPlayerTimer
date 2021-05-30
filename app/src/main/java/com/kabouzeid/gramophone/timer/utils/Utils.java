package com.kabouzeid.gramophone.timer.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


import com.kabouzeid.gramophone.R;

import java.lang.reflect.Method;
import java.util.List;

public class Utils {
    public static final String ACTION_DISMISS_NOTIFICATION = "com.kabouzeid.gramophone.timer.dismiss_notification";
    public static final String ACTION_UPDATE_TIMER = "com.kabouzeid.gramophone.timer.update_timer";
    private final static String PREFS_NAME = "prefs";

    public static void changeFragment(Fragment fragment, FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.from_middle, R.anim.to_middle)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public static void setAppPrefs(Context context, String name, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String getAppPrefs(Context context, String name, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(name, defValue);
    }

    public static boolean removeTask(Context context, String processName, int flags) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Method removeTask = null;
        int taskId = 0;
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(processName)) {
                taskId = appProcess.pid;
            }
        }
        try {
            Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");

            removeTask = activityManagerClass.getMethod("removeTask", new Class[]{int.class, int.class});
            removeTask.setAccessible(true);

        } catch (ClassNotFoundException e) {
            Log.i("MyActivityManager", "No Such Class Found Exception", e);
        } catch (Exception e) {
            Log.i("MyActivityManager", "General Exception occurred", e);
        }
        try {
            return (Boolean) removeTask.invoke(activityManager, Integer.valueOf(taskId), Integer.valueOf(flags));
        } catch (Exception ex) {
            Log.i("MyActivityManager", "Task removal failed", ex);
        }
        return false;
    }

}
