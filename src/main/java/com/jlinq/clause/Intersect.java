package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.function.IFunc2;
import com.jlinq.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Intersect<T> implements ILinqIterable<T> {

    private final Iterable<T> firstIterable;
    private final Iterable<T> secondIterable;
    private final IFunc2<T, T, Boolean> equalsComparator;

    private List<T> intersectedItems = null;

    public Intersect(Iterable<T> firstIterable, Iterable<T> secondIterable, IFunc2<T, T, Boolean> equalsComparator) {
        this.firstIterable = firstIterable;
        this.secondIterable = secondIterable;
        this.equalsComparator = equalsComparator;
    }

    public Intersect(Iterable<T> firstIterable, Iterable<T> secondIterable) {
        this.firstIterable = firstIterable;
        this.secondIterable = secondIterable;
        this.equalsComparator = null;
    }

    @Override
    public synchronized Iterator<T> iterator() {

        if (this.intersectedItems == null) {

            this.intersectedItems = new ArrayList<>();

            for (T lhs : this.firstIterable) {
                for (T rhs : this.secondIterable) {

                    if (this.equalsComparator != null) {
                        if (this.equalsComparator.execute(lhs, rhs)) {
                            this.intersectedItems.add(lhs);
                        }
                    } else if (lhs == rhs) {
                        this.intersectedItems.add(lhs);
                    }
                }
            }

            this.intersectedItems = Collections.unmodifiableList(this.intersectedItems);
        }

        return this.intersectedItems.iterator();
    }

    @Override
    public String explain() {
        String firstExplain = Util.getDesc(this.firstIterable);
        String secondExplain = Util.getDesc(this.secondIterable);
        return " INTERSECT (" + firstExplain + ", " + secondExplain + ")";
    }
}
