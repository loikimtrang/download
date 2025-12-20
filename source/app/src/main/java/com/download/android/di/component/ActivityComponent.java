package com.download.android.di.component;

import com.download.android.di.module.ActivityModule;
import com.download.android.di.scope.ActivityScope;
import com.download.android.ui.main.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}

