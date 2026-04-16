package com.ray3k.gdxparticleeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.ray3k.stripe.PopTable;

/**
 * A convenience class that provides static methods to open OS native file
 * dialogs.
 */
public class FileDialogs {

    public static String savePath = null;
    public static String[] selectedFiles = null;
    //public static ActivityResultLauncher<String[]> pickOne,pickMulti;
    //public static ActivityResultLauncher save;

    public static Array<FileHandle> openMultipleDialog(
            String title, String defaultPath, String[] filterPatterns, String filterDescription) {
        /*
	  selectedFiles = null;

	  new Handler(Looper.getMainLooper()).post(()->{

	  });
	  while(selectedFiles==null){}
	  Array<FileHandle> array = new Array<>();
	  if(selectedFiles == null) return null;
	  for(String result:selectedFiles){
	  	FileHandle fileHandle = Gdx.files.absolute(result);
		  array.add(fileHandle);
		}
	  return array;*/
        return null;
    }

    public static FileHandle openDialog(
            String title, String defaultPath, String[] filterPatterns, String filterDescription) {
        /*
		selectedFiles = null;

	  new Handler(Looper.getMainLooper()).post(()->{

	  });
	  while(selectedFiles==null){}
	  Array<FileHandle> array = new Array<>();
	  if(selectedFiles == null) return null;
	  for(String result:selectedFiles){
	  	FileHandle fileHandle = Gdx.files.absolute(result);
		  array.add(fileHandle);
		}
	  return array.size > 0 ? array.get(0) : null;
         */
        return null;
    }

    public static FileHandle savedFile;

    public static FileHandle saveDialog(
            String title,
            String defaultPath,
            String defaultName,
            String[] filterPatterns,
            String filterDescription) {
        try {
            Object context = Gdx.app.getClass().getMethod("getContext").invoke(Gdx.app);
            java.io.File file = (java.io.File) context.getClass().getMethod("getExternalFilesDir", String.class).invoke(context, "save");
            savedFile = Gdx.files.absolute(file.toString().concat("/").concat(defaultName));
        } catch (Exception e) {
            e.printStackTrace();
            savedFile = Gdx.files.absolute(Gdx.files.getExternalStoragePath() + "/save/" + defaultName);
        }
        savedFile.writeString("", false);
        //Utils.saveFile(defaultName,save);
        return savedFile;
    }

}
