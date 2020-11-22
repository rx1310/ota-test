package rx1310.optinova.ota;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static String get(String urlStr) {
		
        HttpURLConnection urlConnection = null;
        InputStream is = null;
        BufferedReader buffer = null;
        String result = null;
		
        try {
			
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            is = urlConnection.getInputStream();
            buffer = new BufferedReader(new InputStreamReader(is));
			
            StringBuilder strBuilder = new StringBuilder();
            String line;
			
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
			
            result = strBuilder.toString();
			
        } catch (Exception e) {
            Log.e(Constants.TAG, "http  error");
        } finally {
			
            if (buffer != null) {
				
                try {
                    buffer.close();
                } catch (IOException ignored) { }
				
            }
			
            if (is != null) {
				
                try {
                    is.close();
                } catch (IOException ignored) { }
				
            }
			
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
			
        }
		
        return result;
		
    }
	
}
