package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService service;

    //@RequestMapping(value="/add",method=RequestMethod.POST)
    @PostMapping("/add")
    public boolean add(@RequestBody Dept dept) {
        return service.add(dept);
    }

    //@RequestMapping(value="/get/{id}",method=RequestMethod.GET)
    @GetMapping("/get/{id}")
    //一旦调用服务方法失败并抛出了错误信息后。会自动调用@HystrixCommmand标注好的fallbackMethod调用类中的指定方法
    @HystrixCommand(fallbackMethod = "processHystrix_Get")
    public Dept get(@PathVariable("id") Long id) {
        Dept dept = this.service.get(id);
        if(dept == null)
            throw new RuntimeException("该ID：" + id + "没有对应信息");
        return dept;
    }

    public Dept processHystrix_Get(@PathVariable("id") Long id) {
        return new Dept().setDeptno(id)
                .setDname("该ID：" + id + "没有对应信息,null--@HystrixCommand")
                .setDb_source("no this dattabase in Mysql");
    }

    //@RequestMapping(value="/list",method=RequestMethod.GET)
    @GetMapping("/list")
    public List<Dept> list() {
        return service.list();
    }


}