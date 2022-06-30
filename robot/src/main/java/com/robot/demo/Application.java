package com.robot.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;


@SpringBootApplication
@ServletComponentScan
@RestController
@EnableCaching
//@EnableScheduling
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
// @EnableMongoRepositories(basePackages = "com.kq.highnet2.reshearch.framework.test.mongodao")
public class Application {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication();
        ConfigurableApplicationContext context = application.run(Application.class, args);
        context.publishEvent(new ApplicationStartedEvent(application, args));
    }
    /*@Bean(name = "multipartResolver")  
    public MultipartResolver multipartResolver()  
    {  
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();  
        // resolver.setDefaultEncoding("UTF-8");  
        // resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常  
        // resolver.setMaxInMemorySize(40960);  
        resolver.setMaxUploadSize(10 * 1024 * 1024);// 上传文件大小 5M 5*1024*1024  
        return resolver;  
    } */

    
    @Bean
    @ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
    public RestOperations restOperations() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为 "ISO-8859-1"）
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
        }
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
    }
}
