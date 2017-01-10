package com.egygames.apps.rssreader.model;
/**
 * Article entity implements parcelable to be passed between activities.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article extends Model implements Parcelable {


    @SerializedName("nid")
    @Expose
    private String nid;
    @SerializedName("node_type")
    @Expose
    private String nodeType;

    @Column(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("author_img")
    @Expose
    private Object authorImg;

    @Column(name = "details")
    @SerializedName("details")
    @Expose
    private String details;

    @SerializedName("comment_count")
    @Expose
    private String commentCount;


    @Column(name = "created_date")
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("view_count")
    @Expose
    private String viewCount;

    @Column(name = "main_img")
    @SerializedName("main_img")
    @Expose
    private String mainImg;

    @Column(name = "section_name")
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("section_id")
    @Expose
    private String sectionId;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("countries")
    @Expose
    private Object countries;
    @SerializedName("is_followed")
    @Expose
    private Boolean isFollowed;
    @SerializedName("page_name")
    @Expose
    private String pageName;
    @SerializedName("page_id")
    @Expose
    private String pageId;
    @SerializedName("page_details")
    @Expose
    private String pageDetails;
    @SerializedName("page_type")
    @Expose
    private String pageType;
    @SerializedName("page_logo")
    @Expose
    private String pageLogo;


    public Article() {
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Object getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(Object authorImg) {
        this.authorImg = authorImg;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Object getCountries() {
        return countries;
    }

    public void setCountries(Object countries) {
        this.countries = countries;
    }

    public Boolean getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(Boolean isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageDetails() {
        return pageDetails;
    }

    public void setPageDetails(String pageDetails) {
        this.pageDetails = pageDetails;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPageLogo() {
        return pageLogo;
    }

    public void setPageLogo(String pageLogo) {
        this.pageLogo = pageLogo;
    }


    protected Article(Parcel in) {
        nid = in.readString();
        nodeType = in.readString();
        title = in.readString();
        authorId = in.readString();
        authorImg = (Object) in.readValue(Object.class.getClassLoader());
        details = in.readString();
        commentCount = in.readString();
        createdDate = in.readString();
        viewCount = in.readString();
        mainImg = in.readString();
        sectionName = in.readString();
        sectionId = in.readString();

        countries = (Object) in.readValue(Object.class.getClassLoader());
        byte isFollowedVal = in.readByte();
        isFollowed = isFollowedVal == 0x02 ? null : isFollowedVal != 0x00;
        pageName = in.readString();
        pageId = in.readString();
        pageDetails = in.readString();
        pageType = in.readString();
        pageLogo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nid);
        dest.writeString(nodeType);
        dest.writeString(title);
        dest.writeString(authorId);
        dest.writeValue(authorImg);
        dest.writeString(details);
        dest.writeString(commentCount);
        dest.writeString(createdDate);
        dest.writeString(viewCount);
        dest.writeString(mainImg);
        dest.writeString(sectionName);
        dest.writeString(sectionId);

        dest.writeValue(countries);
        if (isFollowed == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isFollowed ? 0x01 : 0x00));
        }
        dest.writeString(pageName);
        dest.writeString(pageId);
        dest.writeString(pageDetails);
        dest.writeString(pageType);
        dest.writeString(pageLogo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}