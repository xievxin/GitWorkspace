package com.getui.gmsm.cipher;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xievxin on 2019/1/30.
 */
public class SM2Test {

    @Test
    public void makePriAndPub() {
        SM2 sm2 = SM2.Instance();

        Set<String> priSet = new HashSet<>();
//        Set<String> pubSet = new HashSet<>();

        for (int i=0;i<100_000;i++) {
            String keys[] = sm2.makePriAndPub();
            priSet.add(keys[0]);
//            pubSet.add(keys[1]);
            System.out.println((i+1) + " times "+priSet.size()/*+","+pubSet.size()*/);
        }
    }
}