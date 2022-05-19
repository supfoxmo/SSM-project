package com.foxmo.crm;

import org.junit.Test;

public class ContainsTest {
    @Test
    public void test1(){
        String str1 = "127.0.0.1,192.168.250.130,0.0.0.0,192.168.1.104";
        String str2 = "127.0.0.1";
        System.out.println(str1.contains(str2));
    }
}
