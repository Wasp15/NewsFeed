package com.enterprises.wasp.newsfeed;

public class News {

    private String privateSectionName;

    private String privateWebTitle;

    private String privateWebPublicationDate;

    private String privateUrl;

    private String privateAuthorName;

    public News(String sectionName, String webTitle, String webPublicationDate, String url,
                String authorName) {
        privateSectionName = sectionName;
        privateWebTitle = webTitle;
        privateWebPublicationDate = webPublicationDate;
        privateUrl = url;
        privateAuthorName = authorName;
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

    public String getPrivateAuthorName() {
        return privateAuthorName;
    }
}

