package pqdong.movie.recommond.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pqdong.movie.recommend.domain.service.AsyncTask;
import pqdong.movie.recommond.BaseTest;


public class AsyncTaskTest extends BaseTest {
    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void testAsyncTasks() throws Exception {
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
    }
}
