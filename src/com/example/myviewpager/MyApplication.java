package com.example.myviewpager;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(mailTo = "yarus7777@gmail.com",
formKey = "",
mode = ReportingInteractionMode.TOAST,
forceCloseDialogAfterToast = false,
resToastText = R.string.crash_toast_text)

public class MyApplication extends Application {

	@Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
	}
}
