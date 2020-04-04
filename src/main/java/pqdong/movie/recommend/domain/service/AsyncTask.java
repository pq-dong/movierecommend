package pqdong.movie.recommend.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * AsyncTask
 *
 * @author pqdong
 * @description 作为一组领域对象，用以支持测试
 * @since 2020/04/03
 */

@Component
@Slf4j
public class AsyncTask{
    private static Random random = new Random();

    @Async("taskExecutor")
    public void doTaskOne() throws Exception {
        log.info("开始做任务一");
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(random.nextInt(100));
        }catch (InterruptedException e){
            Thread.sleep(200);
        }
        long end = System.currentTimeMillis();
        log.info("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    public void doTaskTwo() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(random.nextInt(100));
        }catch (InterruptedException e){
            Thread.sleep(200);
        }
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    public void doTaskThree() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(random.nextInt(100));
        }catch (InterruptedException e){
            Thread.sleep(200);
        }
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }
}
