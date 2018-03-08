package com.enterprises.wasp.newsfeed;

public class News {

    private String privateSectionName;

    private String privateWebTitle;

    private String privateWebPublicationDate;

    private String privateUrl;

    public News(String sectionName, String webTitle, String webPublicationDate, String url) {
        privateSectionName = sectionName;
        privateWebTitle = webTitle;
        privateWebPublicationDate = webPublicationDate;
        privateUrl = url;
    }

    public String getPrivateSectionName() {
        return privateSectionName;
    }

    public String getPrivateWebTitle() {
        return privateWebTitle;
    }

    public String getPrivateWebPublicationDate() {
        return privateWebPublicationDate;
    }

    public String getPrivateUrl() {
        return privateUrl;
    }
}

