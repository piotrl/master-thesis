package net.piotrl.music.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
//@Configuration
public class MvcConfiguration {

    private static class ApiUserArgumentResolver implements HandlerMethodArgumentResolver {

        @Bean
        public ApiUserArgumentResolver apiUserArgumentResolver() {
            return new ApiUserArgumentResolver();
        }

        public boolean supportsParameter(MethodParameter parameter) {
            return ApiUser.class.isAssignableFrom(parameter.getParameterType());
        }

        public Object resolveArgument(MethodParameter parameter,
                                      ModelAndViewContainer modelAndViewContainer,
                                      NativeWebRequest webRequest,
                                      WebDataBinderFactory binderFactory) {
            Authentication auth = null;
            try {
                auth = (Authentication) webRequest.getUserPrincipal();
            } catch (Exception e) {
                log.error("Mvc", e);
            }

            if (auth == null || !(auth.getPrincipal() instanceof ApiUser)) {
                return null;
            }

            return auth.getPrincipal();
        }


    }
}
