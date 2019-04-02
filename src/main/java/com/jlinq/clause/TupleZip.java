package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.tuple.Tuple2;
import com.jlinq.util.Util;

import java.util.Iterator;

public class TupleZip<A, B> implements ILinqIterable<Tuple2<A, B>> {

    private final Iterable<A> firstIterable;
    private final Iterable<B> secondIterable;

    public TupleZip(Iterable<A> firstIterable, Iterable<B> secondIterable) {
        this.firstIterable = firstIterable;
        this.secondIterable = secondIterable;
    }

    @Override
    public Iterator<Tuple2<A, B>> iterator() {
        return new ZipIterator(firstIterable.iterator(), secondIterable.iterator());
    }

    private class ZipIterator implements Iterator<Tuple2<A, B>> {

        private final Iterator<A> firstIterator;
        private final Iterator<B> secondIterator;

        public ZipIterator(Iterator<A> firstIterator, Iterator<B> secondIterator) {
            this.firstIterator = firstIterator;
            this.secondIterator = secondIterator;
        }

        @Override
        public boolean hasNext() {

            if (this.firstIterator.hasNext() && this.secondIterator.hasNext()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Tuple2<A, B> next() {

            A lhs = this.firstIterator.next();
            B rhs = this.secondIterator.next();
            return new Tuple2<>(lhs, rhs);
        }
    }

    @Override
    public String explain() {
        String firstExplain = Util.getDesc(this.firstIterable);
        String secondExplain = Util.getDesc(this.secondIterable);
        return " TUPLE_ZIP (" + firstExplain + ", " + secondExplain + ")";
    }
}
