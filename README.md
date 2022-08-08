# GWT Client Common

This repository contains various libraries holding common GWT utilities.

## Common utilities

Library containing base GWT infrastructure such as PlaceController and ActivityManager. Works in much the same way that native GWT libraries do, except these replace are interfaces and can are ideally injected through GIN, adding flexibility. They also contain some improvements over the native GWT counterpart, such as activities and places being able to change state without changing activity.

```xml
    <dependency>
      <groupId>nl.aerius</groupId>
      <artifactId>gwt-client-common</artifactId>
      <version>${gwt-client-common.version}</version>
      <type>gwt-lib</type>
    </dependency>
```

## Geo utilities

Library containing shared geo utilities.

```xml
    <dependency>
      <groupId>nl.aerius</groupId>
      <artifactId>gwt-shared-geo-common</artifactId>
      <version>${gwt-client-common.version}</version>
      <type>gwt-lib</type>
    </dependency>
```

Library containing basic geo utilities to be used easily in conjunction with a typical Vue-GWT setup (with events):

```xml
    <dependency>
      <groupId>nl.aerius</groupId>
      <artifactId>gwt-client-geo-ol3</artifactId>
      <version>${gwt-client-common.version}</version>
      <type>gwt-lib</type>
    </dependency>
```

## Vue utilities

Library containing a small amount of basic vue-specific adapters, such as an ActivityManager for Vue.

```xml
    <dependency>
      <groupId>nl.aerius</groupId>
      <artifactId>gwt-client-vue</artifactId>
      <version>${gwt-client-common.version}</version>
      <type>gwt-lib</type>
    </dependency>
```

### Usage

In GIN:

```java
    bind(new TypeLiteral<ActivityManager<AcceptsOneComponent>>() {}).to(VueActivityManager.class);
```
