package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.util.Util;

import java.util.*;

public class OrderBy<T> implements ILinqIterable<T> {

    private final Iterable<T> iterable;
    private final Comparator<T> comparator;
    private List<T> childItems;

    public OrderBy(Iterable<T> iterable, Comparator<T> comparator) {
        this.iterable = iterable;
        this.comparator = comparator;
    }

    @Override
    public synchronized Iterator<T> iterator() {

        if (this.childItems == null) {

            Iterator<T> iterator = this.iterable.iterator();

            this.childItems = new ArrayList<T>();

            while (iterator.hasNext()) {
                this.childItems.add(iterator.next());
            }

            if (comparator != null)
                this.childItems.sort(this.comparator);
            else {
                this.childItems.sort(null);
            }

            this.childItems = Collections.unmodifiableList(this.childItems);
        }

        return this.childItems.iterator();
    }

    @Override
    public String explain() {
        String childExplain = Util.getDesc(this.iterable);
        return "ORDER_BY (" + childExplain + ")";
    }
}