package com.getui.gmsm.bouncycastle.util;

public interface Selector
    extends Cloneable
{
    boolean match(Object obj);

    Object clone();
}
