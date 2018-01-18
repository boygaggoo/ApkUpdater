# ApkUpdater
DownLoadManager based on the installation package updates, installation package cache, support for HTTP, custom UI, provides the default UI.


## demo
In accordance with the usual chart or first. From the pictures you can see that the apk is cached, that is, if the download is complete, if not installed the next time you check for updates, if you find the server version and the cached version of the same will skip the download.

! [demonstrate] (materials / gif_apk_updater.gif)

## download
###### The first step: Add the JitPack repository to your project's root gradle file.
`` `
allprojects {
    repositories {
        ...
        maven {url 'https://jitpack.io'}
    }
}
`` `
###### Step Two: Add this dependency.
`` `
dependencies {
    compile 'com.github.kelinZhou: ApkUpdater: 1.5.2'
}
`` `

## use
###### Add permissions
You need to add the following permissions to your manifest file:
`` `
    <! - Network Access ->
    <uses-permission android: name = "android.permission.INTERNET" />
    <! - SD card read permission ->
    <uses-permission android: name = "android.permission.READ_EXTERNAL_STORAGE" />
    <! - SD card write permission ->
    <uses-permission android: name = "android.permission.WRITE_EXTERNAL_STORAGE" />
    <! - Not pop-up notification bar permissions ->
    <uses-permission android: name = "android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
    <! - DownloadManager ->
    <uses-permission android: name = "android.permission.ACCESS_DOWNLOAD_MANAGER" />
    <! - Get Network Status Permissions ->
    <uses-permission android: name = "android.permission.ACCESS_NETWORK_STATE" />
`` `
###### Get updated information
First use your project's network access capability to get updated information from the server side and converted to ** javaBean ** object, and then make this object ** UpdateInfo ** interface. Here's all the methods in this interface:
`` `
public interface UpdateInfo {

    / **
     * Get the version number on the network.
     @return Returns the value of the current object's version number field.
     * /
    int getVersionCode ();

    / **
     * Get the latest version of the download link.
     @return Returns the value of the download link field for the current object.
     * /
    String getDownLoadsUrl ();

    / **
     * Whether to force the update.
     @return <code color = "blue"> true </ code> means mandatory updates, and <code color = "blue"> false </ code> is the opposite.
     * /
    isForceUpdate ();

    / **
     * Obtain a mandatory update version number, if your current compulsory update is for one or some version, you can return in the method. Provided that {@link #isForceUpdate ()}
     * The return value must be true, otherwise the return value of this method is meaningless.
     * @return returns the version number you want to force update, which can return null if null is returned and {@link #isForceUpdate ()} returns true
     * Means all versions are forcibly updated.
     * /
    @Nullable int [] getForceUpdateVersionCodes ();

    / **
     * Get Apk filename (eg xxx.apk or xxx). Suffix name is not necessary.
     * Can return null, if the return null use the date as the default file name.
     * /
    @Nullable String getApkName ();

    / **
     * Get updated content. This is what you updated this time can return here, the content returned here will now Dialog news, if you do not disable Dialog.
     @return returns the content of your update.
     * /
    CharSequence getUpdateMessage ();
}
`` `
###### Build ** Updater ** object
This object is created using the constructor mode, which configures the Icon, Title, Message, and Title and Desc of NotifyCation in the Dialog provided by the Api.

Method Name | Description |
| ----- | ------ |
| public Builder setCallback (UpdateCallback callback) | Set the listener object. |
public Builder setCheckDialogTitle (CharSequence title) | Configure the title of the dialog box when checking for updates. |
| public Builder setDownloadDialogTitle (CharSequence title) | Configure the title of the dialog box when downloading updates. |
| public Builder setDownloadDialogMessage (String message) | Configure the dialog box message when downloading updates. |
public Builder setNotifyTitle (CharSequence title) | Sets the title of the notification bar. (There is no notification bar when forced to update.) |
| public Builder setNotifyDescription (CharSequence description) | Set the description of the notification bar. (There is no notification bar when forced to update.) |
| public Builder setNoDialog () | You can call this method to turn off the default dialog box if you want to create the dialog yourself instead of using the default provided dialog box. If you close the default dialog then you must implement the UI interaction yourself and call the `` `updater.setCheckHandlerResult (boolean)` `` method when the user's update prompts to react. The timing of UI interaction is in the callback. |
| public Builder setCheckWiFiState (boolean check) | set does not check the WiFi state, the default is to check the WiFi state, that is if you download the update if there is no link WiFi default is to prompt the user. But if you do not want to be prompted, you can disable WiFi checking by calling this method. |
| public Updater builder () | Complete the ** Updater ** object's build. |
###### Check for updates
Check the updated code is as follows:
`` ``
private void checkUpdate (UpdateModel updateModel) {
    new Updater.Builder (MainActivity.this) .builder (). check (updateModel);
}
`` ``
Updater check method In addition to `` `public void check (UpdateInfo updateInfo)` `` there is another overloaded public void check (UpdateInfo updateInfo, boolean autoInstall) `` `autoInstall parameter is not to automatically install, If you just want to download an apk file and do not want to install it now, you can set this parameter to false.
###### start download
If you call the check update method this step is ** does not require you to manually call the **, but if you simply want to use the API to download Apk action can be performed by this method, the code is as follows:
`` `
new Updater.Builder (MainActivity.this) .builder (). download (updateModel);
`` `
Like the check update ** check () ** method, the download method also provides an overload, all of which are as follows:

Method Name | Description |
| ----- | ----- |
| public void download (@NonNull UpdateInfo updateInfo) | Start the download. updateInfo: update information object. |
| public void download (@NonNull UpdateInfo updateInfo, boolean autoInstall) | updateInfo: Updates the information object. autoInstall: Whether to install automatically, true means that it will be automatically installed after the download is completed, and false means it will not need to be installed. |
| public void download (@NonNull UpdateInfo updateInfo, CharSequence notifyCationTitle, CharSequence notifyCationDesc, boolean autoInstall) | updateInfo: updates the information object. notifyCationTitle: The title of the notification bar during download. This parameter can be null if it is forcibly updated because there is no notification bar prompt for forced update. notifyCationDesc: Description of the notification bar during download. This parameter can be null if it is forcibly updated because there is no notification bar prompt for forced update. autoInstall: Whether to install automatically, true means that it will be automatically installed after the download is completed, and false means it will not need to be installed. |
###### Install APK
Installation is not to be concerned about you, the download will automatically enter the installation page. Unless you disable automatic installation, or want to install an existing Apk. If so you can use ** UpdateHelper ** `` `public static void installApk (Context context, File apkFile)` `` method.

###### others
The project provides two tools: UpdateHelper and NetWorkStateUtil.

* * *
### License
`` `
Copyright 2016 kelin410@163.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific lang
