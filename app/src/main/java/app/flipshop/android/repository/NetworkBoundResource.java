package app.flipshop.android.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import app.flipshop.android.model.Resource;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.HttpException;
import timber.log.Timber;

public abstract class NetworkBoundResource<RequestType, ResultType> {

    // result is a Flowable because Room Database only returns Flowables
    // Retrofit response will also be folded into the stream as a Flowable
    private Flowable<Resource<ResultType>> result;

    @MainThread
    NetworkBoundResource() {
        Flowable<Resource<ResultType>> source;
        if (shouldFetch()) {
            source = createCall()
                    .doOnNext(this::saveCallResult)
                    .flatMap(apiResponse -> loadFromDb().toObservable().map(Resource::success))
                    .doOnError(this::onFetchFailed)
                    .onErrorResumeNext(t -> {
                        return loadFromDb()
                                .toObservable()
                                .map(data -> {
                                    Resource resource;

                                    if (t instanceof HttpException && ((HttpException) t).code() >= 400 && ((HttpException) t).code() < 500) {
                                        resource = Resource.invalid(t.getMessage(), data);
                                    } else {
                                        resource = Resource.error(t.getMessage(), data);
                                    }

                                    return resource;
                                });
                    })
                    .toFlowable(BackpressureStrategy.LATEST);
        } else {
            source = loadFromDb()
                    .subscribeOn(Schedulers.io())
                    .map(Resource::success);
        }

        result = Flowable.concat(initLoadDb()
                        .map(Resource::loading)
                        .take(1),
                source)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Resource<ResultType>> asObservable() {
        return result.toObservable();
    }
    public LiveData<Resource<ResultType>> asLiveData(){
        return LiveDataReactiveStreams.fromPublisher(result);
    }

    @SuppressWarnings("WeakerAccess")
    protected void onFetchFailed(Throwable t) {
        Timber.e(t);
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType resultType);

    @MainThread
    protected abstract boolean shouldFetch();

    @NonNull
    @MainThread
    protected abstract Flowable<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract Observable<RequestType> createCall();

    @NonNull
    @MainThread
    protected Flowable<ResultType> initLoadDb() {
        return loadFromDb();
    }
}