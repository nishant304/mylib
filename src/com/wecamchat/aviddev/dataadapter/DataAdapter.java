package com.wecamchat.aviddev.dataadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wecamchat.aviddev.model.bo.MediaContent;

public class DataAdapter {

    Random random = new Random();

    private String[] images = {
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/1477808435448400161_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/6467345464276633858_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/3644145802928408850_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/5017432411640253662_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/-1770947963276409371_thumb.jpg",
            "https://uat-akiaioxcsjhrze6wdu3a-wc-photo.s3.amazonaws.com/-7752448954739441098_thumb.jpg" };

    protected String[] videos = {
            "http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4",
            "http://www.quirksmode.org/html5/videos/big_buck_bunny.ogv" };

    protected String[] names = { "Steve Waugh", "Louis Peterson",
            "Phillip Huge", "Joseph Williams", "Clark Davis",
            "Edward Stevenson", "Daniel Booker" };

    protected String[] upFor = { "Just Chatting", "Video Calls", "Friendships",
            "Right Now", "Relationships" };

    protected String[] role = { "Top", "Bottom", "Versatile" };

    protected String[] orientation = { "Gay", "Bisexual", "Bi-curious",
            "Open Minded", "Transgender" };

    protected String[] persona = { "Twink", "Butch", "Bear", "Daddy",
            "Clean-cut", "Jock", "Leather", "Otter", "Rugged", "Trans" };

    protected String[] bodyType = { "Slim", "Muscular", "Athletic",
            "Heavy Set", "Big Boy" };

    protected String[] bodyHair = { "Smooth", "Trimmed", "Shaved",
            "Light Hair", "Hairy" };

    protected String[] temperment = { "Shy", "Outgoing", "Over the top" };

    protected String[] cut = { "Cut", "Uncut" };

    protected String[] size = { "Small", "Average", "Large", "Extra Large" };

    protected String[] ethnicity = { "Black/African", "Hispanic", "Caucasian",
            "East Indian", "Middle Eastern", "Native American",
            "Pacific Island", "Mixed Race" };

    protected String[] hivStatus = { "Negative", "Positive", "Don't Know" };

    protected String[] height = { "Really Short", "Short", "Average", "Tall",
            "Really Tall" };

    protected String[] outTo = { "Friends", "Family", "Co-Workers" };

    protected String[] drink = { "Yes", "Socially", "Never" };

    protected String[] smoke = { "Yes", "Socially", "Never" };

    protected String[] eyeColor = { "Black", "Blue", "Brown", "Grey", "Green",
            "Hazel" };

    protected String[] hairColor = { "Bald", "Auburn", "Black", "Blonde",
            "Brown", "Grey", "White" };

    protected String[] lastActiveTimes = { "Active 2 hours ago",
            "Active 1 hour ago", "Active 3 hour ago", "Acive 7 hours ago" };

    protected String getSingleOptions(String[] singleOptionsList) {
        return singleOptionsList[randInt(0, singleOptionsList.length - 1)];
    }

    protected String getMultipleOptions(String[] multiOptionList) {
        int option = randInt(1, multiOptionList.length - 1);
        StringBuilder response = new StringBuilder();
        for (int index = 0; index < option; index++) {
            response.append(multiOptionList[index]);
            if (index < option - 1) {
                response.append(", ");
            }
        }

        return response.toString();
    }

    protected int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    protected List<MediaContent> getMediaContents() {
        List<MediaContent> mediaContents = new ArrayList<MediaContent>();
        MediaContent mediaContent = null;
        for (int index = 0; index < 5; index++) {
            mediaContent = new MediaContent();
            if (random.nextInt(1) == 0) {
                mediaContent.setType("image");
                mediaContent.setUrl(getSingleOptions(getImages()));
            } else {
                mediaContent.setType("video");
                mediaContent.setUrl(getSingleOptions(videos));
            }

            // add bo in list here

            mediaContents.add(mediaContent);

        }
        return mediaContents;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

}
