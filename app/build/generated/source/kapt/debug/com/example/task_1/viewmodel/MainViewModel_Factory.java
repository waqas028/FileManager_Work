package com.example.task_1.viewmodel;

import com.example.task_1.repository.MainRepo;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<MainRepo> mainRepoProvider;

  public MainViewModel_Factory(Provider<MainRepo> mainRepoProvider) {
    this.mainRepoProvider = mainRepoProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(mainRepoProvider.get());
  }

  public static MainViewModel_Factory create(Provider<MainRepo> mainRepoProvider) {
    return new MainViewModel_Factory(mainRepoProvider);
  }

  public static MainViewModel newInstance(MainRepo mainRepo) {
    return new MainViewModel(mainRepo);
  }
}
