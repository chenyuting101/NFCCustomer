package chenyuting.com.nfccustomer.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.NetTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import chenyuting.com.nfccustomer.tool.SharedPreferenceTool;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 3/27/18.
 */

public class AdvertisementService extends Service {
    private String TAG = "AdvertisementService";
    private Thread myThread;

    @Override
    public void onCreate() {
        super.onCreate();
//        myThread = new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                DataBaseTool dataBaseTool = new DataBaseTool();
//                NetTool netTool = new NetTool();
//                VideoTool videoTool = new VideoTool();
//                Video video;
//                if(dataBaseTool.getAllUploadVideos(VideoUploadService.this).size()>0){
//                    video = dataBaseTool.getAllUploadVideos(VideoUploadService.this).get(0);
//                    if(netTool.isNetworkAvailable(VideoUploadService.this)) videoTool.uploadVideo(video, VideoUploadService.this);
//                }
//            }
//        };
//        myThread.start();




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        myThread = new Thread(){
            @Override
            public void run() {
                super.run();
                DataBaseTool dataBaseTool = new DataBaseTool();
                NetTool netTool = new NetTool();
                //VideoTool videoTool = new VideoTool();
                //MediaStore.Video video;
                String result;
                while (true) {
                    String requsetBody = Parameter.K_ADVERTISEMENT_FUNCTION+"="+ Parameter.V_COUPON_FUNCTION_ASK_FOR_ADVERTISEMENT;
                    String read = SharedPreferenceTool.read(Parameter.K_SETTING_RECEIVE_ADVERTISEMENT,getBaseContext());
                    Log.w(TAG, "is read"+read);
                    if(read.equals(Parameter.V_TOGGLE_BUTTON_ON)) {
                        try {
                            result = netTool.postRequest(Parameter.ADVERTISEMENT_URL, requsetBody).trim();
                            if (result.equals("yes")) {
                                sendNotification();
                                Log.w(TAG, "send notification");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                        try {
                            this.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                }
            }
        };
        myThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.myThread.stop();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotification() {
        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.drawable.ic_launcher)
                //设置通知标题
                .setContentTitle("NFC Retailer")
                //设置通知内容
                .setContentText("New Advertisement!");
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        //.setWhen(System.currentTimeMillis());
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, builder.build());
    }
}
