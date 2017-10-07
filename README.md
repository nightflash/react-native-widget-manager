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
new WidgetManagerPackage(Widget.class)
``` 

## API

Works for **Android only** but safe to execute on iOS.

### `reloadWidgets`

Reload all widgets that were instantiated from your Widget.java (Do nothing on iOS)
  

### `getWidgetIds`

Return promise that will be fulfilled with array of ids. (Return empty array on iOS)
