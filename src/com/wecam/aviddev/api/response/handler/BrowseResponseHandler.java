package com.wecam.aviddev.api.response.handler;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import com.wecamchat.aviddev.api.ErrorObject;
import com.wecamchat.aviddev.api.io.User;
import com.wecamchat.aviddev.api.io.UserList;
import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.db.BrowseContract;
import com.wecamchat.aviddev.db.DbInsertHelper;
import com.wecamchat.aviddev.httpClient.AppResponseListener;
import com.wecamchat.aviddev.util.AppUtil;

public class BrowseResponseHandler extends AppResponseListener<UserList> {

	private Context context;

	public BrowseResponseHandler(Class<UserList> t, Context context) {
		super(t);
		this.context = context;
	}

	@Override
	public void onSuccess(final UserList list) {
		DbInsertHelper.getInstance().submitNewTask(new Runnable() {
			@Override
			public void run() {

				for (User user : list.getUsers()) {
					AppUtil.getContentValues(user);
					ContentValues values = new ContentValues();
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_UID,
							user.getId());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_UNAME,
							user.getName());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_DISTANCE,
							user.getDistance());
					values.put(
							BrowseContract.BrowseEntry.COLUMN_NAME_BODY_HAIR,
							user.getMetaData().getBodyHair());
					values.put(
							BrowseContract.BrowseEntry.COLUMN_NAME_BODY_TYPE,
							"");
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_CUT, user
							.getMetaData().getCut());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_DRINK,
							user.getMetaData().getDrink());
					values.put(
							BrowseContract.BrowseEntry.COLUMN_NAME_ETHNICITY,
							user.getMetaData().getEthnicity());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_EYECOLOR,
							user.getMetaData().getEyeColor());
					values.put(
							BrowseContract.BrowseEntry.COLUMN_NAME_HAIRCOLOR,
							user.getMetaData().getHairColor());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_HEIGHT,
							user.getMetaData().getHeight());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_HIV_ST,
							"");
					values.put(
							BrowseContract.BrowseEntry.COLUMN_NAME_LAST_ACT_TIME,
							"");
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_LAT, user
							.getPos().getCoordinates().get(0));
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_LONG,
							user.getPos().getCoordinates().get(1));
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_ORIENT,
							user.getMetaData().getOrientation());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_OUTTO,
							user.getMetaData().getOutTo());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_PERSONA,
							user.getMetaData().getPersona());
					values.put(
							BrowseContract.BrowseEntry.COLUMN_NAME_PRIVATE_NOTE,
							"");
					values.put(
							BrowseContract.BrowseEntry.COLUMN_NAME_PROFILE_DESC,
							"");
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_ROLE, "");
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_SIZE,
							user.getMetaData().getSize());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_SMOKE,
							user.getMetaData().getSmoke());
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_TEMPER,
							"");
					values.put(BrowseContract.BrowseEntry.COLUMN_NAME_UIMG,
							user.getPics().get(0).getUrl());
					getContentResolver().insert(
							AvidCPHelper.Uris.URI_BROWSE_USER, values);
				}
			}
		});
	}

	private ContentResolver getContentResolver() {
		return context.getApplicationContext().getContentResolver();
	}

	@Override
	public void onError(ErrorObject error) {

	}

}
