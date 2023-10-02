package org.examples.diff.model;

import java.util.List;

public class ListChange {
    private String property;
    private List<String> added;
    private List<String> removed;
    private String subname = "";
    public ListChange(String property, List<String> added, List<String> removed) {
        this.property = property;
        this.added = added;
        this.removed = removed;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }
    public String getSubname() {
        return subname;
    }
    @Override
    public String toString() {
        String props = subname;
        if(subname.startsWith(".")){
            props = subname.substring(1);
        }
        if(subname.endsWith(".") && subname.length() > 2
                && !subname.contains("]")){
            props = subname.substring(0, subname.length()-1);
        }
        return "\n{" +
                "property='" +  props   + '\'' +
                ", added='" + added.toString() + '\'' +
                ", removed='" + removed.toString() + '\'' +
                '}';
    }
}
