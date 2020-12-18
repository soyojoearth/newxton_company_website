package com.newxton.nxtframework;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/9/15
 * @address Shenzhen, China
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {


    @Test
    public void helloTest() {

        Assert.assertEquals(1, 1);

    }

}
