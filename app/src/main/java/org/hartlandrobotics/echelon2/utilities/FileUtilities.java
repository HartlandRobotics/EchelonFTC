package org.hartlandrobotics.echelon2.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import java.io.File;

public class FileUtilities {

    public static File getFile(Context applicationContext, String directoryName, String fileName){
        File directory = ensureDirectory(applicationContext, directoryName);
        if( directory == null ) return null;

        File configFile = new File(directory.getAbsolutePath(), fileName);
        if( !configFile.exists() ){
            Toast.makeText(applicationContext, configFile + " is not on this tablet!", Toast.LENGTH_LONG).show();
            return null;
        }

        return configFile;
    }

    private static File ensureDirectory( Context appContext, String directoryName){
        ContextWrapper contextWrapper = new ContextWrapper( appContext );
        File configDir =  contextWrapper.getExternalFilesDir(directoryName);
        if( configDir == null ) return null;
        if( !configDir.exists() ) {
            boolean created = configDir.mkdirs();
            if( !created ){
                Toast.makeText(appContext, directoryName + " was not created", Toast.LENGTH_LONG).show();
            }
        }

        return configDir;
    }
}
