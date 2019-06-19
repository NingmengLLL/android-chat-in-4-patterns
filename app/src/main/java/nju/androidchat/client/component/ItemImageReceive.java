package nju.androidchat.client.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import nju.androidchat.client.R;

public class ItemImageReceive extends LinearLayout {

    private ImageView imageView;
    private Context context;
    private UUID messageId;

    public ItemImageReceive(Context context,UUID messageId,String url) {
        super(context);
        this.context = context;
        this.messageId = messageId;

        inflate(context, R.layout.item_image_receive, this);
        this.imageView = findViewById(R.id.chat_item_content_image);

        try {
            InputStream inputStream = getImageViewInputStream(url);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  InputStream getImageViewInputStream(String str) throws IOException {
        InputStream inputStream = null;
        URL url = new URL(str);                    //服务器地址
        if (url != null) {
            //打开连接
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();

            httpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
                public boolean verify(String string, SSLSession ssls) {
                    return true;
                }
            });
            httpsURLConnection.setConnectTimeout(3000);//设置网络连接超时的时间为3秒
            httpsURLConnection.setRequestMethod("GET");        //设置请求方法为GET
            httpsURLConnection.setDoInput(true);                //打开输入流
            int responseCode = httpsURLConnection.getResponseCode();    // 获取服务器响应值
            if (responseCode == HttpURLConnection.HTTP_OK) {        //正常连接
                inputStream = httpsURLConnection.getInputStream();        //获取输入流
            }
        }
        return inputStream;
    }


}
