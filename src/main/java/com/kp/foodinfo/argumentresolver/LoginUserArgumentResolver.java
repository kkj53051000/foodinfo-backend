package com.kp.foodinfo.argumentresolver;

import com.kp.foodinfo.domain.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        log.info("supportsParamter!");

        boolean hasLoginAnnotation = methodParameter.hasParameterAnnotation(Login.class);
        boolean hasUserSessionType = UserSession.class.isAssignableFrom(methodParameter.getParameterType());

        return hasLoginAnnotation && hasUserSessionType;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        log.info("resolveArgument!");

        HttpServletRequest httpServletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();

        Long userId = (Long)httpServletRequest.getAttribute("userId");

        UserSession userSession = new UserSession();
        userSession.setUserId(userId);

        return userSession;
    }
}
