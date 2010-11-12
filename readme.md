Blackjack
==============

By P.Antoine.

Introduction
------------

This is a very simple Android application that is being used by me to learn how
the write for the Android UI for phones. It is a basic app that works most of the
time but is never designed to be released on a phone and is only a learning tool.

The version here is formatted for the ANT builder that turns up with the Android
SDK and should fit into to the eclipse build as well. I changed from the eclipse
to the ANT version, most of this code v0.1 was written with the help of eclipse
Helios, but eclipse is too slow and gets in the way too much. So the ANT build 
is here.

Building and running
--------------------

To tell the build where your android sdk dir is, you need to change the
*local.properties* file. The *sdk_dir* variable needs changing.

*cd* to the Blackjack directory and call:
	ant debug

This will cause it all to build. To run the app in the emulator do the following:

	../android-sdk-linux_x86/tools/android&
	../android-sdk-linux_x86/tools/adb install -r bin/Blackjack-debug.apk 
	../android-sdk-linux_x86/tools/adb logcat

This assumes the Blackjack directory is copied into the Android SDK directory.

Other Things
------------

To get the logging out of the app call the following:

	../android-sdk-linux_x86/tools/adb logcat

You can send the debug to a device by calling:

	../android-sdk-linux_x86/tools/adb -d install -r bin/Blackjack-debug.apk 

...and that is all.

Have Fun.

Peter.
