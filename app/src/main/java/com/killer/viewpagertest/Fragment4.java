package com.killer.viewpagertest;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**加载就自动查询联系人信息并输入出LOG的INFO中，点击按钮取回网络上的图片并更新UI显示
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {

    //定义一个权限返回值
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private ImageView showImage;
    private Button getWebImageButton;
    private Bitmap bitmap;



    public Fragment4() {
        // Required empty public constructor
    }

    /*
    获取网络图片要用异步方式通过handler取得,这样才能刷新UI界面元素
     */
    private Handler handler = new Handler(){

        public void handleMessage(Message msg){
            bitmap = (Bitmap)msg.obj;
            showImage.setImageBitmap(bitmap);
        }
    };




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view4,container,false);
        showImage = (ImageView) view.findViewById(R.id.view4_imageView);
        getWebImageButton = (Button) view.findViewById(R.id.view4_button);

        getWebImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 主线程不能直接访问网络，因此要获得需要用新线程来取得图片
                new Thread(){
                    @Override
                    public void run() {
                        InputStream is = null;
                        try {
                            URL url = new URL("http://mmbiz.qpic.cn/mmbiz/1xMIv8n9iaObe1avOI0zU3AibwcJZMl06fEEgvnP5SIhuGagGhictRicjEGXDZwWncMB99ETeUiaSQOmaBoRI5t52JA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setConnectTimeout(5000);
                            connection.setRequestMethod("GET");
                            if(connection.getResponseCode() == 200) {
                                is = connection.getInputStream();
                                bitmap = BitmapFactory.decodeStream(is);
                                //Message.obtain();该方法返回一个消息对象，如果有消息就复用消息池里的消息，如果没有消息就创建一个新的消息
                                Message msg = Message.obtain();
                                //msg.obg可以携带参数
                                msg.obj = bitmap;
                                // 当sendMessage执行时，handler就会运行
                                handler.sendMessage(msg);


                            }

                            connection.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if(is != null) {
                                try {
                                    is.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }.start();

            }
        });


        // 通过ContentResolver查询联系人内容
        ContentResolver resolver = getContext().getContentResolver();
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }else {

            // 查询ID
            Cursor cursor = resolver.query(Contacts.CONTENT_URI, new String[]{Contacts._ID}, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    Log.i("info", "id:" + id);
                    // 根据ID查询电话号码和电话类型
                    Cursor cursor1 = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                    if (cursor1 != null) {
                        while (cursor1.moveToNext()) {
                            int type = cursor1.getInt(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            if (type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME) { // 输出家庭号码
                                Log.i("info", cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                            } else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) { // 输出手机号码
                                Log.i("info", cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                            }
                            // 输出联系人姓名
                            Log.i("info",cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                        }
                        cursor1.close();
                    }

                    // 根据ID查询EMAIL
                    Cursor c2 = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Email.CONTACT_ID},
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id,
                            null,null);

                    if(c2 != null){
                        while (c2.moveToNext()){
                            // 输出Email，ID号

                            Log.i("info",c2.getInt(c2.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))+ "");
                            Log.i("info",c2.getString(c2.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));

                        }
                        c2.close();
                    }
                }
                cursor.close();
            }
        }

        return view;
    }

}
