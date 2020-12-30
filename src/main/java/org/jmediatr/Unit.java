package org.jmediatr;

/**
 * 无返回类型的辅助类型，作用类型void
 * @author rende
 */
public class Unit implements Comparable<Unit> {
    public static final Unit VALUE = new Unit();

    private Unit() {
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "()";
    }

    @Override
    public int compareTo(Unit o) {
        return 0;
    }
}
