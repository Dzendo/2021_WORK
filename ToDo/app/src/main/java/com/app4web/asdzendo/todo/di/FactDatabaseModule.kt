/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app4web.asdzendo.todo.di
/*
import android.content.Context
import com.app4web.asdzendo.todo.database.FactDatabase
import com.app4web.asdzendo.todo.database.FactDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
//import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
//@InstallIn(ActivityComponent::class)
//@InstallIn(ApplicationComponent::class)
@Module
class FactDatabaseModule {

    @Singleton
    @Provides
    fun provideFactDatabase(@ApplicationContext context: Context): FactDatabase {
        return FactDatabase.getInstance(context)
    }

    @Provides
    fun provideFactDatabaseDao(factDatabase: FactDatabase): FactDatabaseDao {
        return factDatabase.factDatabaseDao()
    }
}
*/