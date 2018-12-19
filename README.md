# Android Admobilize Tracker Control library

Library for controling and receiving data for Android Detector apps 

## Gradle Config

If you already have jcenter() in your build.gradle, only just add a dependency:

``` javascript
  implementation 'admobilize.adtrackerlib.control:libadtrackercontrol:0.1.2'
```

## Implementation Sample

Define `OnDetectorActionListener` for control events

``` java
  OnDetectorActionListener onDetectorActionListener = new OnDetectorActionListener() {

      @Override
      public void onChangeName(String name) {

      }

      @Override
      public void onChangeUrl(String url) {

      }

      @Override
      public void onServiceEnable(boolean enable) {

      }

      @Override
      public void onServiceStart() {

      }

      @Override
      public void onServiceStop(String msg) {

      }

      @Override
      public void onUpdateOrientationAllowed(boolean isOrientationAllowed) {

      }

      @Override
      public void onUpdateOrientation(String orientation) {

      }

      @Override
      public void onDetectorId(String detectorId) {

      }

      @Override
      public void onDetectorData(String data) {

      }
  };
```

Register it on `ConfigManager`


``` java
  configManager = new ConfigManager(this, onDetectorActionListener);
```

Use it, for example:

``` java
  configManager.setServiceEnable(true);
```





