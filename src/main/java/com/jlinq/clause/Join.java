package com.jlinq.clause;


import com.jlinq.ILinqIterable;
import com.jlinq.util.Util;

import java.util.Iterator;

public class Join<T> implements ILinqIterable<T> {

    private final Iterable<T> firstIterable;
    private final Iterable<T> secondIterable;

    public Join(Iterable<T> firstIterable, Iterable<T> secondIterable) {
        this.firstIterable = firstIterable;
        this.secondIterable = secondIterable;
    }

    @Override
    public Iterator<T> iterator() {
        return new JoinIterator(this.firstIterable.iterator(), this.secondIterable.iterator());
    }

    private class JoinIterator implements Iterator<T> {

        private Iterator<T> firstIterator;
        private Iterator<T> secondIterator;

        private boolean firstIteratorFinished = false;


        public JoinIterator(Iterator<T> firstIterator, Iterator<T> secondIterator) {
            this.firstIterator = firstIterator;
            this.secondIterator = secondIterator;
        }

        @Override
        public boolean hasNext() {

            if (!this.firstIteratorFinished) {

                if (this.firstIterator.hasNext()) {
                    return true;
                } else {
                    this.firstIteratorFinished = true;
                    return this.secondIterator.hasNext();
                }
            } else {
                return this.secondIterator.hasNext();
            }
        }

        @Override
        public T next() {

            if (!firstIteratorFinished) {
                return this.firstIterator.next();
            } else {
                return this.secondIterator.next();
            }
        }
    }

    @Override
    public String explain() {
        String firstExplain = Util.getDesc(this.firstIterable);
        String secondExplain = Util.getDesc(this.secondIterable);
        return " JOIN (" + firstExplain + ", " + secondExplain + ")";
    }
}
