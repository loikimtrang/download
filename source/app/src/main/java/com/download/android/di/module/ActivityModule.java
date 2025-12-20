package com.download.android.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.download.android.MVVMApplication;
import com.download.android.ViewModelProviderFactory;
import com.download.android.data.Repository;
import com.download.android.di.scope.ActivityScope;
import com.download.android.ui.base.activity.BaseActivity;
import com.download.android.ui.main.MainViewModel;
import com.download.android.utils.GetInfo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }

    @Named("access_token")
    @Provides
    @ActivityScope
    String provideToken(Repository repository){
        return repository.getToken();
    }

    @Named("device_id")
    @Provides
    @ActivityScope
    String provideDeviceId( Context applicationContext){
        return GetInfo.getAll(applicationContext);
    }


    @Provides
    @ActivityScope
    MainViewModel provideMainViewModel(Repository repository, Context application) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }


}
