echo Android AirInput -- Initializing...

echo Waiting for device to be connected...
adb wait-for-device

echo - Installing native service...
adb push airinput/dist/air-native "/data/local/tmp/air-native"
adb shell chmod 755 "/data/local/tmp/air-native"

echo Starting...
# adb shell kill -9 "/data/local/tmp/air-native" // doesnt work wtf
adb shell "/data/local/tmp/air-native -daemon"
adb shell "/data/local/tmp/air-native"

echo Service started successfully.
