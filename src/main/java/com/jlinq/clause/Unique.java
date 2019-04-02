package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.function.IFunc2;
import com.jlinq.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Unique<T> implements ILinqIterable<T> {

    private final Iterable<T> childIterable;
    private final IFunc2<T, T, Boolean> equalsComparator;

    private List<T> childItems = null;

    public Unique(Iterable<T> childIterable) {
        this.childIterable = childIterable;
        this.equalsComparator = null;
    }

    public Unique(Iterable<T> childIterable, IFunc2<T, T, Boolean> equalsComparator) {
        this.childIterable = childIterable;
        this.equalsComparator = equalsComparator;
    }

    @Override
    public synchronized Iterator<T> iterator() {

        if (this.childItems == null) {

            this.childItems = new ArrayList<>();

            for (T lhs : this.childIterable) {

                boolean add = true;

                for (T rhs : childItems) {

                    if (this.equalsComparator != null) {
                        if (this.equalsComparator.execute(lhs, rhs)) {
                            add = false;
                            break;
                        }
                    } else if (lhs == rhs) {
                        add = false;
                        break;
                    }
                }

                if (add) {
                    this.childItems.add(lhs);
                }
            }

            this.childItems = Collections.unmodifiableList(this.childItems);
        }

        return childItems.iterator();
    }

    @Override
    public String explain() {

        String childExplain = Util.getDesc(this.childIterable);
        return "UNIQUE (" + childExplain + ")";
    }
}
