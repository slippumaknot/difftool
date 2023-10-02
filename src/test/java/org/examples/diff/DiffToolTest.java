package org.examples.diff;

import org.examples.diff.exceptions.DuplicateIdTypeException;
import org.examples.diff.exceptions.NoIdTypeInObjectsListException;
import org.examples.diff.model.ChangeType;
import org.examples.diff.objects.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class DiffToolTest {

    @Test
    public void DiffSimpleObjects() throws IllegalAccessException {
        Subscription subscriptionActive = new Subscription(1, "ACTIVE", 0);
        Subscription subscriptionExpired = new Subscription(1, "EXPIRED", 10);

        List<String> listServices1 = Arrays.asList("Interior/Exterior Wash","VisualInspection") ;
        List<String> listServices2 = Arrays.asList("OilChange","Wheels Change", "VisualInspection") ;

        List<Vehicle> listVehicle1 = Arrays.asList(
                new Vehicle("1","My Car", "Ford", 1000L),
                new Vehicle("2", "murcielago", "lamborghini", 2000L)
        ) ;
        List<Vehicle> listVehicle2 = Arrays.asList(
                new Vehicle("1","23 Ferrari 296 GTS", "Ferrari", 1500L),
                new Vehicle("2", "diablo", "lamborghini", 2000L),
                new Vehicle("3", "Camaro", "Chevrolet", 2000L),
                new Vehicle("6", "maybach1", "Maybach", 21000L)
        ) ;

        Person p1 = new Person("James", "lastNameX", 30, subscriptionActive,
                listServices1, listVehicle1);
        Person p2 = new Person("Jim", "lastNameX", 30, subscriptionExpired,
                listServices2, listVehicle2);

        DiffTool dt = new DiffTool();
        dt.diff(p1, p2).forEach(ct -> System.out.printf(ct.toString()));
    }

    @Test
    public void DiffSimpleObjectsExceptions() throws IllegalAccessException {
        List<PartNoId> listNoIdpart1 = Arrays.asList(
                new PartNoId("name1","type1"),
                new PartNoId("name2","type2")) ;
        List<PartNoId> listNoIdpart2 = Arrays.asList(
                new PartNoId("name1x","type1x"),
                new PartNoId("name2x","type2x")) ;

        List<PartDuplicateId> listpartDuplicate1 = Arrays.asList(
                new PartDuplicateId("key1", "id1","type1"),
                new PartDuplicateId("key2", "id1","type2")) ;
        List<PartDuplicateId> listpartDuplicate2= Arrays.asList(
                new PartDuplicateId("key1", "id1","type1"),
                new PartDuplicateId("key2", "id1","type2")) ;

        Car c1 = new Car("Car1", listNoIdpart1, null);
        Car c2 = new Car("Car2", listNoIdpart2, null);

        DiffTool dt = new DiffTool();
        NoIdTypeInObjectsListException thrownNoId = Assertions.assertThrows(NoIdTypeInObjectsListException.class, () -> {
            List<ChangeType> result = dt.diff(c1, c2);
        });

        Car c3 = new Car("Car1", null, listpartDuplicate1);
        Car c4 = new Car("Car2", null, listpartDuplicate2);

        DuplicateIdTypeException thrownDupId = Assertions.assertThrows(DuplicateIdTypeException.class, () -> {
            List<ChangeType> result = dt.diff(c3, c4);
        });

    }
}