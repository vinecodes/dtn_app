package me.iologic.apps.dtn;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vinee on 16-01-2018.
 */

public class FileServices  {

    private File file;
    private FileOutputStream outputStream;
    private Context ctx;

    String fileContent;

    public FileServices(Context context)
    {
        ctx = context;
    }
    public File createTempFile(String ReceivedFileName) {

        try {
            String fileName = ReceivedFileName;
            file = File.createTempFile(fileName, null, ctx.getFilesDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    public void fillTempFile(String fileName)
    {
        try {
            outputStream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(createString(1024 * 1024).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readTempFile(String fileName){
        String ret = "";

        try {
            InputStream inputStream = ctx.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(Constants.TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(Constants.TAG, "Can not read file: " + e.toString());
        }

        fileContent = ret;

        return ret;
    }

    public float getFileSize(){
        return fileContent.length();
    }

    static String createString(long size){
        StringBuilder o=new StringBuilder();
        for(int i=0;i<size;i++){
            o.append("f");
        }
        return o.toString();
    }
}
