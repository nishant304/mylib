package com.wecamchat.aviddev.httpClient;

import android.view.inputmethod.InputBinding;

import com.wecamchat.aviddev.api.AvidUrls;
import com.wecamchat.aviddev.api.input.NearMeInput;
import com.wecamchat.aviddev.api.io.BrowseUserResult;
import com.wecamchat.aviddev.api.io.UserList;
import com.wecamchat.aviddev.model.bo.Profile;
import com.wecamchat.aviddev.util.AppFile;

import java.io.File;

/**
 * Created by tsingh on 21/1/15.
 */

public class AppRequestBuilder {
	private static final String BASE_URL = "http://104.237.131.161:3001";

	private static final String APP_BASE_URL = "http://104.237.131.161:3001";

	public static AppHttpRequest getHello(int i, AppResponseListener listener) {
		return AppHttpRequest.getGetRequest(APP_BASE_URL + "/hello/get/" + i,
				listener);
	}

	public static AppHttpRequest getHelloPost(AppResponseListener listener) {
		AppHttpRequest req = AppHttpRequest.getPostRequest(APP_BASE_URL
				+ "/hello/post/", listener);
		req.addParam("x", 1);
		req.addParam("y", 2);
		return req;
	}

	public static AppHttpRequest getSession(AppResponseListener listener) {
		AppHttpRequest req = AppHttpRequest.getGetRequest(APP_BASE_URL
				+ "/hello/session/", listener);
		req.addParam("x", 1);
		req.addParam("y", 2);
		return req;
	}

	public static AppHttpRequest userRecover(String email, String phone,
			AppResponseListener<String> listener) {
		return AppHttpRequest
				.getPostRequest(BASE_URL + "/user/recover", listener)
				.addParam("email", email).addParam("phone", phone);
	}

	public static AppHttpRequest userVerify(String vc, String email,
			String phone, AppResponseListener<String> listener) {
		return AppHttpRequest
				.getGetRequest(BASE_URL + "/me/verify" + "/" + vc, listener)
				.addParam("email", email).addParam("phone", phone);
	}

	public static AppHttpRequest getQuery(
			AppResponseListener<Integer> appResponseListener) {
		return AppHttpRequest.getGetRequest(APP_BASE_URL
				+ "/hello/query?accessToken=789", appResponseListener);
	}

	public static AppHttpRequest getNearMe(NearMeInput input,
			AppResponseListener<UserList> appResponseListener) {
		return AppHttpRequest
				.getPostRequest(APP_BASE_URL + "/user/find/near",
						appResponseListener)
				.addParam("limit", input.getLimit())
				.addParam("lng", input.getLng())
				.addParam("lat", input.getLat())
				.addParam("skip", input.getSkip())
				.addParam("maxDistance", String.valueOf(10000))
				.addParam("minDistance", String.valueOf(0));
	}

	public static AppHttpRequest registerMeWithImage(String filePath,
			String mName, AppResponseListener listener) {
		return AppHttpRequest
				.getPostRequest(APP_BASE_URL + "/me/register", listener)
				.addParam("name", mName)
				.addFile(
						"image",
						new AppFile(new File(filePath), "image/"
								+ getExt(filePath)));
	}

	public static AppHttpRequest registerMeWithVideo(String filePath,
			String mName, AppResponseListener listener) {
		return AppHttpRequest
				.getPostRequest(APP_BASE_URL + "/me/register", listener)
				.addParam("name", mName)
				.addFile(
						"image",
						new AppFile(new File(filePath), "video/"
								+ getExt(filePath)));
	}

	public static AppHttpRequest getHotUnhot(int flag,
			AppResponseListener<String> appResponseListener) {
		return AppHttpRequest.getGetRequest(BASE_URL + "/me/hot/" + flag,
				appResponseListener);
	}

	public static AppHttpRequest getMyProfile(
			AppResponseListener<Profile> listener) {
		return AppHttpRequest.getGetRequest(AvidUrls.BASE_URL
				+ AvidUrls.PROFILE, listener);
	}

	private static String getExt(String fileName) {
		String fileFormat = "";
		fileFormat = (fileName.split("[.]")[1]);
		return fileFormat;
	}
}
