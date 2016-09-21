package com.fyj.fastbee.utils;

import com.fyj.fastbee.bean.base.BaseArrayBean;
import com.fyj.fastbee.bean.base.BaseObjectBean;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseObjectBean<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<BaseObjectBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseObjectBean<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<BaseObjectBean<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseObjectBean<T> tHttpResponse) {

                        if (tHttpResponse == null) {
                            return Observable.error(new Exception("网络故障"));
                        }

                        if (tHttpResponse.getStatus() == 10001) {
                            return createData(tHttpResponse.getData());
                        } else {
                            return Observable.error(new Exception(tHttpResponse.getMsg()));
                        }

                    }
                });
            }
        };
    }

//    /**
//     * 统一返回结果处理
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<BaseArrayBean<T>, T> handleWXResult() {   //compose判断结果
//        return new Observable.Transformer<BaseArrayBean<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<BaseArrayBean<T>> httpResponseObservable) {
//                return httpResponseObservable.flatMap(new Func1<BaseArrayBean<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(BaseArrayBean<T> tHttpResponse) {
//                        if (tHttpResponse == null) {
//                            return Observable.error(new Exception("网络故障"));
//                        }
//
//                        if (tHttpResponse.getStatus() == 10001) {
//                            return createData(tHttpResponse.getData());
//                        } else {
//                            return Observable.error(new Exception(tHttpResponse.getMsg()));
//                        }
//                    }
//                });
//            }
//        };
//    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
