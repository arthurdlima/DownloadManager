package downloadlib.threadtypes;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class DownloadThreadPool {

    /*Since I'm fetching data from server, will use fixed pool size with a large number.
    any thread not being handled will be in "waiting" state */

    private ExecutorService service = Executors.newFixedThreadPool(50);

    //SINGLTETON DESIGN PATTERN
    private static volatile DownloadThreadPool singletonthreadpool = null;

    public DownloadThreadPool(){}

    public static DownloadThreadPool getSingletonThreadPool(){
        if(singletonthreadpool == null){
            synchronized (DownloadThreadPool.class){
                if(singletonthreadpool == null) {
                    singletonthreadpool = new DownloadThreadPool();
                }
            }
        }
        return singletonthreadpool;
    }

    public void useService(String url,String type){
        //type = image, audio or video
        if(type.equals("image")){
            service.execute(new DownloadImageThread(url));
        }

        if(type.equals("audio")){
            service.execute(new DownloadAudioThread(url));
        }


    }

}
