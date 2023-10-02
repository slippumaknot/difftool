package org.examples.diff.model;

public class PorpertyChange {
    private String property;
    private String previous;
    private String current;
    private String subname = "";

    public PorpertyChange(String property, String previous, String current) {
        this.property = property;
        this.previous = previous;
        this.current = current;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getSubname() {
        return subname;
    }

    @Override
    public String toString() {
        return "\n{" +
                "property='" + subname +  property + '\'' +
                ", previous='" + previous + '\'' +
                ", current='" + current + '\'' +
                '}';
    }
}
