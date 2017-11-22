package ru.spbmax.react.widget;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WidgetManagerPackage implements ReactPackage {
    private Class widgetClass;
    private String idsField;

    public WidgetManagerPackage(Class widgetClass, String idsField) {
        this.widgetClass = widgetClass;
        this.idsField = idsField;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new WidgetManagerModule(reactApplicationContext, this.widgetClass, this.idsField));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        return Collections.emptyList();
    }

    // Deprecated RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }
}
