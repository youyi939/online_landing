package cn.lnfvc.ken.filter.point;

import cn.lnfvc.ken.pojo.CommonResult;
import cn.lnfvc.ken.util.LandUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(LandUtil.createResponseBody(401,"认证失败"));
    }

}
