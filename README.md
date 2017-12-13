
## a simple IM client

## effect

![img](https://github.com/heruijun/TravelFlanGCM/blob/master/effect.gif)

## kotlin version
git checkout -b master

## java8 version
git checkout -b java8

## server side
see https://github.com/heruijun/TravelFlanServer

## architecture
kotlin + anko + gcm + md
DSL Layout

## modularization development

```
if (isUserModule.toBoolean()) {
    apply plugin: 'com.android.library'
} else {
    apply plugin: 'com.android.application'
}

sourceSets {
    main {
        if (isUserModule.toBoolean()) {
            manifest.srcFile 'src/main/release/AndroidManifest.xml'
        } else {
            manifest.srcFile 'src/main/debug/AndroidManifest.xml'
        }
    }
}

```