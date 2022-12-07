package com.microservices.demo.common.util;

import java.util.ArrayList;
import java.util.List;

/*
 * we create this class as singleton class instead of adding public static methods
 * */
public class CollectionsUtil {

    private CollectionsUtil() {
    }

    private static class CollectionsUtilHolder {
        static final CollectionsUtil INSTANCE = new CollectionsUtil();
    }

    public static CollectionsUtil getInstance() {
        return CollectionsUtilHolder.INSTANCE;
    }

    public <T> List<T> getListFromIterable(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }


}
