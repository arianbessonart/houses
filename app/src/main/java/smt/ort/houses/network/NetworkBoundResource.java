package smt.ort.houses.network;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private MediatorLiveData<ResultType> result = new MediatorLiveData<>();

    public NetworkBoundResource() {
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource(dbSource);
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            setValue(newData);
                        }
                    });
                }
            }
        });
    }

    private void setValue(ResultType newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }


    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
                setValue(newData);
            }
        });
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> response) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                if (response.isSuccessful()) {
                    saveResultAndReInit(response);
                } else {
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            Log.e("ERROR NETWORK", "" + response.getError());
                            result.setValue(newData);
                        }
                    });
                }
            }

        });
    }

    @SuppressLint("StaticFieldLeak")
    private void saveResultAndReInit(final ApiResponse<RequestType> response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response.body);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType newData) {
                        result.setValue(newData);
                    }
                });
            }
        }.execute();
    }

    protected abstract void saveCallResult(@NonNull RequestType item);

    protected abstract boolean shouldFetch(@NonNull ResultType data);

    protected abstract LiveData<ResultType> loadFromDb();

    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    protected void onFetchFailed() {
    }

    public final LiveData<ResultType> getAsLiveData() {
        return result;
    }

}
