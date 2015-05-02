package com.wecamchat.aviddev.fragment;

import java.util.List;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.model.bo.User;
import com.wecamchat.aviddev.viewadapter.ChatViewAdapter;

public class ChatFragment extends AvidBaseFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private View view;
    private ListView lv_chat;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        try {

            view = inflater.inflate(R.layout.fragment_chat, container, false);
            getLoaderManager().initLoader(0, null, this);
            return view;

        } catch (Exception e) {
            return null;
        }
    }

    private void setUI(List<User> chatUsers) {

        lv_chat = (ListView) view.findViewById(R.id.lv_chat);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_image).cacheInMemory(false)
                .cacheOnDisc(true).considerExifParams(true).build();

        lv_chat.setAdapter(new ChatViewAdapter(
                (AvidFragmentBaseActivity) getActivity(), chatUsers, imageLoader,
                options));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                AvidCPHelper.Uris.URI_BROWSE_USER, null, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        AvidCPHelper helper = AvidCPHelper.getInstance(getActivity());

        Cursor cursor = helper.getCursorB(AvidCPHelper.Uris.URI_BROWSE_USER,
                null, null, null, null);

        List<User> chatUsers = helper.getBrowseUsersFromCursor(cursor);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_image).cacheInMemory(false)
                .cacheOnDisc(true).considerExifParams(true).build();

        setUI(chatUsers);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {

    }

    @Override
    public boolean onBackPressed() {
        // TODO Auto-generated method stub
        return false;
    }
}
