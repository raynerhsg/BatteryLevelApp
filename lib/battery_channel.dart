import 'package:flutter/services.dart';

class BatteryChannel {
  final MethodChannel _channel = const MethodChannel('myChannel');

  Future<int> getBatteryLevel() async {
    try {
      final level = await _channel.invokeMethod('batteryLevel');
      return level;
    } catch (e) {
      rethrow;
    }
  }
}
