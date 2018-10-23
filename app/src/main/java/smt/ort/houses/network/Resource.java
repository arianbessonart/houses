package smt.ort.houses.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Resource<T> {

    public enum Status {
        SUCCESS, ERROR, LOADING
    }

    private final Status status;
    private final T data;
    private final Exception exception;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable Exception exception) {
        this.status = status;
        this.data = data;
        this.exception = exception;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Exception getException() {
        return exception;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Exception exception, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, exception);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }
}
