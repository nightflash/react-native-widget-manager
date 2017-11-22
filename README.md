# react-native-widget-manager
Provide an JS API to force native Android widgets reload and getting their ids

## Installation

### Android

```bash
npm i react-native-widget-manager --save
react-native link react-native-widget-manager
```

 Change your *MainAplication.java* from  
 
 ```java
 new WidgetManagerPackage()
 ``` 
 
 to

```java
new WidgetManagerPackage(Widget.class, null)
```

if you want to use `AppWidgetManager.EXTRA_APPWIDGET_IDS` field for widget IDs, or

```java
new WidgetManagerPackage(Widget.class, "mySpecificIDsField")
```

if you want to use your own field (**recommended**)

where *Widget* is your widget provider java class (you should have Widget.java in your source code)

https://developer.android.com/guide/topics/appwidgets/index.html

## API

Works for **Android only** but safe to execute on iOS.

### `reloadWidgets(delay)`


Reload all widgets that were instantiated from your Widget.java (Do nothing on iOS)

 - There is a bug in React/Android/Headless JS that you can't use *setTimeout* inside a background task,
 so you may need a way to reload your widgets after `JS -> Native` synchronization. 
 For example after `AsyncStorage` save; For this case you can use this delay.
 If you don't need any sync keep it 0 or don't define it.

  

### `getWidgetIds`

Return promise that will be fulfilled with array of ids. (Return empty array on iOS)
