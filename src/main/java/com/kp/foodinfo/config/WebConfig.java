package com.kp.foodinfo.config;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.kp.foodinfo.argumentresolver.LoginUserArgumentResolver;
import com.kp.foodinfo.interceptor.LogInterceptor;
import com.kp.foodinfo.interceptor.AdminAuthInterceptor;
import com.kp.foodinfo.interceptor.UserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        int a = 3;

        // String b = a;

        resolvers.add(new LoginUserArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new CorsInterceptor())
//                .order(1)

        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");

        registry.addInterceptor(new UserAuthInterceptor())
                .order(2)
                .addPathPatterns("/api/user/**");

        registry.addInterceptor(new AdminAuthInterceptor())
                .order(3)
                .addPathPatterns("/api/admin/**");
    }

//    @Autowired
//    private Environment env;
//
//    @Bean
//    public AmazonS3 amazonS3Client() {
//        Regions clientRegions = Regions.valueOf(env.getRequiredProperty("s3.tree.bucket.regions"));
//
//        return AmazonS3ClientBuilder.standard()
//                .withRegion(clientRegions)
//                .enablePathStyleAccess()
//                .build();
//    }



//    @Bean
//    public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new LogFilter());
//        filterRegistrationBean.setOrder(1);
//        filterRegistrationBean.setUrlPatterns(Collections.singleton("/api/user/*"));
//
//        return filterRegistrationBean;
//    }

}
