package com.webapp.formychild.interceptor;

import com.webapp.formychild.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        logger.info(request.getMethod() + " : " + request.getServletPath());

        // option 요청은 바로 통과
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            // request의 parameter에서 auth_token으로 넘어온 것을 찾기
            String token = request.getHeader("auth_token");
            if (token != null && token.length() > 0) {
                // 유효한 토큰이면 진행, 그렇지 않으면 예외 발생
                jwtService.checkValid(token);

                logger.info("토근 사용 가능 : {}", token);
                return true;
            }else{
                throw new RuntimeException("인증 토큰이 없습니다.");
            }
        }
    }
}
