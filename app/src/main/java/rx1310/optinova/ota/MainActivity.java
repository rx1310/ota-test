package rx1310.optinova.ota;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import rx1310.optinova.ota.Constants;
import rx1310.optinova.ota.R;

public class MainActivity extends Activity
 {
	Intent mIntent;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mIntent = getIntent();
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
		String mProjectAssetFile = mIntent.getStringExtra("PROJECT_ASSET_FILE");
		TextView tv = findViewById(R.id.mainTextView1);
		tv.setText(mProjectAssetFile);
        btn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new CheckUpdateTask(MainActivity.this, Constants.TYPE_DIALOG, true).execute();
				}
			});
			
        btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UpdateChecker.checkForNotification(MainActivity.this);
				}
			});

		//new CheckUpdateTask(this, Constants.TYPE_DIALOG, true).execute();
        TextView textView = findViewById(R.id.textView1);

        textView.setText("v: versionName = " + AppUtils.getVersionName(this) + " versionCode = " + AppUtils.getVersionCode(this));
    }
	
	public static void startWizard(Context context, String assetFileName) {

		Intent i = new Intent(context, MainActivity.class);
		i.putExtra("PROJECT_ASSET_FILE", assetFileName);
		context.startActivity(i);

	} 

	class CheckUpdateTask extends AsyncTask<Void, Void, String> {

		private ProgressDialog dialog;
		private Context mContext;
		private int mType;
		private boolean mShowProgressDialog;
		private static final String url = Constants.UPDATE_URL;

		CheckUpdateTask(Context context, int type, boolean showProgressDialog) {

			this.mContext = context;
			this.mType = type;
			this.mShowProgressDialog = showProgressDialog;

		}


		protected void onPreExecute() {

			if (mShowProgressDialog) {

				dialog = new ProgressDialog(mContext);
				dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
				dialog.show();

			}

		}


		@Override
		protected void onPostExecute(String result) {

			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}

			if (!TextUtils.isEmpty(result)) {
				parseJson(result);
			}

		}

		private void parseJson(String result) {

			try {

				JSONObject obj = new JSONObject(result);
				String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
				String apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL);
				int apkCode = obj.getInt(Constants.APK_VERSION_CODE);

				int versionCode = AppUtils.getVersionCode(mContext);

				if (apkCode > versionCode) {

					if (mType == Constants.TYPE_NOTIFICATION) {
						new NotificationHelper(mContext).showNotification(updateMessage, apkUrl);
					} else if (mType == Constants.TYPE_DIALOG) {
						//showDialog(mContext, updateMessage, apkUrl);
						MainActivity.startWizard(MainActivity.this, updateMessage);
					}

				} else if (mShowProgressDialog) {
					Toast.makeText(mContext, mContext.getString(R.string.android_auto_update_toast_no_new_update), Toast.LENGTH_SHORT).show();
				}

			} catch (JSONException e) {
				Log.e(Constants.TAG, "parse json error");
			}

		}

		private void showDialog(Context context, String content, String apkUrl) {
			UpdateDialog.show(context, content, apkUrl);
		}

		@Override
		protected String doInBackground(Void... args) {
			return HttpUtils.get(url);
		}

	}
	
}


