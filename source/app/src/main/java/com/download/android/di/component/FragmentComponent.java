package com.download.android.di.component;


import com.download.android.di.module.FragmentModule;
import com.download.android.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {

}
