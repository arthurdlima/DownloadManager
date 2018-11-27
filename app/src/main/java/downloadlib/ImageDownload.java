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
import java.util.concurrent.Executor;

import downloadlib.threadtypes.DownloadThreadPool;

public class ImageDownload extends DownloadMng {

    @Override
     public boolean downloadRequest(String url) {

        URL downloadURL = null;
        InputStream inputStream = null;
        HttpURLConnection connect = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            downloadURL = new URL(url);
            connect = (HttpURLConnection) downloadURL.openConnection();
            inputStream = connect.getInputStream();

            //Creating folder and verifying if image exists
            try {
                //used try catch to handle returned null (if image was found in storage) throws error
                file = new File(createFile(url,null).toString());
            }catch (Exception e){
                //image exists, exist method
                Log.i("ImageStatus","Image in storage!");
                return false;
            }


            fileOutputStream = new FileOutputStream(file);

            int readInput = -1;
            byte[] buffer = new byte[1024];
            while((readInput=inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer,0,readInput);
            //Log.i("ImageStatus","buffer working!" +readInput);
            }
            Log.i("ImageStatus","Image downloaded!" +readInput);
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
        //Thread imgThread = new Thread(new DownloadImageThread(url));
        //imgThread.start();
        DownloadThreadPool threadpool = new DownloadThreadPool();
        threadpool.getSingletonThreadPool();
        threadpool.useService(url,"image");
    }

    @Override
    public File createFile(String urlForParse,String name){
        //Path example: /storage/sdcard0/DownloaderFBV
        String fileName = Uri.parse(urlForParse).getLastPathSegment();
        String directory = Environment.getExternalStorageDirectory().toString();
        //Folder FBV
        File folderDir = new File(directory+"/DownloaderFBV");
        //Folder pictures
        File folderImages = new File(directory+"/DownloaderFBV/images");
        //Folders + file
        File folderAndFile = new File(directory+"/DownloaderFBV/images/"+fileName);

        if(!folderDir.isDirectory()){
            //if DownloaderFBV directory doesn't exist, create and return complete path
            folderImages.mkdirs();
            return folderAndFile;
        }else if (!folderImages.isDirectory()){
            //DownloaderFBV exists. If images directory  doesn't exist, create. Return complete path
            folderImages.mkdir();
            return folderAndFile;
        }else if(!folderAndFile.exists()){
            return folderAndFile;
        }else{
            return null;
        }
        //Toast.makeText(getApplicationContext(),file.getAbsolutePath(),Toast.LENGTH_LONG).show();
    }

}
