package org.hartlandrobotics.echelonFTC.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;
import java.io.File;

public class FileUtilities {
    public static File getFile(Context applicationContext, String directoryName, String fileName){
        File directory = ensureDirectory(applicationContext, directoryName);

        File configFile = new File(directory.getAbsolutePath(), fileName);
        if( !configFile.exists() ){
            Toast.makeText(applicationContext, configFile + " is not on this tablet!", Toast.LENGTH_LONG).show();
            return null;
        }

        return configFile;
    }

    public static File ensureDirectory( Context appContext, String directoryName){
        ContextWrapper contextWrapper = new ContextWrapper( appContext );
        File filesDir =  contextWrapper.getFilesDir();

        File dir ;
        if(!( filesDir.toString().endsWith("/") ||
                directoryName.startsWith("/") )
        ){
            dir = new File(filesDir.getAbsolutePath() + "/" + directoryName);
        } else {
            dir = new File(filesDir.getAbsolutePath() + directoryName);
        }
        if( !dir.exists() ) {
            boolean created = dir.mkdirs();
            if( !created ){
                Toast.makeText(appContext, directoryName + " was not created", Toast.LENGTH_LONG).show();
            }
        }

        return dir;
    }
}
