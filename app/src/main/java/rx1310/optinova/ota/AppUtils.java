package rx1310.optinova.ota;

import android.content.Context;
import android.content.pm.PackageManager;

public class AppUtils {

    public static int getVersionCode(Context context) {
		
        if (context != null) {
			
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException ignored) { }
			
        }
		
        return 0;
		
    }

    public static String getVersionName(Context context) {
		
        if (context != null) {
			
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException ignored) { }
			
        }

        return "";
		
    }
	
}
