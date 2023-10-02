package org.examples.diff;

import org.examples.diff.anotations.AuditKey;
import org.examples.diff.exceptions.DuplicateIdTypeException;
import org.examples.diff.exceptions.NoIdTypeInObjectsListException;
import org.examples.diff.model.ChangeType;
import org.examples.diff.model.ListUpdate;
import org.examples.diff.model.PorpertyUpdate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiffTool {
    private static final List<String> simpleObjectsList = Arrays.asList("java.lang.Integer","java.lang.Short","java.lang.Double",
            "java.lang.Float","java.lang.String");
    private static final List<String> listTypesList = Arrays.asList("java.util.List","java.util.ArrayList","java.util.Arrays",
            "java.util.Arrays$ArrayList");

    public List<ChangeType> diff(Object previous, Object current) throws IllegalAccessException {
        List<ChangeType> diffList = new ArrayList<>();
        if (previous == current)  {
            return diffList;
        }
        if (previous == null && current == null) {
            return diffList;
        }
        if ((previous != null && current == null) ||
                (previous == null && current != null)) {
            return diffList;
        }

        //Evaluate LISTS
        if (listTypesList.contains(previous.getClass().getTypeName())) {
            List listPrevious = (List) previous;
            List listCurrent = (List) current;
            List<String> prevListAux = new ArrayList<>();
            List<String> currentListAux = new ArrayList<>();
            String fieldTypeName;
            List keyList = null;
            if (listPrevious.isEmpty()) {
                if (listCurrent.isEmpty()) {
                    return diffList;
                } else {
                    fieldTypeName = listCurrent.get(0).getClass().getTypeName();
                    keyList = listCurrent;
                }
            } else {
                fieldTypeName = listPrevious.get(0).getClass().getTypeName();
                keyList = listPrevious;
            }
            //Lists with objects
            if (!simpleObjectsList.contains(fieldTypeName)) {
                Field fieldId = null;
                //Get ID
                for (Field field : keyList.get(0).getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(AuditKey.class) || field.getName().equals("id")) {
                        if (fieldId != null) {
                            throw new DuplicateIdTypeException();
                        }
                        fieldId = field;
                    }
                }
                if (fieldId == null) {
                    throw new NoIdTypeInObjectsListException();
                }
                boolean filledCurrentIds = false;
                //Compare objects
                for (Object prevItem: listPrevious) {
                    fieldId.setAccessible(true);
                    String prevId = String.valueOf(fieldId.get(prevItem));
                    prevListAux.add("{" + fieldId.getName() + "[" + prevId + "]}");
                    for (Object currentItem: listCurrent) {
                        String currentId = String.valueOf(fieldId.get(currentItem));
                        if (!filledCurrentIds) {
                            currentListAux.add("{" + fieldId.getName() + "[" + currentId + "]}");
                        }
                        if (prevId.equals(currentId)) {
                            //Compare objects and get result
                            List<ChangeType> lt = diff(prevItem, currentItem);
                            Field finalFieldId = fieldId;
                            lt.forEach(d -> {
                                if (d instanceof ListUpdate) {
                                    d.addSubNameList(finalFieldId.getName() + "[" + currentId + "]." +
                                            d.getSubnameList());
                                } else {
                                    d.addSubName(finalFieldId.getName() + "[" + currentId + "]."+
                                            d.getSubname());;
                                }
                            });
                            diffList.addAll(lt);
                        }
                    }
                    filledCurrentIds = true;
                }
            } else {
                //Simple lists
                prevListAux = listPrevious;
                currentListAux = listCurrent;
            }
            List listPreviousDiff = new ArrayList<>(prevListAux);
            listPreviousDiff.removeAll(currentListAux);
            List listCurrentDiff = new ArrayList<>(currentListAux);
            listCurrentDiff.removeAll(prevListAux);
            ListUpdate lu = new ListUpdate();
            lu.addPorpertyListUpdate(fieldTypeName,
                    listCurrentDiff, listPreviousDiff);
            diffList.add(lu);
        } else {
            //For Objects
            if (!previous.getClass().getCanonicalName().equals(current.getClass().getCanonicalName())) {
                diffList.add(new PorpertyUpdate("Class", previous.getClass().getCanonicalName(),
                        current.getClass().getCanonicalName()));
                return diffList;
            }
            for (Field field : previous.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(previous) != null && field.get(current) == null) {
                    diffList.add(new PorpertyUpdate(field.getName(), field.get(previous).toString(), "NULL"));
                } else if (field.get(previous) == null && field.get(current) != null) {
                    diffList.add(new PorpertyUpdate(field.getName(), "NULL", field.get(current).toString()));
                } else if (field.get(previous) == null && field.get(current) == null)
                    continue;

                String fieldDescription = "";
                switch (field.getType().getTypeName()) {
                    case "java.lang.String" -> {
                        if (!field.get(previous).equals(field.get(current)))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), (String) field.get(previous), (String) field.get(current)));
                    }
                    case "java.lang.Integer" -> {
                        if (Integer.parseInt(field.get(previous).toString()) != Integer.parseInt(field.get(current).toString()))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.get(previous), field.get(current)));
                    }
                    case "java.lang.Long" -> {
                        if (Long.parseLong(field.get(previous).toString()) != Long.parseLong(field.get(current).toString()))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.get(previous), field.get(current)));
                    }
                    case "java.lang.Float" -> {
                        if (Float.parseFloat(field.get(previous).toString()) != Float.parseFloat(field.get(current).toString()))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.get(previous), field.get(current)));
                    }
                    case "java.lang.Double" -> {
                        if (Double.parseDouble(field.get(previous).toString()) != Double.parseDouble(field.get(current).toString()))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.get(previous), field.get(current)));
                    }
                    case "java.lang.Short" -> {
                        if (Short.parseShort(field.get(previous).toString()) != Short.parseShort(field.get(current).toString()))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.get(previous), field.get(current)));
                    }
                    case "int" -> {
                        if (field.getInt(previous) != field.getInt(current))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.getInt(previous), field.getInt(current)));
                    }
                    case "long" -> {
                        if (field.getLong(previous) != field.getLong(current))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.getLong(previous), field.getLong(current)));
                    }
                    case "float" -> {
                        if (field.getFloat(previous) != field.getFloat(current))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.getFloat(previous), field.getFloat(current)));
                    }
                    case "double" -> {
                        if (field.getDouble(previous) != field.getDouble(current))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.getDouble(previous), field.getDouble(current)));
                    }
                    case "char" -> {
                        if (field.getChar(previous) != field.getChar(current))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.getChar(previous), field.getChar(current)));
                    }
                    case "short" -> {
                        if (field.getShort(previous) != field.getShort(current))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.getShort(previous), field.getShort(current)));
                    }
                    case "byte" -> {
                        if (field.getByte(previous) != field.getByte(current))
                            diffList.add(new PorpertyUpdate(fieldDescription + field.getName(), field.getByte(previous), field.getShort(current)));
                    }
                    default -> {
                        List<ChangeType> lt = diff(field.get(previous), field.get(current));
                        lt.forEach( d -> {
                            if (d instanceof ListUpdate) {
                                  d.addSubNameList(field.getName() + "." + d.getSubnameList());
                            } else {
                                d.addSubName(field.getName() + "." + d.getSubname());
                            }
                        });
                        diffList.addAll(lt);
                    }
                }
            }
        }
        return diffList;
    }

}