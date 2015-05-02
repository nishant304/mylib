package com.wecamchat.aviddev.model.bo;

public class FilterOptions {
    private boolean isChecked;
    private String filterOptionName;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getFilterOptionName() {
        return filterOptionName;
    }

    public void setFilterOptionName(String filterOptionName) {
        this.filterOptionName = filterOptionName;
    }
}
