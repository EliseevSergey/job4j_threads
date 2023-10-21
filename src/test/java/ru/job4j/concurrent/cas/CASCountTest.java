package ru.job4j.concurrent.cas;

import org.junit.jupiter.api.Test;

class CASCountTest {
    @Test
    public void whenIncrement() {
        CASCount cc = new CASCount();
        cc.increment();
        cc.increment();
        System.out.println(cc.get());

    }

}