package rx1310.optinova.ota;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.loveplusplus.update.AppUtils;
import com.loveplusplus.update.UpdateChecker;
import rx1310.optinova.ota.MainActivity;

public class MainActivity extends Activity
 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);

        btn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UpdateChecker.checkForDialog(MainActivity.this);
				}
			});
			
        btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UpdateChecker.checkForNotification(MainActivity.this);
				}
			});


        TextView textView = findViewById(R.id.textView1);

        textView.setText("v: versionName = " + AppUtils.getVersionName(this) + " versionCode = " + AppUtils.getVersionCode(this));
    }

}
