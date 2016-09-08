package com.killer.viewpagertest;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment implements View.OnClickListener{

    private Button send;
    private Button cancel;
    private NotificationManager notificationManager;
    private int NOTIFICATION_ID = 1;// 通知窗口的ID

    private ImageView star;
    private Button starOn;
    private Button starOff;
    private ImageView transitionStar;
    private TransitionDrawable drawable;
    private ImageView cricleImage;



    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view1,container,false);
        send = (Button) view.findViewById(R.id.view1_send);
        cancel = (Button) view.findViewById(R.id.view1_cancel);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);
        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);


        // 使用Level-list来显示图片的切换
        star = (ImageView) view.findViewById(R.id.view1_imageV);
        star.setImageLevel(7);
        transitionStar = (ImageView) view.findViewById(R.id.view1_imageV2);

        // 设置圆形图片显示
        cricleImage = (ImageView) view.findViewById(R.id.view1_imageV3);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dialog);
        cricleImage.setImageDrawable(new CicrleDrawable(bitmap));
        // 下面是圆角矩形显示图形
//      cricleImage.setImageDrawable(new RoundDrawable(bitmap));



        starOn = (Button) view.findViewById(R.id.view1_starOn_button);
        starOff = (Button) view.findViewById(R.id.view1_starOff_button);

        starOn.setOnClickListener(this);
        starOff.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.view1_send:
                sendNotification();
                break;
            case R.id.view1_cancel:
                notificationManager.cancel(NOTIFICATION_ID);
                break;

            case R.id.view1_starOn_button:
                star.setImageLevel(11);
                drawable = (TransitionDrawable) transitionStar.getDrawable();
                drawable.startTransition(3000);
                break;
            case R.id.view1_starOff_button:
                star.setImageLevel(7);
                drawable = (TransitionDrawable) transitionStar.getDrawable();
                drawable.reverseTransition(3000);

                break;
        }


    }

    /**
     * 构造notification并发送到通知栏
     */
    private void sendNotification() {
        Intent intent = new Intent(getContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0, intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
        builder.setSmallIcon(android.R.drawable.ic_btn_speak_now);
        builder.setTicker("this a ticker by notification");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("通知栏通知");
        builder.setContentText("此通知来自" + getContext().getPackageName());
        builder.setContentIntent(pendingIntent);// 点击后的意图
//        builder.setDefaults(Notification.DEFAULT_SOUND);// 设置提示声音
//        builder.setDefaults(Notification.DEFAULT_LIGHTS);// 设置指示灯
//        builder.setDefaults(Notification.DEFAULT_VIBRATE);// 设置震动
        builder.setDefaults(Notification.DEFAULT_ALL);// 上面全部设置
        Notification notification = builder.build();

        notificationManager.notify(NOTIFICATION_ID,notification);




    }
}
