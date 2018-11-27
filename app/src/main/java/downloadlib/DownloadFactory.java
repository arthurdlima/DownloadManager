package downloadlib;

import java.net.HttpURLConnection;
import java.net.URL;

public final class DownloadFactory {

    //FACTORY METHOD DESIGN PATTERN

    /* Utilizado para resolver problemas de "race conditions" em que multiplas instancias
       da classe seriam criadas. É uma implementação "thread-safe".
    */

    //SINGLTETON DESIGN PATTERN
    private static volatile DownloadFactory singletonFactory = null;

    public DownloadFactory(){}

    public static DownloadFactory getSingletonFactory(){
        if(singletonFactory == null){
            synchronized (DownloadFactory.class){
                if(singletonFactory == null) {
                    singletonFactory = new DownloadFactory();
                }
            }
        }
        return singletonFactory;
    }

     public DownloadMng makeurlobject(String url,String type){


         String extension = "";
         int i = url.lastIndexOf(".");
         if(i>0){
             //Getting the url extension
             extension = url.substring(i+1);

             if(extension.equals("jpg") || extension.equals("png") && type.equals("image") ){
                 return new ImageDownload();
             }

             if(type.equals("audio")){
                return new AudioDownload();
             }

         }

        return null;
    }

}
