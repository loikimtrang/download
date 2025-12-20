package com.download.android.ui.main;

import androidx.lifecycle.MutableLiveData;

import com.download.android.MVVMApplication;
import com.download.android.data.Repository;
import com.download.android.ui.base.activity.BaseViewModel;

public class MainViewModel extends BaseViewModel {
    public MutableLiveData<String> downloadStatus = new MutableLiveData<>("Chưa tải");
    public MainViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
