package com.jlinq;

import com.jlinq.clause.*;
import com.jlinq.function.IFunc1;
import com.jlinq.function.IFunc2;
import com.jlinq.tuple.Tuple2;
import com.jlinq.util.Util;

import java.util.Comparator;
import java.util.Iterator;

public class Linq<T> implements ILinqIterable<T> {

    private Iterable<T> clauses;

    private Linq(Iterable<T> iterable) {
        this.clauses = iterable;
    }

    public static <T> Linq<T> from(Iterable<T> collection) {
        return new Linq<T>(collection);
    }

    public Linq<T> where(IFunc1<T, Boolean> predicate) {
        this.clauses = new Where<>(this.clauses, predicate);
        return this;
    }

    public Linq<T> orderBy(Comparator<T> comparator) {
        this.clauses = new OrderBy<>(this.clauses, comparator);
        return this;
    }

    public Linq<T> join(Iterable<T> anotherCollection) {
        this.clauses = new Join<>(this.clauses, anotherCollection);
        return this;
    }

    public Linq<T> intersect(Iterable<T> anotherCollection) {
        this.clauses = new Intersect<>(this.clauses, anotherCollection);
        return this;
    }

    public Linq<T> intersect(Iterable<T> anotherCollection, IFunc2<T, T, Boolean> equalsComparator) {
        this.clauses = new Intersect<>(this.clauses, anotherCollection, equalsComparator);
        return this;
    }

    public Linq<T> unique(IFunc2<T, T, Boolean> equalsComparator) {
        this.clauses = new Unique<>(this.clauses, equalsComparator);
        return this;
    }

    public Linq<T> unique() {
        this.clauses = new Unique<>(this.clauses);
        return this;
    }

    public Linq<T> range(int startIndex, int count) {
        this.clauses = new Range<>(this.clauses, startIndex, count);
        return this;
    }

    public T first() {
        this.clauses = new Range<>(this.clauses, 0, 1);
        Iterator<T> iterator = this.iterator();

        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public Linq<T> first(int count) {
        this.clauses = new Range<>(this.clauses, 0, count);
        return this;
    }

    public Linq<T> last(int count) {
        this.clauses = new Last<>(this.clauses, count);
        return this;
    }

    public <R> Linq<R> transform(IFunc1<T, R> transformer) {
        return new Linq<>(new Transform<>(this, transformer));
    }

    public <R, P> Linq<P> zipWith(Iterable<R> anotherCollection, IFunc2<T, R, P> zipper) {
        return new Linq<>(new Zip<>(this, anotherCollection, zipper));
    }

    public <R> Linq<Tuple2<T, R>> zipWith(Iterable<R> anotherCollection) {
        return new Linq<>(new TupleZip<>(this, anotherCollection));
    }

    @Override
    public String explain() {
       return Util.getDesc(this.clauses);
    }

    @Override
    public Iterator<T> iterator() {
        return this.clauses.iterator();
    }
}