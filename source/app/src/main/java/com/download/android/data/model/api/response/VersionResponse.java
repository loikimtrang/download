package com.download.android.data.model.api.response;

import lombok.Data;

@Data
public class VersionResponse {
    private String currentVersion;
    private String minimumVersion;
    private String urlApk;
}
