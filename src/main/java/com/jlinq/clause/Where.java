package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.function.IFunc1;
import com.jlinq.util.Util;

import java.util.Iterator;

public class Where<T> implements ILinqIterable<T> {

    private final Iterable<T> childIterator;
    private final IFunc1<T, Boolean> predicate;

    public Where(Iterable<T> childIterator, IFunc1<T, Boolean> predicate) {
        this.childIterator = childIterator;
        this.predicate = predicate;
    }

    @Override
    public Iterator<T> iterator() {
        return new WhereClauseIterator(this.childIterator.iterator());
    }

    private class WhereClauseIterator implements Iterator<T> {

        private Iterator<T> iterator;
        private T nextItem;

        public WhereClauseIterator(Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {

            this.nextItem = null;

            while (this.iterator.hasNext()) {

                T item = this.iterator.next();

                if (predicate.execute(item)) {
                    this.nextItem = item;
                    return true;
                }
            }

            return false;
        }

        @Override
        public T next() {
            return nextItem;
        }
    }

    @Override
    public String explain() {
        String childExplain = Util.getDesc(this.childIterator);
        return "WHERE \n(" + childExplain + ")";
    }
}
