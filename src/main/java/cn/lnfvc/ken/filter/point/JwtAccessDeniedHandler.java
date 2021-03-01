package cn.lnfvc.ken.filter.point;

import cn.lnfvc.ken.pojo.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义无权访问处理类
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(200);
        response.setContentType("text/json;charset=utf-8");
        String json = objectMapper.writeValueAsString(new CommonResult<String>(400,"无访问权限: "+request.getRequestURI()));
        response.getWriter().write(json);

        //// TODO: 2021/3/1 用户访问无权限接口log
        System.out.println(request.getRequestURI());

    }
}
