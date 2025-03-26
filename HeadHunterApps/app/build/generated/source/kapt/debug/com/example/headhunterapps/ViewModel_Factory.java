// Generated by Dagger (https://dagger.dev).
package com.example.headhunterapps;

import com.example.headhunterapp.data.db.UserDao;
import com.example.headhunterapp.data.repository.Repository;
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
public final class ViewModel_Factory implements Factory<ViewModel> {
  private final Provider<UserDao> myDaoProvider;

  private final Provider<Repository> repositoryProvider;

  public ViewModel_Factory(Provider<UserDao> myDaoProvider,
      Provider<Repository> repositoryProvider) {
    this.myDaoProvider = myDaoProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ViewModel get() {
    return newInstance(myDaoProvider.get(), repositoryProvider.get());
  }

  public static ViewModel_Factory create(Provider<UserDao> myDaoProvider,
      Provider<Repository> repositoryProvider) {
    return new ViewModel_Factory(myDaoProvider, repositoryProvider);
  }

  public static ViewModel newInstance(UserDao myDao, Repository repository) {
    return new ViewModel(myDao, repository);
  }
}
