package cz.muni.pb138project.forms;

public class ChangeCategory {
    private int mediaId;
    private String oldCategory;
    private String newCategory;

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getOldCategory() {
        return oldCategory;
    }

    public void setOldCategory(String oldCategory) {
        this.oldCategory = oldCategory;
    }

    public String getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(String newCategory) {
        this.newCategory = newCategory;
    }

    @Override
    public String toString() {
        return "ChangeCategory{" +
                "mediaId=" + mediaId +
                ", oldCategory='" + oldCategory + '\'' +
                ", newCategory='" + newCategory + '\'' +
                '}';
    }
}
