package com.foxmo.crm;

import com.foxmo.crm.commons.utils.UUIDUtils;

public class UUIDTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUIDUtils.getUUID());

        }
    }
}
