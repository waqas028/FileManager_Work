package com.example.task_1.ui.activity;

import com.example.task_1.adapter.PremiumImagesAdapter;
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
public final class PurchasingActivity_MembersInjector implements MembersInjector<PurchasingActivity> {
  private final Provider<PremiumImagesAdapter> premiumImagesAdapterProvider;

  public PurchasingActivity_MembersInjector(
      Provider<PremiumImagesAdapter> premiumImagesAdapterProvider) {
    this.premiumImagesAdapterProvider = premiumImagesAdapterProvider;
  }

  public static MembersInjector<PurchasingActivity> create(
      Provider<PremiumImagesAdapter> premiumImagesAdapterProvider) {
    return new PurchasingActivity_MembersInjector(premiumImagesAdapterProvider);
  }

  @Override
  public void injectMembers(PurchasingActivity instance) {
    injectPremiumImagesAdapter(instance, premiumImagesAdapterProvider.get());
  }

  @InjectedFieldSignature("com.example.task_1.ui.activity.PurchasingActivity.premiumImagesAdapter")
  public static void injectPremiumImagesAdapter(PurchasingActivity instance,
      PremiumImagesAdapter premiumImagesAdapter) {
    instance.premiumImagesAdapter = premiumImagesAdapter;
  }
}
