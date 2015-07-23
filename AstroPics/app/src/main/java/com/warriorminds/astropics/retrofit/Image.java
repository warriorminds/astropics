package com.warriorminds.astropics.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rodrigo.guerrero on 6/24/2015.
 */
public class Image implements Serializable {

    private boolean animated;
    private String description;
    private int h;
    private String id;
    @SerializedName("imaging_cameras")
    private String[] cameras;
    @SerializedName("imaging_telescopes")
    private String[] telescopes;
    @SerializedName("is_final")
    private boolean isFinal;
    @SerializedName("is_solved")
    private boolean isSolved;
    private int license;
    private String link;
    @SerializedName("link_to_fits")
    private String linkToFits;
    private String[] locations;
    @SerializedName("resource_uri")
    private String resourceUri;
    private String[] revisions;
    private String[] subjects;
    private String title;
    private String updated;
    private String uploaded;
    @SerializedName("url_duckduckgo")
    private String url;
    @SerializedName("url_duckduckgo_small")
    private String urlSmall;
    @SerializedName("url_gallery")
    private String urlGallery;
    @SerializedName("url_hd")
    private String urlHd;
    @SerializedName("url_real")
    private String urlReal;
    @SerializedName("url_regular")
    private String urlRegular;
    @SerializedName("url_thumb")
    private String urlThumbnail;
    private String user;
    private int w;


    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getCameras() {
        return cameras;
    }

    public void setCameras(String[] cameras) {
        this.cameras = cameras;
    }

    public String[] getTelescopes() {
        return telescopes;
    }

    public void setTelescopes(String[] telescopes) {
        this.telescopes = telescopes;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public int getLicense() {
        return license;
    }

    public void setLicense(int license) {
        this.license = license;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkToFits() {
        return linkToFits;
    }

    public void setLinkToFits(String linkToFits) {
        this.linkToFits = linkToFits;
    }

    public String[] getLocations() {
        return locations;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    public String[] getRevisions() {
        return revisions;
    }

    public void setRevisions(String[] revisions) {
        this.revisions = revisions;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlSmall() {
        return urlSmall;
    }

    public void setUrlSmall(String urlSmall) {
        this.urlSmall = urlSmall;
    }

    public String getUrlGallery() {
        return urlGallery;
    }

    public void setUrlGallery(String urlGallery) {
        this.urlGallery = urlGallery;
    }

    public String getUrlHd() {
        return urlHd;
    }

    public void setUrlHd(String urlHd) {
        this.urlHd = urlHd;
    }

    public String getUrlReal() {
        return urlReal;
    }

    public void setUrlReal(String urlReal) {
        this.urlReal = urlReal;
    }

    public String getUrlRegular() {
        return urlRegular;
    }

    public void setUrlRegular(String urlRegular) {
        this.urlRegular = urlRegular;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }
}
