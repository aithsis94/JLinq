package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.function.IFunc2;
import com.jlinq.util.Util;

import java.util.Iterator;

public class Zip<A, B, C> implements ILinqIterable<C> {

    private final Iterable<A> firstIterable;
    private final Iterable<B> secondIterable;

    private final IFunc2<A, B, C> zipper;

    public Zip(Iterable<A> firstIterable, Iterable<B> secondIterable, IFunc2<A, B, C> zipper) {
        this.firstIterable = firstIterable;
        this.secondIterable = secondIterable;
        this.zipper = zipper;
    }

    @Override
    public Iterator<C> iterator() {
        return new ZipIterator(firstIterable.iterator(), secondIterable.iterator());
    }

    private class ZipIterator implements Iterator<C> {

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
        public C next() {

            A lhs = this.firstIterator.next();
            B rhs = this.secondIterator.next();
            return zipper.execute(lhs, rhs);
        }
    }

    @Override
    public String explain() {
        String secondExplain = Util.getDesc(this.secondIterable);
        return " ZIP (" + secondExplain + ")";
    }
}
