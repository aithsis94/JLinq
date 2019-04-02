package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Last<T> implements ILinqIterable<T> {

    private final Iterable<T> childIterable;
    private final int itemCount;

    private List<T> childItems = null;

    public Last(Iterable<T> childIterable, int itemCount) {
        this.childIterable = childIterable;
        this.itemCount = itemCount;

        if (itemCount < 0) {
            throw new IllegalArgumentException("itemCount should be positive");
        }
    }

    @Override
    public synchronized Iterator<T> iterator() {

        if (this.childItems == null) {

            if (this.itemCount > 0) {

                List<T> items = new ArrayList<>();
                for (T item : this.childIterable) {
                    items.add(item);
                }

                int iteratorSize = items.size();

                int startIndex = Math.max(0, iteratorSize - this.itemCount);
                this.childItems = items.subList(startIndex, iteratorSize);
            } else {
                this.childItems = new ArrayList<>();
            }

            this.childItems = Collections.unmodifiableList(this.childItems);
        }

        return this.childItems.iterator();
    }

    @Override
    public String explain() {
        String childExplain = Util.getDesc(this.childIterable);
        return "LAST (" + itemCount + " of (" + childExplain + "))";
    }
}
