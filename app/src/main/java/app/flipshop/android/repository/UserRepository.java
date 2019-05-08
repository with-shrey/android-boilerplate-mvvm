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

package app.flipshop.android.repository;

import app.flipshop.android.AppExecutors;
import app.flipshop.android.api.ApiResponse;
import app.flipshop.android.api.WebService;
import app.flipshop.android.db.UserDao;
import app.flipshop.android.vo.Resource;
import app.flipshop.android.vo.User;
import io.reactivex.Flowable;
import io.reactivex.Observable;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles User objects.
 */
@Singleton
public class UserRepository {
    private final UserDao userDao;
    private final WebService WebService;
    private final AppExecutors appExecutors;

    @Inject
    UserRepository(AppExecutors appExecutors, UserDao userDao, WebService WebService) {
        this.userDao = userDao;
        this.WebService = WebService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<User>> loadUser(String login) {
        return new NetworkBoundResource<User,User>() {

            @Override
            protected void saveCallResult(@NonNull User resultType) {

            }

            @Override
            protected boolean shouldFetch() {
                return false;
            }

            @NonNull
            @Override
            protected Flowable<User> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected Observable<User> createCall() {
                return null;
            }
        }.asLiveData();
    }
}
