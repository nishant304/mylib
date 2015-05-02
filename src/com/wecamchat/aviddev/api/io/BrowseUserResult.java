package com.wecamchat.aviddev.api.io;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.wecamchat.aviddev.api.ApiOutput;

public class BrowseUserResult extends ApiOutput {

	@Expose
	private Integer statusCode;
	@Expose
	private UserList result;

	/**
	 * 
	 * @return The statusCode
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * 
	 * @param statusCode
	 *            The statusCode
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 
	 * @return The result
	 */
	public UserList getResult() {
		return result;
	}

	/**
	 * 
	 * @param result
	 *            The result
	 */
	public void setResult(UserList result) {
		this.result = result;
	}

	@Override
	public boolean createFromJson(final JSONObject jsonObject) {
		final Gson gson = new Gson();
		try {
			result = gson
					.fromJson(jsonObject.getString("result"), UserList.class);
			statusCode = jsonObject.getInt("statusCode");
		} catch (JSONException excption) {
			return false;
		}
		return true;
	}

}
