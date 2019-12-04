/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.flipshop.android.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import app.flipshop.android.api.WebService;
import app.flipshop.android.db.FlipshopDb;
import app.flipshop.android.db.UserDao;
import app.flipshop.android.util.LiveDataCallAdapterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.flipshop.android.util.SharedPreferencesUtil;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.flipshop.android.util.Constants.BASE_API_URL;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Provides
    @Singleton
    @NonNull
    WebService provideWebService() {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        //add retro builder
        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

//        retroBuilder.client(httpClient.build());

        //create retrofit - only this instance would be used in the entire application
        Retrofit retrofit = retroBuilder.build();
        return retrofit.create(WebService.class);
    }
    @Singleton @Provides
    FlipshopDb provideDb(Application app) {
        return Room.databaseBuilder(app, FlipshopDb.class,"app.flipshop.android.db").build();
    }


    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Singleton @Provides
    UserDao provideUserDao(FlipshopDb db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    SharedPreferencesUtil provideSharedPreferencesUtil(Context context) {
        return new SharedPreferencesUtil(context);
    }

//    @Singleton @Provides
//    RepoDao provideRepoDao(FlipshopDb db) {
//        return db.repoDao();
//    }
}
