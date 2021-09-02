package com.excise.demo;

import java.util.Arrays;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;

/**
 * @author liuzhiyang<liuzhiyang @ kuaishou.com>
 * Created on 2021/9/2
 */
public class GuavaTest {

    public static void main(String[] args) {
//        option();
        checkArgument();
    }

    /**
     * Optional 处理null
     */
    public static void option() {
        Optional<Integer> optional1 = Optional.of(1);
        System.out.println(optional1.get());

        // of构造会检查null
        Optional<Integer> optional2 = Optional.fromNullable(null);
        System.out.println(optional2.or(4));

        if (optional2.isPresent()) {
            System.out.println("optional2 not null");
        }
    }

    /**
     * Preconditions 检查
     */
    public static void checkArgument() {
        // 检查false 抛出IllegalArgumentException异常
        Preconditions.checkArgument(true, "error message");

        // 检查null 抛出NullPointerException异常
        Preconditions.checkNotNull(new Integer(3));

        // 检查index >= 0 且 index < size 抛出IndexOutOfBoundsException
        Preconditions.checkElementIndex(3, 4);

        // 检查index >= 0 且 index <= size 抛出IndexOutOfBoundsException
        Preconditions.checkPositionIndex(4, 4);

        // 检查start 和 end 在 [0, size] 之间 抛出IndexOutOfBoundsException
        Preconditions.checkPositionIndexes(0, 4, 4);
    }

    public static void ordering() {
        System.out.println(Ordering.natural().nullsFirst().isOrdered(Arrays.asList(3, 5, 7)));
        System.out.println(Ordering.natural().nullsFirst().sortedCopy(Arrays.asList(3, 10, 7)));
    }

}
