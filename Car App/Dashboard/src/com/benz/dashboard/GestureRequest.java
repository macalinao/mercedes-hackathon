package com.benz.dashboard;

import org.shaded.apache.http.HttpEntity;
import org.shaded.apache.http.HttpResponse;
import org.shaded.apache.http.client.HttpClient;
import org.shaded.apache.http.client.methods.HttpPost;
import org.shaded.apache.http.entity.StringEntity;
import org.shaded.apache.http.impl.client.DefaultHttpClient;
import org.shaded.apache.http.protocol.HTTP;

import android.os.AsyncTask;

public class GestureRequest extends AsyncTask<String, Integer, Void> {

	@Override
	protected Void doInBackground(String... params) {
		try {
			String parameters = params[0];
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://localhost:21000/runjs");

			StringEntity se = new StringEntity(parameters, HTTP.UTF_8);
			se.setContentType("text/xml");
			httppost.setEntity(se);

			HttpResponse httpresponse = httpclient.execute(httppost);
			HttpEntity resEntity = httpresponse.getEntity();
			// tvData.setText(EntityUtils.toString(resEntity));

			// Unirest.post().body(String.format(cmd, args)).asJson();
			// HttpURLConnection connection;
			// OutputStreamWriter request = null;
			// URL url = null;
			// String response = null;
			// String parameters = params[0];
			//
			// try {
			//
			// url = new URL("http://localhost:21000/runjs");
			// connection = (HttpURLConnection) url.openConnection();
			// connection.setDoOutput(true);
			// // connection.setRequestProperty("Content-Type",
			// // "application/x-www-form-urlencoded");
			// connection.setRequestMethod("POST");
			//
			// request = new OutputStreamWriter(connection.getOutputStream());
			// request.write(parameters);
			// request.flush();
			// String line = "";
			// InputStreamReader isr = new InputStreamReader(
			// connection.getInputStream());
			// BufferedReader reader = new BufferedReader(isr);
			// StringBuilder sb = new StringBuilder();
			// while ((line = reader.readLine()) != null) {
			// sb.append(line + "\n");
			// }
			// // Response from server after login process will be stored in
			// // response variable.
			// response = sb.toString();
			// // You can perform UI operations here
			// Log.e("Message from Server:", response);
			// isr.close();
			// reader.close();
			// request.close();

			// 8. Execute POST request to the given URL
			// HttpResponse httpResponse = httpclient.execute(httpPost);

			// // 9. receive response as inputStream
			// inputStream = httpResponse.getEntity().getContent();
			//
			// // 10. convert inputstream to string
			// if (inputStream != null)
			// result = convertInputStreamToString(inputStream);
			// else
			// result = "Did not work!";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}