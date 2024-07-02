package com.tangerino.touchless.flutter_method_channel

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {

    private val channelName = "myChannel";

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channelName);

        channel.setMethodCallHandler { call, result ->
            if(call.method == "batteryLevel") {
                 val battery = getBatteryLevel(this)
              if(battery != -1) {
                  result.success(battery);
              } else {
                  result.error("Error", "Battery not available", null);
              }
            } else {
                result.notImplemented();
            }

        }


    }

    private fun getBatteryLevel(context: Context): Int {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context.registerReceiver(null, ifilter)
        }

        val level: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        return if (level != -1 && scale != -1) {
            (level / scale.toFloat() * 100).toInt()
        } else {
            -1 // Return -1 if the battery level could not be determined
        }
    }

}
