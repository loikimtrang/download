package com.download.android.data;

import com.download.android.data.local.prefs.PreferencesService;
import com.download.android.data.local.room.RoomService;
import com.download.android.data.remote.ApiService;


public interface Repository {

    /**
     * ################################## Preference section ##################################
     */
    String getToken();
    void setToken(String token);

    PreferencesService getSharedPreferences();


    /**
     *  ################################## Remote api ##################################
     */
    ApiService getApiService();

    RoomService getRoomService();

}
