package com.dq.bootstrap;

import com.dq.core.timer.poll.BatchTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/30
 **/
@Component
public class BootStrap implements CommandLineRunner {
    @Autowired
    BatchTimer batchTimer;

    @Autowired
    Test test;

    @Override
    public void run(String... args) throws Exception {
        test.test();
        batchTimer.scan();
    }
}
