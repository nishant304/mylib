package com.wecamchat.aviddev.model.bo;

import java.util.List;

public class ExpandableFilter {

    private String parent;
    private List<FilterOptions> children;

    public List<FilterOptions> getChildren() {
        return children;
    }

    public void setChildren(List<FilterOptions> children) {
        this.children = children;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
