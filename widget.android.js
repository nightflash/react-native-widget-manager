import { NativeModules } from 'react-native';

const nativeModule = NativeModules.RNWidgetManager;

export default {
  reloadWidgets(delay = 0) {
    return nativeModule.reloadWidgets(delay);
  },
  getWidgetIds() {
    return nativeModule.getWidgetIds();
  }
};