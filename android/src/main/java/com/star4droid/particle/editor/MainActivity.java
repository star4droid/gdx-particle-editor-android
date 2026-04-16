package com.star4droid.particle.editor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.ray3k.gdxparticleeditor.Core;

public class MainActivity extends AndroidApplication {
  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    if (Build.VERSION.SDK_INT >= 23) {
      if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
              == PackageManager.PERMISSION_DENIED
          || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
              == PackageManager.PERMISSION_DENIED) {
        requestPermissions(
            new String[] {
              Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            },
            1000);
      }
    }
    AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
    initialize(new Core(), configuration);
  }
}
