package com.robot.demo.timer;

import com.robot.demo.timer.service.ITimerService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JobHandler {
    private int total=1;

    private Integer count=0;
    @Resource
    ITimerService timerService;


    /**
     * 测试
     * @return
     * @throws Exception
     */
    @XxlJob(value = "test")
    public ReturnT<String> test() throws Exception{
        String jobParam = XxlJobHelper.getJobParam();
        System.out.println(jobParam);
        ReturnT<String> returnT = new ReturnT<>();
        try {
            System.out.println("total = " + total);
            System.out.println("count = " + count);
            System.out.println(123);
        }catch (Exception e){
            returnT.setCode(ReturnT.FAIL_CODE);
            returnT.setMsg("trackId="+" msg="+e.getMessage());
            return returnT;
        }
        returnT.setCode(ReturnT.SUCCESS_CODE);
        returnT.setMsg("trackId="+" msg="+"调用成功");
        return returnT;
    }






}
