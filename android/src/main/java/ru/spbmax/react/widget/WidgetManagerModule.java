package ru.spbmax.react.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;


public class WidgetManagerModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext reactContext;
    private Class widgetClass;
    private String idsField;

    public WidgetManagerModule(ReactApplicationContext context, Class widgetClass, String idsField) {
        super(context);
        this.reactContext = context;
        this.widgetClass = widgetClass;

        if (idsField == null) {
            this.idsField = AppWidgetManager.EXTRA_APPWIDGET_IDS;
        } else {
            this.idsField = idsField;
        }

    }

    final private String _moduleName = "RNWidgetManager";

    public String getName() {
        return _moduleName;
    }

    @ReactMethod
    public void reloadWidgets(final Integer delay, final Promise promise) {
        Log.d(_moduleName, "Reload widgets method, delay: " + delay);

        final int [] ids = _getIds();

        if (delay != null && delay > 0) {
            new Thread() {
                public void run() {
                    try {
                        Log.d(_moduleName, "Reload widgets delayed");
                        Thread.sleep(delay);

                        Log.d(_moduleName, "Reload widgets delay finished");
                        _sendUpdateIntent(ids);
                        promise.resolve(_convertIds(ids));
                    } catch(InterruptedException e) {
                        promise.reject(e);
                    }
                }
            }.start();
        } else {
            _sendUpdateIntent(ids);
            promise.resolve(_convertIds(ids));
        }
    }

    @ReactMethod
    public void getWidgetIds(final Promise promise) {
        promise.resolve(_convertIds(_getIds()));
    }

    private void _sendUpdateIntent(int[] ids) {
        Log.d(_moduleName, "Sending reload intent");
        Context context = reactContext.getApplicationContext();

        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(this.idsField, ids);
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
