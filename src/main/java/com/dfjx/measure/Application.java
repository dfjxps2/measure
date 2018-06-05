package com.dfjx.measure;

import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Sc on 2018/5/3.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer
{
    /**
     * SrpingBoot启动方式 1 ：启动这个main
     *                   2 ：maven编译完了之后 通过cmd去项目目录下  mvn spring-boor:run
     *                   3  ： 打jar  jar在target目录下
     *                          java -jar <jar包name>
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Application.class);
        logger.info("springBoot容器启动"+
                new SimpleDateFormat("yyyymmdd-HH:MM:SS").format(new Date()));
        SpringApplication.run(Application.class, args);
    }

    /**
     * 更换springboot 启动端口
     * @param configurableEmbeddedServletContainer
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.setPort(8082);
    }


}
