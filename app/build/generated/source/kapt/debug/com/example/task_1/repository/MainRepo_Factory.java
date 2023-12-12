package com.example.task_1.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MainRepo_Factory implements Factory<MainRepo> {
  private final Provider<Context> contextProvider;

  public MainRepo_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MainRepo get() {
    return newInstance(contextProvider.get());
  }

  public static MainRepo_Factory create(Provider<Context> contextProvider) {
    return new MainRepo_Factory(contextProvider);
  }

  public static MainRepo newInstance(Context context) {
    return new MainRepo(context);
  }
}
