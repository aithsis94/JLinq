package com.jlinq.tuple;

public class Tuple2<A, B> {

    private A first;
    private B second;

    public Tuple2(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
