package com.example.task_1.adapter;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class PremiumImagesAdapter_Factory implements Factory<PremiumImagesAdapter> {
  @Override
  public PremiumImagesAdapter get() {
    return newInstance();
  }

  public static PremiumImagesAdapter_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PremiumImagesAdapter newInstance() {
    return new PremiumImagesAdapter();
  }

  private static final class InstanceHolder {
    private static final PremiumImagesAdapter_Factory INSTANCE = new PremiumImagesAdapter_Factory();
  }
}
