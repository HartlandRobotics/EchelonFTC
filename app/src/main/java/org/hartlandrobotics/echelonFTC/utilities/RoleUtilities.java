package org.hartlandrobotics.echelonFTC.utilities;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;

public class RoleUtilities {

    public static boolean isAdminTablet(String tabletName){
        return tabletName.startsWith("c");

    }
    public static boolean isStudentTablet(String tabletName){
        if (tabletName.startsWith("red")){
            return true;
        }
        if (tabletName.startsWith("blue")){
            return true;
        }
        return false;
    }

    public static String deviceColor(String tabletName){
        if (tabletName.startsWith("red")){
            return "red";
        }
        if (tabletName.startsWith("blue")){
            return "blue";
        }
        if (tabletName.startsWith("coach")){
            return "yellow";
        }
        if (tabletName.startsWith("captain")){
            return "yellow";
        }
        return StringUtils.EMPTY;
    }

}
