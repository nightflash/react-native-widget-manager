package ru.spbmax.react.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
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
    public void reloadWidgets(final Integer delay, final Promise promise) {
        int [] ids = _getIds();

        if (delay != null && delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                promise.reject(e);
            }
        }

        sendUpdateIntent(ids);

        promise.resolve(_convertIds(ids));
    }

    @ReactMethod
    public void getWidgetIds(final Promise promise) {
        promise.resolve(_convertIds(_getIds()));
    }

    private void sendUpdateIntent(int[] ids) {
        Context context = reactContext.getApplicationContext();

        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    private int[] _getIds() {
        Context context = reactContext.getApplicationContext();

        AppWidgetManager man = AppWidgetManager.getInstance(context);
        return man.getAppWidgetIds(
                new ComponentName(context, this.widgetClass));
    }

    private WritableArray _convertIds(int[] ids) {
        WritableArray resultArray = new WritableNativeArray();
        for (int i = 0; i < ids.length; i++) {
            resultArray.pushInt(ids[i]);
        }

        return resultArray;
    }

}
