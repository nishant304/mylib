package com.wecamchat.aviddev.viewadapter;

import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.activity.AvidBaseActivity;
import com.wecamchat.aviddev.activity.AvidFragmentBaseActivity;
import com.wecamchat.aviddev.model.bo.User;

public class ChatViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    AvidFragmentBaseActivity activity;
    List<User> chatUser;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private MyTouchListener mOnTouchListener;
    private int action_down_x = 0;
    private int action_up_x = 0;
    private int difference = 0;

    public ChatViewAdapter(AvidFragmentBaseActivity activity, List<User> chatUser,
            ImageLoader imageLoader, DisplayImageOptions options) {
        this.imageLoader = imageLoader;
        this.options = options;
        this.activity = activity;
        this.chatUser = chatUser;
        inflater = LayoutInflater.from(activity);
        mOnTouchListener = new MyTouchListener();
    }

    @Override
    public int getCount() {
        return chatUser.size();
    }

    @Override
    public Object getItem(int i) {
        return chatUser.get(i);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            vi = inflater.inflate(R.layout.layout_chat_custome_list_view, null);

            holder = new ViewHolder();

            holder.iv_chat_view_user_image = (ImageView) vi
                    .findViewById(R.id.iv_chat_view_user_image);
            holder.iv_chat_view_user_border = (ImageView) vi
                    .findViewById(R.id.iv_chat_view_user_border);
            holder.iv_chat_view_user_icon = (ImageView) vi
                    .findViewById(R.id.iv_chat_view_user_icon);

            holder.iv_chat_upload_image = (ImageView) vi
                    .findViewById(R.id.iv_chat_upload_image);
            holder.iv_chat_play_btn = (ImageView) vi
                    .findViewById(R.id.iv_chat_play_btn);

            holder.tv_chat_user_name = (TextView) vi
                    .findViewById(R.id.tv_chat_user_name);
            holder.tv_chat_user_message = (TextView) vi
                    .findViewById(R.id.tv_chat_user_message);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        setData((User) getItem(pos), holder);
        return vi;

    }

    private void setData(User user, ViewHolder holder) {
        if (user != null) {

            imageLoader.displayImage(user.getuImg(),
                    holder.iv_chat_view_user_image, options);

            holder.iv_chat_view_user_border
                    .setImageResource(getLargeBorderResource(user
                            .getActiveStatus()));
            holder.iv_chat_view_user_icon
                    .setImageResource(getSmallResourceIcon(user.getUserType()));

            holder.tv_chat_user_name.setText(user.getuName());

            int index = getMessageImage(user.getUserType());
            if (user.getUserType() == 1) {
                holder.iv_chat_play_btn.setVisibility(View.VISIBLE);
            } else {
                holder.iv_chat_play_btn.setVisibility(View.INVISIBLE);
            }
            holder.iv_chat_upload_image.setImageResource(index);
        }
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public class ViewHolder {

        public View first, second;

        public ImageView iv_chat_view_user_image;
        public ImageView iv_chat_view_user_icon;
        public ImageView iv_chat_view_user_border;

        public ImageView iv_chat_play_btn;
        public ImageView iv_chat_upload_image;

        public TextView tv_chat_user_name;
        public TextView tv_chat_user_message;
    }

    int getSmallResourceIcon(int type) {
        switch (type) {

        case 1:

            return R.drawable.star_ic;
        case 2:

            return R.drawable.user_group_ic;

        default:
            return R.drawable.hot_ic;

        }
    }

    int getMessageImage(int type) {
        switch (type) {
        case 1:
            return R.drawable.user_bg;
        case 2:
            return R.drawable.chat_message_send_ic;
        default:

            return R.drawable.chat_messages_smile_ic;

        }
    }

    int getLargeBorderResource(int type) {
        switch (type) {

        case 1:

            return R.drawable.user_grey_border;

        default:
            return R.drawable.user_green_border;

        }
    }

    class MyTouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            ViewHolder holder = (ViewHolder) v
                    .getTag(R.layout.layout_chat_custome_list_view);
            int action = event.getAction();
            int position = (Integer.parseInt(v.getTag().toString()));
            Log.i("TAG", "  pos " + position);
            switch (action) {
            case MotionEvent.ACTION_DOWN:
                action_down_x = (int) event.getX();
                Log.d("action", "ACTION_DOWN - ");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("action", "ACTION_MOVE - ");
                action_up_x = (int) event.getX();
                difference = action_down_x - action_up_x;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("action", "ACTION_UP - ");
                calcuateDifference(holder, position);
                action_down_x = 0;
                action_up_x = 0;
                difference = 0;
                break;
            }
            return true;
        }
    }

    private void calcuateDifference(final ViewHolder holder, final int position) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (difference == 0) {

                }
                // r - l
                if (difference > 200) {
                    holder.second.setVisibility(View.VISIBLE);
                }
                // l - r
                if (difference < -200) {

                }
            }
        });
    }

}
