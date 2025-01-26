package net.htlgkr.lugern.coctracker.api;

public interface HTTPListener<T> {
    void onSuccess(T a);

    void onError(String error);
}
