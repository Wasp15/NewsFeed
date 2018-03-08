package com.enterprises.wasp.newsfeed;

import java.util.Date;

public class News {

    private String privateSectionName;

    private String privateWebTitle;

    private Date privateWebPublicationDate;

    public News(String sectionName, String webTitle, Date webPublicationDate) {
        privateSectionName = sectionName;
        privateWebTitle = webTitle;
        privateWebPublicationDate = webPublicationDate;
    }

    public String getPrivateSectionName() {
        return privateSectionName;
    }

    public String getPrivateWebTitle() {
        return privateWebTitle;
    }

    public Date getPrivateWebPublicationDate() {
        return privateWebPublicationDate;
    }
}

