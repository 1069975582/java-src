/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang;
import java.util.*;

/**
 * link: https://www.cnblogs.com/lin-jing/p/8278271.html
 *
 * 对实现此接口的对象会强制进行整体排序. compareTo方法称为自然排序方法, 使用
 * 此方法排序的方式称为自然排序.
 * This interface imposes a total ordering on the objects of each class that
 * implements it.  This ordering is referred to as the class's <i>natural
 * ordering</i>, and the class's <tt>compareTo</tt> method is referred to as
 * its <i>natural comparison method</i>.<p>
 *
 * 实现此接口的对象集合(数组)可以使用Collections.sort()/Arrays.sort()进行自动排序.
 * 当实现了此接口的对象用作有序集合(SortedMap/SortedSet)中的键/元素时,不需要指定比较器.
 * Lists (and arrays) of objects that implement this interface can be sorted
 * automatically by {@link Collections#sort(List) Collections.sort} (and
 * {@link Arrays#sort(Object[]) Arrays.sort}).  Objects that implement this
 * interface can be used as keys in a {@linkplain SortedMap sorted map} or as
 * elements in a {@linkplain SortedSet sorted set}, without the need to
 * specify a {@linkplain Comparator comparator}.<p>
 *
 * 对于类C(Collections)中的每一个元素来说, 当且仅当e1.compareTo(e2) == 0与e1.equals(e2)存在相同值时,
 * 类C的自然排序才叫做与equals一致. null不是任何class的实例, 即使e.equals(null)返回false,
 * e.compareTo(null)也会抛出NullPointerException
 * The natural ordering for a class <tt>C</tt> is said to be <i>consistent
 * with equals</i> if and only if <tt>e1.compareTo(e2) == 0</tt> has
 * the same boolean value as <tt>e1.equals(e2)</tt> for every
 * <tt>e1</tt> and <tt>e2</tt> of class <tt>C</tt>.  Note that <tt>null</tt>
 * is not an instance of any class, and <tt>e.compareTo(null)</tt> should
 * throw a <tt>NullPointerException</tt> even though <tt>e.equals(null)</tt>
 * returns <tt>false</tt>.<p>
 *
 * 强烈推荐自然排序与equals一致(虽然不是必须的). 这是因为排序的set/map没有
 * It is strongly recommended (though not required) that natural orderings be
 * consistent with equals.  This is so because sorted sets (and sorted maps)
 * without explicit comparators behave "strangely" when they are used with
 * elements (or keys) whose natural ordering is inconsistent with equals.  In
 * particular, such a sorted set (or sorted map) violates the general contract
 * for set (or map), which is defined in terms of the <tt>equals</tt>
 * method.<p>
 *
 * For example, if one adds two keys <tt>a</tt> and <tt>b</tt> such that
 * {@code (!a.equals(b) && a.compareTo(b) == 0)} to a sorted
 * set that does not use an explicit comparator, the second <tt>add</tt>
 * operation returns false (and the size of the sorted set does not increase)
 * because <tt>a</tt> and <tt>b</tt> are equivalent from the sorted set's
 * perspective.<p>
 *
 * Virtually all Java core classes that implement <tt>Comparable</tt> have natural
 * orderings that are consistent with equals.  One exception is
 * <tt>java.math.BigDecimal</tt>, whose natural ordering equates
 * <tt>BigDecimal</tt> objects with equal values and different precisions
 * (such as 4.0 and 4.00).<p>
 *
 * For the mathematically inclined, the <i>relation</i> that defines
 * the natural ordering on a given class C is:<pre>
 *       {(x, y) such that x.compareTo(y) &lt;= 0}.
 * </pre> The <i>quotient</i> for this total order is: <pre>
 *       {(x, y) such that x.compareTo(y) == 0}.
 * </pre>
 *
 * It follows immediately from the contract for <tt>compareTo</tt> that the
 * quotient is an <i>equivalence relation</i> on <tt>C</tt>, and that the
 * natural ordering is a <i>total order</i> on <tt>C</tt>.  When we say that a
 * class's natural ordering is <i>consistent with equals</i>, we mean that the
 * quotient for the natural ordering is the equivalence relation defined by
 * the class's {@link Object#equals(Object) equals(Object)} method:<pre>
 *     {(x, y) such that x.equals(y)}. </pre><p>
 *
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <T> the type of objects that this object may be compared to
 *
 * @author  Josh Bloch
 * @see java.util.Comparator
 * @since 1.2
 */
public interface Comparable<T> {
    /**
     *
     * 为指定的对象排序时,返回一个负数、0或者一个正数,表示此对象小于、等于或者大于另外一个.
     *
     * 实现类需要确保 sgn(x.compareTo(y)) == -sgn(y.compareTo(x))(这意味着x.compareTo(y)抛出一个异常, y.compareTo(x)
     * 也需要抛出一个异常)
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * 实现类还需要确保关系是可传递的: x.compareTo(y)>0, y.compareTo(z)>0, 那么x.compareTo(z)>0
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * 最后,实现者需要确保 x.compareTo(y)==0,对所有的z来说,都有sgn(x.compareTo(z)) == sgn(y.compareTo(z))
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * 强烈建议(但不是必须的)(x.compareTo(y) == 0) == (x.equals(y)),正常来说,任何实现Comparable接口但是违反此条件的
     * 类,都需要清楚的指出.推荐如下描述: "注意：此类具有与 equals 不一致的自然排序。"
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * 在前面的描述中，符号 sgn(expression) 指定 signum 数学函数，该函数根据 expression 的值是负数、零还是正数，
     * 分别返回 -1、0 或 1 中的一个值。
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param   o the object to be compared. 被比较的对象
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     *         抛出异常:ClassCastException - 如果指定对象的类型不允许它与此对象进行比较。
     */
    public int compareTo(T o);
}
