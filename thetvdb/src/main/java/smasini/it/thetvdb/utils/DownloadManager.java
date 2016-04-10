package smasini.it.thetvdb.utils;

import android.util.Log;

import java.io.File;

/**
 * Project: Traxer
 * Package: smasini.it.thetvdb.utils
 * Created by Simone Masini on 04/02/2016 at 22.51.
 */
public class DownloadManager {

    public static boolean makeDirectory(String path){
        File file = new File(path);
        if(!file.exists()){
            if(file.mkdirs())
            {
                Log.d("created_directory", file.getPath());
                return true;
            }
            else{
                Log.d("created_directory", "error");
                return false;
            }
        }else
        {
            Log.d("created_directory", "directory already exist");
            return false;
        }
    }

    public static void nomedia(String path){
        //File file = new File(path + "/.nomedia");
    }

    public static void delete(String path){
        File f = new File(path);
        deleteAll(f);
    }

    public static void deleteAll(File file){
        if(file.isDirectory()){
            //directory is empty, then delete it
            if(file.list().length==0){
                file.delete();
            }else{
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    deleteAll(fileDelete);
                }
                //check the directory again, if empty then delete it
                if(file.list().length==0){
                    file.delete();
                }
            }
        }else{
            //if file, then delete it
            file.delete();
        }
    }


}
