package com.amhfilho.finsys;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isNullOrEmpty(Collection<?> collection){
        return collection == null || collection.size()==0;
    }

    public static boolean isNullOrEmpty(Object[] array){
        return array == null || array.length==0;
    }
}
