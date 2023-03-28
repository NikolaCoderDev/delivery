package com.guavaPay.employeeservice.config.feignConfig;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class OrderFeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        if (RequestContextHolder.getRequestAttributes() != null && RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            String authorization = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(authorization)) {
                template.header("Authorization", authorization);
            }
        }
    }
}