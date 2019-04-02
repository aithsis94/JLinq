package com.jlinq.clause;

import com.jlinq.ILinqIterable;
import com.jlinq.function.IFunc1;

import java.util.Iterator;

public class Transform<T, R> implements ILinqIterable<R> {

    private final Iterable<T> childIterable;
    private final IFunc1<T, R> transformer;

    public Transform(Iterable<T> childIterable, IFunc1<T, R> transformer) {
        this.childIterable = childIterable;
        this.transformer = transformer;
    }

    @Override
    public Iterator<R> iterator() {
        return new TransformIterator(childIterable.iterator());
    }

    private class TransformIterator implements Iterator<R> {

        private Iterator<T> childIterator;

        public TransformIterator(Iterator<T> childIterator) {
            this.childIterator = childIterator;
        }

        @Override
        public boolean hasNext() {
            return this.childIterator.hasNext();
        }

        @Override
        public R next() {

            T input = this.childIterator.next();
            R output = transformer.execute(input);
            return output;
        }
    }

    @Override
    public String explain() {
        return " Transform (" + transformer + ")";
    }
}
