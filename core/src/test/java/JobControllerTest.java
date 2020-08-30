
import com.dq.Application;
import com.dq.controller.JobController;
import com.dq.model.JobStatus;import com.dq.model.Job;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JobControllerTest {
    @Autowired
    JobController jobController;

    @Test
    public void cpuLoadTest() throws Exception {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Job job = new Job();
            job.setId(UUID.randomUUID().toString());
            job.setTopic("test");
            job.setDelay(100L);
            job.setTtr(10L);
            job.setBody("test");
            jobController.addJob(job);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
