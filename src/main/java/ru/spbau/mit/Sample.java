package ru.spbau.mit;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ivan on 17.05.17.
 */

interface Sample extends Collection, Comparable {
    String getStr(Collection col, Map map, int index);

    Collection getCol(Iterator begin, Iterator end);
}

