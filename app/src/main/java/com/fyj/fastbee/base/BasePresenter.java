package com.fyj.fastbee.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Fyj on 2016/8/23.
 */
public abstract class BasePresenter<V, M> {

    protected CompositeSubscription mCompositeSubscription;

    public V mView;
    public M mModel;

    public void setVM(V view, M model) {
        this.mView = view;
        this.mModel = model;
    }

    public abstract void onStart();

    protected void onDestory(){
        unSubscribe();
    };

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
}
