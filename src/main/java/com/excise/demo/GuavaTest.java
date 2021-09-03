package com.excise.demo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.BoundType;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RangeSet;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.Table;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeMultiset;
import com.google.common.collect.TreeRangeMap;
import com.google.common.collect.TreeRangeSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sun.security.provider.certpath.Vertex;

/**
 * Guava工具使用示例
 * @author liuzhiyang<liuzhiyang @ kuaishou.com>
 * Created on 2021/9/2
 */
@AllArgsConstructor
@Getter
@Setter
public class GuavaTest {

    private String var1;

    private Long var2;

    private Integer var3;

    public static void main(String[] args) throws Exception {
        GuavaTest test = new GuavaTest("b", 12L, 4);

//        test.option();

//        test.checkArgument();

//        test.object(new GuavaTest("b", null, 6));

//        test.ordering();

//        test.exception();

//        test.collection();

        test.collectionType();
    }

    /**
     * 使用和避免null
     */
    public void option() {
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
     * 前置检查
     */
    public void checkArgument() {
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

    /**
     * 常见Object方法
     */
    public void object(GuavaTest that) {
        // equals
        boolean var1 = Objects.equal("a", "a");// returns true
        boolean var2 = Objects.equal(null, "a");// returns false
        boolean var3 = Objects.equal("a", null);// returns false
        boolean var4 = Objects.equal(null, null);// returns true
        System.out.println(var1 + " " + var2 + " " + var3 + " " + var4);

        // 计算出合理的、顺序敏感的散列值
        int hashCode = Objects.hashCode(this.var1, this.var2, this.var3);
        System.out.println(hashCode);

        // toString
        // Returns "ClassName{x=1}"
        String var5 = Objects.toStringHelper(this).add("var1", this.var1).add("var2", this.var2).add("var3", this.var3).toString();
        // Returns "MyObject{x=1}"
        String var6 = Objects.toStringHelper("MyObject").add("x", 1).toString();
        System.out.println(var5 + "\n" + var6);

        // 执行比较操作直至发现非零的结果，在那之后的比较输入将被忽略
        int result = ComparisonChain.start()
                .compare(this.var1, that.var1)
                .compare(this.var2, that.var2)
                .compare(this.var3, that.var3, Ordering.natural().nullsLast())
                .result();
        System.out.println(result);
    }

    /**
     * 排序
     */
    public void ordering() {
        List<Integer> var1 = Arrays.asList(3, 10, 7);
        System.out.println(Ordering.natural().nullsFirst().isOrdered(var1));
        System.out.println(Ordering.natural().reverse().nullsFirst().sortedCopy(var1));
        System.out.println(Arrays.toString(var1.toArray()));
    }

    /**
     * 异常
     */
    public void exception() throws IOException {
        // Throwable类型为X才抛出
        Throwables.propagateIfInstanceOf(new SQLException(), IOException.class);

        // Throwable类型为Error或RuntimeException才抛出
        // 常见的运行时异常：NullPointerException、ClassCastException、IllegalArgumentException、IndexOutOfBoundsException
        Throwables.propagateIfPossible(new IOException());

        // Throwable类型为X（IOException）, Error或RuntimeException才抛出
        Throwables.propagateIfPossible(new SQLException(), IOException.class);

        // 如果Throwable是Error或RuntimeException，直接抛出；否则把Throwable包装成RuntimeException抛出
        Throwables.propagate(new SQLException());

        System.out.println("test");
    }

    /**
     * 不可变集合
     */
    public void collection() {
        ImmutableSortedSet<String> var1 = ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");
        ImmutableMap<String, Integer> var2 = ImmutableMap.of("a", 1, "b", 2);
        ImmutableList<Integer> var3 = ImmutableList.of(3, 5, 7);
        var3.add(0); // 添加元素抛UnsupportedOperationException
    }

    /**
     * 新集合类型
     */
    public void collectionType() {
        List<String> list = Arrays.asList("aa", "bb", "aa");

        // Multiset可以多次添加相等的元素
        Multiset<String> var1 = HashMultiset.create();
        var1.addAll(list);
        System.out.println("var1: " + var1.count("aa"));
        System.out.println("var1: " + var1.elementSet().toString()); // 不重复元素集合

        // SortedMultiset支持高效地获取指定范围的子集
        SortedMultiset<String> var2 = TreeMultiset.create();
        var2.addAll(list);
        System.out.println("var2: " + var2.subMultiset("aa", BoundType.CLOSED, "bb", BoundType.OPEN));

        // Multimap键-值集合映射
        TreeMultimap<String, String> var3 = TreeMultimap.create();
        var3.put("b", "bb");
        var3.put("a", "aa1");
        var3.put("a", "aa2");
        for (String key : var3.keySet()) {
            NavigableSet<String> vals = var3.get(key);
            System.out.println("var3: " + vals);
        }
        ArrayListMultimap<String, String> var4 = ArrayListMultimap.create(); // 保序（values有序，aa2 -> aa1）
        var4.put("b", "bb");
        var4.put("a", "aa2");
        var4.put("a", "aa1");
        for (String key : var4.keySet()) {
            List<String> vals = var4.get(key);
            System.out.println("var4: " + vals);
        }
        HashMultimap<String, String> var5 = HashMultimap.create(); // 去重
        var5.put("b", "bb");
        var5.put("a", "aa1");
        var5.put("a", "aa1");
        for (String key : var5.keySet()) {
            Set<String> vals = var5.get(key);
            System.out.println("var5: " + vals);
        }

        // BiMap实现键值对的双向映射需要维护两个单独的map
        BiMap<String, Integer> var6 = HashBiMap.create();
        var6.put("a", 1);
        var6.put("a", 3);
        var6.put("b", 2);
        for (Integer val : var6.values()) {
            System.out.print("var6: " + val + " -> ");
            String key = var6.inverse().get(val);
            System.out.println("var6: " + key);
        }

        // Table就是一张数据表，类似于Map<String, Map<String, String>>
        Table<Integer, Integer, String> var7 = HashBasedTable.create();
        var7.put(1, 1, "aa");
        var7.put(1, 3, "cc");
        var7.put(3, 3, "ff");

        Map<Integer, String> row = var7.row(1);
        Map<Integer, String> column = var7.column(3);
        System.out.println("var7: " + row);
        System.out.println("var7: " + column);

        // 特殊的Map：键是类型，而值是符合键所指类型的对象
        ClassToInstanceMap<Integer> var8 = MutableClassToInstanceMap.create();
        var8.putInstance(Integer.class, Integer.valueOf(0));
        var8.putInstance(Integer.class, Integer.valueOf(1));
        System.out.println("var8: " + var8);

        // RangeSet描述了一组不相连的、非空的区间
        RangeSet<Integer> var9 = TreeRangeSet.create();
        var9.add(Range.closed(1, 10)); // {[1,10]}
        var9.add(Range.closedOpen(11, 15));// 不相连区间:{[1,10], [11,15)}
        var9.add(Range.closedOpen(15, 20)); // 相连区间; {[1,10], [11,20)}
        var9.add(Range.openClosed(0, 0)); // 空区间; {[1,10], [11,20)}
        var9.remove(Range.open(5, 10)); // 分割[1, 10]; {[1,5], [10,10], [11,20)}
        System.out.println("var9: " + var9);
        System.out.println("var9: " + var9.contains(10));
        System.out.println("var9: " + var9.rangeContaining(10));
        System.out.println("var9: " + var9.encloses(Range.closed(2, 3)));
        System.out.println("var9: " + var9.span()); // 区间涵盖最大范围

        // RangeMap描述了”不相交的、非空的区间”到特定值的映射。和RangeSet不同，RangeMap不会合并相邻的映射，即便相邻的区间映射到相同的值
        RangeMap<Integer, String> var10 = TreeRangeMap.create();
        var10.put(Range.closed(1, 10), "foo"); // {[1,10] => "foo"}
        var10.put(Range.open(3, 6), "bar"); // {[1,3] => "foo", (3,6) => "bar", [6,10] => "foo"}
        var10.put(Range.open(10, 20), "foo"); // {[1,3] => "foo", (3,6) => "bar", [6,10] => "foo", (10,20) => "foo"}
        var10.remove(Range.closed(5, 11)); // {[1,3] => "foo", (3,5) => "bar", (11,20) => "foo"}
        Map<Range<Integer>, String> asMapOfRanges = var10.asMapOfRanges();
        for (Range r : asMapOfRanges.keySet()) {
            System.out.println("var9: " + r + asMapOfRanges.get(r));
        }
    }

}
