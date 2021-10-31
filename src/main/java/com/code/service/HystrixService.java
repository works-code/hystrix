package com.code.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HystrixService {

    /***
     * commandKey 는 안쓰면 자동으로 메소드 명으로 지정됨
     * 다른 패키지에 같은 매소드 명이 있다면 잘못 동작할 가능성이 있으니 지정 하는 것이 좋다.
     * execution.isolation.thread.timeoutInMilliseconds : 해당 시간 동안 메서드가 끝나지 않으면 circuit open
     * metrics.rollingStats.timeInMilliseconds : circuit open 조건 : 해당 시간 동안
     * circuitBreaker.errorThresholdPercentage : circuit open 조건 : 해당 에러 퍼센트만큼 실패시 오픈
     * circuitBreaker.requestVolumeThreshold : circuit open 조건 : 최소 판단 하기 위해 해당 요청 건수만큼 들어와야 한다.
     * circuitBreaker.sleepWindowInMilliseconds : circuit open 시 지속 될 시간
     * ms 단
     * @return
     */
    @HystrixCommand(commandKey = "A", fallbackMethod = "B", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "20"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
            // 10 초 동안 10번 호출 중 20% 실패시(2번 실패시) 10초간 fallback 메소드 호출
            // 단, 해당 메소드가 3초 안에 끝나지 않을시 fallback 메소드 호출
    })
    public String A(Boolean errYn) throws Exception{
        if(errYn){
            log.error("### call A exception");
            throw new Exception();
        }else{
            log.error("### call A");
        }
        return "A";
    }

    public String B(Boolean errYn){
        log.error("### call B");
        return "B";
    }

}
