package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
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

    @Autowired
    private DiscoveryClient client;

    //@RequestMapping(value="/add",method=RequestMethod.POST)
    @PostMapping("/add")
    public boolean add(@RequestBody Dept dept) {
        return service.add(dept);
    }

    //@RequestMapping(value="/get/{id}",method=RequestMethod.GET)
    @GetMapping("/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    //@RequestMapping(value="/list",method=RequestMethod.GET)
    @GetMapping("/list")
    public List<Dept> list() {
        return service.list();
    }

    //@RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)、
    @GetMapping("/discovery")
    public Object discovery() {
        List<String> list = client.getServices();
        System.out.println("**********" + list);
        List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
        for (ServiceInstance element : srvList) {
            System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
                    + element.getUri());
        }
        return this.client;

    }

}