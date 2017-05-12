package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@RestController
@RequestMapping("/users*")
@EnableDiscoveryClient
public class UserApplication {
@Autowired LoadBalancerClient loadBalancer;
 
  @RequestMapping("/hi")
  public String hi(@RequestParam(value="name", defaultValue="Artaban") String name) {
	  ServiceInstance instance = loadBalancer.choose("say-hello");
	  String greeting = (new RestTemplate()).getForObject(instance.getUri()+"/greeting",String.class);
   
    return String.format("%s, %s!", greeting, name);
  }
  @RequestMapping("/bye")
  public String bye(@RequestParam(value="name", defaultValue="Artaban") String name) {
	  ServiceInstance instance = loadBalancer.choose("say-bye");
	  String greeting = (new RestTemplate()).getForObject(instance.getUri()+"/greeting",String.class);
      return String.format("%s, %s!", greeting, name);
  }
  

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}

