package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Range<T> implements ILinqIterable<T> {

    private final Iterable<T> childIterable;
    private final int startIndex;
    private final int itemCount;

    private List<T> childItems = null;

    public Range(Iterable<T> childIterable, int startIndex, int itemCount) {
        this.childIterable = childIterable;
        this.startIndex = startIndex;
        this.itemCount = itemCount;

        if (itemCount < 0) {
            throw new IllegalArgumentException("itemCount should be positive");
        }

        if (startIndex < 0) {
            throw new IllegalArgumentException("startIndex should be positive");
        }
    }

    @Override
    public synchronized Iterator<T> iterator() {

        if (this.childItems == null) {

            this.childItems = new ArrayList<>();

            if (this.itemCount > 0) {

                boolean traversedTillStart = false;
                int currIndex = -1;


                for (T item : this.childIterable) {

                    currIndex++;

                    if (traversedTillStart) {

                        this.childItems.add(item);
                        if (this.childItems.size() == this.itemCount) {
                            break;
                        }

                    } else {

                        if (currIndex == this.startIndex) {
                            traversedTillStart = true;

                            this.childItems.add(item);
                            if (this.childItems.size() == this.itemCount) {
                                break;
                            }
                        }
                    }
                }
            }

            this.childItems = Collections.unmodifiableList(this.childItems);
        }

        return this.childItems.iterator();
    }

    @Override
    public String explain() {
        String childExplain = Util.getDesc(this.childIterable);
        return "RANGE (" + startIndex + ", " + itemCount + " of ("+ childExplain + "))";
    }
}
