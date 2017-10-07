package ru.spbmax.react.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;


public class WidgetManagerModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private Class widgetClass;

    public WidgetManagerModule(ReactApplicationContext context, Class widgetClass) {
        super(context);
        this.reactContext = context;
        this.widgetClass = widgetClass;
    }

    public String getName() {
        return "RNWidgetManager";
    }

    @ReactMethod
    public void reloadWidgets(final Promise promise) {
        Activity activity = getCurrentActivity();

        if (activity != null) {
            int [] ids = this._getIds();

            Intent intent = new Intent();
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            activity.sendBroadcast(intent);

            promise.resolve(ids.length);
        } else {
            promise.reject("noActivity", "no running activity");
        }
    }

    @ReactMethod
    public void getWidgetIds(final Promise promise) {
        int [] ids = this._getIds();

        WritableArray resultArray = new WritableNativeArray();
        for (int i = 0; i < ids.length; i++) {
            resultArray.pushInt(ids[i]);
        }

        promise.resolve(resultArray);
    }

    private int[] _getIds() {
        Context context = reactContext.getApplicationContext();

        AppWidgetManager man = AppWidgetManager.getInstance(context);
        return man.getAppWidgetIds(
                new ComponentName(context, this.widgetClass));
    }

}
