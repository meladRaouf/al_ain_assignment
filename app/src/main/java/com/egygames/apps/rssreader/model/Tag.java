
package com.egygames.apps.rssreader.model;

/**
 * Tag entity.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

    @SerializedName("tag_name")
    @Expose
    private String tagName;
    @SerializedName("tag_id")
    @Expose
    private String tagId;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

}
