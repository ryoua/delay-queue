package com.dq;

import com.dq.core.timer.poll.Timer;
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
    Timer timer;

    @Autowired
    Test test;

    @Override
    public void run(String... args) throws Exception {
        test.test();
        new Thread(timer).start();
    }
}
