package com.example.rxandroid1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.rxandroid1.utils.DownloadUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imgView;
    private String path="http://pic33.nipic.com/20130916/3420027_192919547000_2.jpg";
    private DownloadUtils downloadUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadUtils=new DownloadUtils();
        button = (Button) findViewById(R.id.button);
        imgView = (ImageView) findViewById(R.id.imgview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadUtils.downloadImg(path).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imgView.setImageBitmap(bitmap);

                    }
                });
            }
        });
    }
}
