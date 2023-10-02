package org.examples.diff.model;

import java.util.ArrayList;
import java.util.List;

public final class ListUpdate implements ChangeType {
    List<PorpertyChange>  porpertyChanges = new ArrayList<>();;
    List<ListChange>  listChanges = new ArrayList<>();
    String subname = "";
    String subnameList = "";
    public void addPorpertyUpdate(String property, String previous, String current) {
        porpertyChanges.add(new PorpertyChange(property, previous, current));
    }
    public void addPorpertyListUpdate(String property, List<String> previous, List<String>  current) {
        listChanges.add(new ListChange(property, previous, current));
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (!porpertyChanges.isEmpty()) {
            sb.append(porpertyChanges);
            sb.append("\n");
        }
        if (!listChanges.isEmpty()) {
            listChanges.forEach(sb::append);
        }
        return sb.toString();
    }

    @Override
    public void addSubName(String subname) {
        porpertyChanges.forEach(p -> p.setSubname(subname));
        this.subname = subname;
    }
    @Override
    public void addSubNameList(String subnameList) {
        listChanges.forEach(p -> p.setSubname(subnameList));
        this.subnameList = subnameList;
    }

    @Override
    public String getSubname() {
        return subname;
    }
    @Override
    public String getSubnameList() {
        return subnameList;
    }

}
