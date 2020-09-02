package com.dq.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * * @Author: RyouA
 * * @Date: 2020/9/2
 **/
public class CollectionUtil {
    public static List removeNullElement(List list) {
        List result = new ArrayList();
        for (Object o : list) {
            if (!StringUtil.isNull(o)) {
                result.add(o);
            }
        }
        return result;
    }
}
