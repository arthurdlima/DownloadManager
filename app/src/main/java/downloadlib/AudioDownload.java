package downloadlib;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import downloadlib.threadtypes.DownloadThreadPool;

public class AudioDownload extends DownloadMng {

    @Override
    public boolean downloadRequest(String url) {

        URL downloadURL = null;
        InputStream inputStream = null;
        HttpURLConnection connect = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        String fieldValue = null;
        try {
            downloadURL = new URL(url);
            connect = (HttpURLConnection) downloadURL.openConnection();

            //Getting the actual audio file name
            fieldValue = connect.getHeaderField("Content-Disposition");
            String filename = fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1);


            inputStream = connect.getInputStream();

            //Creating folder and verifying if image exists
            try {
                //used try catch to handle returned null (if Audio was found in storage) throws error
                file = new File(createFile(url,filename).toString());
            }catch (Exception e){
                //Audio exists, exist method
                Log.i("AudioStatus","Audio in storage!");
                return false;
            }


            fileOutputStream = new FileOutputStream(file);

            int readInput = -1;
            byte[] buffer = new byte[1024];
            while((readInput=inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer,0,readInput);

            }
            Log.i("AudioStatus","Audio downloaded!" +readInput);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Using finally to close the connections
        finally {
            if (connect!=null){
                connect.disconnect();
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    @Override
    public void downloadStartThread(String url) {

        DownloadThreadPool threadpool = new DownloadThreadPool();
        threadpool.getSingletonThreadPool();
        threadpool.useService(url,"audio");
    }

    @Override
    public File createFile(String urlForParse,String name){
        //Path example: /storage/sdcard0/DownloaderFBV

        //Seeing if audio file has name or not
        String fileName = null;
        if(name.equals(null)){
            fileName = Uri.parse(urlForParse).getLastPathSegment();
        }else{
            fileName = name;
        }
        String directory = Environment.getExternalStorageDirectory().toString();
        //Folder FBV
        File folderDir = new File(directory+"/DownloaderFBV");
        //Folder audio
        File folderAudio = new File(directory+"/DownloaderFBV/audios");
        //Folders + file
        File folderAndFile = new File(directory+"/DownloaderFBV/audios/"+fileName);


        if(!folderDir.isDirectory()){
            //if DownloaderFBV directory doesn't exist, create and return complete path
            folderAudio.mkdirs();
            return folderAndFile;
        }else if (!folderAudio.isDirectory()){
            //DownloaderFBV exists. If audio directory  doesn't exist, create. Return complete path
            folderAudio.mkdir();
            return folderAndFile;
        }else if(!folderAndFile.exists()){
            return folderAndFile;
        }else{
            return null;
        }
        //Toast.makeText(getApplicationContext(),file.getAbsolutePath(),Toast.LENGTH_LONG).show();
    }
}