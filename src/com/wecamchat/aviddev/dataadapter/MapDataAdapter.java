package com.wecamchat.aviddev.dataadapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.model.bo.MetaData;
import com.wecamchat.aviddev.model.bo.User;

public class MapDataAdapter extends DataAdapter {

    private Context context;

    public MapDataAdapter(Context context) {
        super();
        this.context = context;
    }

    @SuppressWarnings("boxing")
    public void inserDummyDataInDb() {

        int noOfUSer = randInt(20, 40);

        User user = null;
        List<User> users = new ArrayList<User>();

        for (int index = 0; index < noOfUSer; index++) {
            user = new User();
            if (index % 2 == 0) {
                user.setmLatitude(28.62232 + (random.nextInt(5) * 0.002));
                user.setmLongitude(77.3754 + (random.nextInt(5) * 0.002));

            } else {
                user.setmLatitude(28.62232 - (random.nextInt(5) * 0.002));
                user.setmLongitude(77.3754 - (random.nextInt(5) * 0.002));

            }
            user.setuId(index);
            user.setuName(names[random.nextInt(5)]);
            user.setUserType(random.nextInt(2));
            user.setActiveStatus(random.nextInt(2));
            user.setuImg(getImages()[random.nextInt(5)]);
            user.setAge(randInt(32, 68));

            user.setMetaData(new MetaData());
            user.getMetaData().setBody_hair(getMultipleOptions(bodyHair));
            user.getMetaData().setBody_type(getSingleOptions(bodyType));
            user.getMetaData().setCut((getSingleOptions(cut)));
            user.setDistance(random.nextInt(15) + " mi away ");
            user.getMetaData().setHiv_status((getSingleOptions(hivStatus)));
            user.setLastActiveTime(getSingleOptions(lastActiveTimes));
            user.setMediaContents(getMediaContents());
            user.getMetaData().setOrientation(getSingleOptions(orientation));
            user.getMetaData().setPersona(getSingleOptions(persona));
            user.setPrivateNotes("I have to meet John and I want friendship.");
            user.setProfileDesc("Here is John's headline! Users can write a short message here.");
            user.getMetaData().setRole(getSingleOptions(role));
            user.getMetaData().setSize(getSingleOptions(size));
            user.getMetaData().setTemperament(getSingleOptions(temperment));
            user.getMetaData().setUp_for(getMultipleOptions(upFor));
            user.setWinkCount(randInt(200, 400));

            user.getMetaData().setHeight(getSingleOptions(height));
            user.getMetaData().setDrink(getSingleOptions(drink));
            user.getMetaData().setEye_color(getSingleOptions(eyeColor));
            user.getMetaData().setHair_color(getSingleOptions(hairColor));
            user.getMetaData().setSmoke(getSingleOptions(smoke));
            user.getMetaData().setOut_to(getSingleOptions(outTo));
            user.getMetaData().setEthnicity(getSingleOptions(ethnicity)); 

            if (!random.nextBoolean()) {
                user.setUser(false);
            }

            // add bo in list
            users.add(user);

        }
        // insert object here

        AvidCPHelper helper = AvidCPHelper.getInstance(context);

        helper.insertMapUserContentProviderData(users);

    }

    public List<User> getMapUsers(final Cursor cursor) {
        AvidCPHelper helper = AvidCPHelper.getInstance(context);

        return helper.getMapUsersFromCursor(cursor);

    }

}
