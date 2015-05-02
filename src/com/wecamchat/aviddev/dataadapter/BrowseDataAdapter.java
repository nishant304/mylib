package com.wecamchat.aviddev.dataadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;

import com.wecamchat.aviddev.cp.AvidCPHelper;
import com.wecamchat.aviddev.model.bo.MetaData;
import com.wecamchat.aviddev.model.bo.User;

public class BrowseDataAdapter extends DataAdapter {

    Random random = new Random();

    private Context context;

    private String[] images = {
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/1477808435448400161_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/6467345464276633858_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/3644145802928408850_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/5017432411640253662_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/-1770947963276409371_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/-7752448954739441098_thumb.jpg" };

    public BrowseDataAdapter(Context context) {
        super();
        this.context = context;
    }

    @SuppressWarnings("boxing")
    public void inserDummyDataInDb() {

        int noOfUSer = randInt(50, 100);
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
            user.setuImg(images[random.nextInt(5)]);
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

            // add bo in list
            users.add(user);

        }
        // insert object here

        AvidCPHelper helper = AvidCPHelper.getInstance(context);

        helper.insertBrowseUserContentProviderData(users);

    }

    public List<User> getBrowseUsers(final Cursor cursor) {
        AvidCPHelper helper = AvidCPHelper.getInstance(context);

        return helper.getBrowseUsersFromCursor(cursor);

    }
}
