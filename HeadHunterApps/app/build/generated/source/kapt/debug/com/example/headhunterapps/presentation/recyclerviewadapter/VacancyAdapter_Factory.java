// Generated by Dagger (https://dagger.dev).
package com.example.headhunterapps.presentation.recyclerviewadapter;

import android.content.Context;
import com.example.headhunterapp.model.Vacancy;
import com.example.headhunterapps.ViewModel;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.List;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.reflect.KFunction;

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
public final class VacancyAdapter_Factory implements Factory<VacancyAdapter> {
  private final Provider<Context> contextProvider;

  private final Provider<List<Vacancy>> vacanciesProvider;

  private final Provider<KFunction<Unit>> onClickListenerProvider;

  private final Provider<ViewModel> viewModelProvider;

  public VacancyAdapter_Factory(Provider<Context> contextProvider,
      Provider<List<Vacancy>> vacanciesProvider, Provider<KFunction<Unit>> onClickListenerProvider,
      Provider<ViewModel> viewModelProvider) {
    this.contextProvider = contextProvider;
    this.vacanciesProvider = vacanciesProvider;
    this.onClickListenerProvider = onClickListenerProvider;
    this.viewModelProvider = viewModelProvider;
  }

  @Override
  public VacancyAdapter get() {
    return newInstance(contextProvider.get(), vacanciesProvider.get(), onClickListenerProvider.get(), viewModelProvider.get());
  }

  public static VacancyAdapter_Factory create(Provider<Context> contextProvider,
      Provider<List<Vacancy>> vacanciesProvider, Provider<KFunction<Unit>> onClickListenerProvider,
      Provider<ViewModel> viewModelProvider) {
    return new VacancyAdapter_Factory(contextProvider, vacanciesProvider, onClickListenerProvider, viewModelProvider);
  }

  public static VacancyAdapter newInstance(Context context, List<Vacancy> vacancies,
      KFunction<Unit> onClickListener, ViewModel viewModel) {
    return new VacancyAdapter(context, vacancies, onClickListener, viewModel);
  }
}
