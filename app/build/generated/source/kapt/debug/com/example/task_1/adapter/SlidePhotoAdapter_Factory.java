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
public final class SlidePhotoAdapter_Factory implements Factory<SlidePhotoAdapter> {
  @Override
  public SlidePhotoAdapter get() {
    return newInstance();
  }

  public static SlidePhotoAdapter_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SlidePhotoAdapter newInstance() {
    return new SlidePhotoAdapter();
  }

  private static final class InstanceHolder {
    private static final SlidePhotoAdapter_Factory INSTANCE = new SlidePhotoAdapter_Factory();
  }
}
