package org.examples.diff.model;

public final class PorpertyUpdate implements ChangeType {
    PorpertyChange porpertyChange;
    public PorpertyUpdate(String property, String previous, String current) {
        porpertyChange =  new PorpertyChange(property, previous, current);
    }
    public PorpertyUpdate(String property, int previous, int current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }
    public PorpertyUpdate(String property, Object previous, Object current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }
    public PorpertyUpdate(String property, long previous, long current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }
    public PorpertyUpdate(String property, float previous, float current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }
    public PorpertyUpdate(String property, double previous, double current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }
    public PorpertyUpdate(String property, char previous, char current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }
    public PorpertyUpdate(String property, short previous, short current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }
    public PorpertyUpdate(String property, byte previous, byte current) {
        porpertyChange =  new PorpertyChange(property, String.valueOf(previous), String.valueOf(current));
    }

    @Override
    public String toString() {
        return porpertyChange.toString();
    }
    public String getSubname() {
        return porpertyChange.getSubname();
    }

    @Override
    public void addSubNameList(String subnameList) {

    }

    @Override
    public String getSubnameList() {
        return null;
    }

    @Override
    public void addSubName(String subname) {
        porpertyChange.setSubname(subname);
    }
}
