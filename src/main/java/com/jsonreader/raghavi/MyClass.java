package com.jsonreader.raghavi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class MyClass {
	
	@Test
	public void readJsin() throws IOException, JSONException {
		String url = "http://api.viki.io/v4/videos.json?app=100250a&per_page=10&page=";
		boolean more = true;
		int trueCount = 0, falseCount = 0, pageNo = 0;

		while (more) {
			pageNo++;
			String loopUrl = url + Integer.toString(pageNo);
			// System.out.println(loopUrl);
			URL urlCon = new URL(loopUrl);
			HttpURLConnection con = (HttpURLConnection) urlCon.openConnection();
			con = (HttpURLConnection) urlCon.openConnection();

			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				content.append(inputLine);
			}
			reader.close();

			String jsonString = content.toString();
			JSONObject jsonObject = new JSONObject(jsonString);
			more = jsonObject.getBoolean("more");
			JSONArray response = jsonObject.getJSONArray("response");

			for (int i = 0; i < response.length(); i++) {
				JSONObject flags = response.getJSONObject(i).getJSONObject("flags");
				if (flags.getBoolean("hd")) {
					trueCount++;
				} else {
					falseCount++;
				}
			}
		}
		System.out.println("Number of response objects have flags:hd set to true are : " + trueCount);
		System.out.println("Number of response objects have flags:hd set to true are : " + falseCount);

	}
}
