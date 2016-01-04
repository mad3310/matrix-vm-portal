package com.letv.lcp.openstack.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public class CollectionUtil {
    public static <T> List<T> toList(Iterable<T> iterable) {
        if (iterable instanceof List) {
            return (List<T>) iterable;
        }

        List<T> list = new LinkedList<T>();
        for (Iterator<T> it = iterable.iterator(); it.hasNext(); ) {
            list.add(it.next());
        }
        return list;
    }
}
