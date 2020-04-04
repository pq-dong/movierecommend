/**
 * @(#)BaseTest.java, 2018-10-20.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pqdong.movie.recommond;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pqdong.movie.recommend.MovieRecommendApplication;

/**
 * BaseTest
 * @author pqdong
 * @since 2020/04/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieRecommendApplication.class)
public class BaseTest{

    @Test
    public void test() {

    }
}