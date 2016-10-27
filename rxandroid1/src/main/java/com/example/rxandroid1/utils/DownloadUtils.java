package com.example.rxandroid1.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yfx on 16/10/23.
 */
public class DownloadUtils {

    private OkHttpClient client;

    public DownloadUtils() {
        this.client = new OkHttpClient();
    }

    public Observable<byte[]> downloadImg(final String path) {

        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(path).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                byte[] bytes = response.body().bytes();
                                if (bytes != null) {
                                    subscriber.onNext(bytes);
                                }
                                subscriber.onCompleted();
                            }
                        }
                    });
                }
            }
        });
    }
}
