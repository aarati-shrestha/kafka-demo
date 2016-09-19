package com.demo.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.domain.Status;
import com.demo.domain.Vote;
import com.demo.kafka.SpringBootKafkaProducer;

@RestController
public class SpringBootKafkaController {
 
    @Autowired
    SpringBootKafkaProducer springBootKafkaProducer;
 
    @RequestMapping("/vote")
    public Status vote() throws ExecutionException, InterruptedException {
 
        springBootKafkaProducer.send("Test");
 
        return new Status("ok");
    }
}
