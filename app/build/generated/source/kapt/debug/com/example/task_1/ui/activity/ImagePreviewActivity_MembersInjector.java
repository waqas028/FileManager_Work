package com.example.task_1.ui.activity;

import com.example.task_1.adapter.SlidePhotoAdapter;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ImagePreviewActivity_MembersInjector implements MembersInjector<ImagePreviewActivity> {
  private final Provider<SlidePhotoAdapter> slidePhotoAdapterProvider;

  public ImagePreviewActivity_MembersInjector(
      Provider<SlidePhotoAdapter> slidePhotoAdapterProvider) {
    this.slidePhotoAdapterProvider = slidePhotoAdapterProvider;
  }

  public static MembersInjector<ImagePreviewActivity> create(
      Provider<SlidePhotoAdapter> slidePhotoAdapterProvider) {
    return new ImagePreviewActivity_MembersInjector(slidePhotoAdapterProvider);
  }

  @Override
  public void injectMembers(ImagePreviewActivity instance) {
    injectSlidePhotoAdapter(instance, slidePhotoAdapterProvider.get());
  }

  @InjectedFieldSignature("com.example.task_1.ui.activity.ImagePreviewActivity.slidePhotoAdapter")
  public static void injectSlidePhotoAdapter(ImagePreviewActivity instance,
      SlidePhotoAdapter slidePhotoAdapter) {
    instance.slidePhotoAdapter = slidePhotoAdapter;
  }
}
