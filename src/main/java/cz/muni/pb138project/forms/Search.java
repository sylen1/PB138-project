package cz.muni.pb138project.forms;

import java.util.Map;

public class Search {
    private String label;
    private String tags;
    private Map<String, String> properties;
    private String category;
    private boolean onlySelectedCategory;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String[] getTagArray() {
        return tags.split(" ");
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isOnlySelectedCategory() {
        return onlySelectedCategory;
    }

    public void setOnlySelectedCategory(boolean onlySelectedCategory) {
        this.onlySelectedCategory = onlySelectedCategory;
    }
}
