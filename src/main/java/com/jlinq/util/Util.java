package com.jlinq.util;

import com.jlinq.ILinqIterable;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static <T> List<T> getList(Iterable<T> iterable) {

        List<T> list = new ArrayList<>();

        for (T item : iterable) {
            list.add(item);
        }

        return list;
    }

    public static String getDesc(Iterable iterable) {

        String desc = null;

        if (iterable instanceof ILinqIterable) {
            desc = ((ILinqIterable) iterable).explain();
        } else {
            desc = iterable.getClass().getName();
            desc = desc.substring(desc.lastIndexOf('.') + 1);
        }

        return desc;
    }

    public static String prettifyDesc(String desc) {

        String indentTab = "\t";
        StringBuilder indentBuilder = new StringBuilder();

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < desc.length(); i++) {

            char ch = desc.charAt(i);

            if (ch == '(') {
                builder.append(" {\n");
                indentBuilder.append(indentTab);
                builder.append(indentBuilder);
            } else if (ch == ')') {
                builder.append("}\n");
                indentBuilder.delete(indentBuilder.length() - indentTab.length(), indentBuilder.length());
                builder.append(indentBuilder);
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }
}
