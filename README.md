# react-native-widget-manager
Provide an JS API to force native Android widgets reload and getting their ids

## Installation
npm i react-native-widget-manager --save
react-native link react-native-widget-manager

Edit the MainAplication.java as:
new WidgetManagerPackage(Widget.class)
