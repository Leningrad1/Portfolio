// Generated by Dagger (https://dagger.dev).
package com.example.headhunterapp.data.repository;

import com.example.headhunterapp.data.db.UserDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class Repository_Factory implements Factory<Repository> {
  private final Provider<UserDao> userDaoProvider;

  public Repository_Factory(Provider<UserDao> userDaoProvider) {
    this.userDaoProvider = userDaoProvider;
  }

  @Override
  public Repository get() {
    return newInstance(userDaoProvider.get());
  }

  public static Repository_Factory create(Provider<UserDao> userDaoProvider) {
    return new Repository_Factory(userDaoProvider);
  }

  public static Repository newInstance(UserDao userDao) {
    return new Repository(userDao);
  }
}
