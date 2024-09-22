package org.hartlandrobotics.echelonFTC.utilities;

import android.content.Context;
import android.provider.Settings;

public class DeviceUtilities {
    public static String getDeviceName(Context context){
        String deviceName = Settings.Secure.getString(context.getContentResolver(), "bluetooth_name");
        return deviceName;
    }
}
