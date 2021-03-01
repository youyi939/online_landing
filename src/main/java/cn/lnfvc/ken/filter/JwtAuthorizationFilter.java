package cn.lnfvc.ken.filter;

import cn.lnfvc.ken.pojo.UserPo;
import cn.lnfvc.ken.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: KenChen
 * @Description: 登陆过滤器，认证成功后分发token
 * @Date: Create in  2021/2/22 下午3:35
 */
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
            setFilterProcessesUrl("/api/token");            //获取token的url
    }

//认证逻辑
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserPo loginData = pareData(request);
        UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(loginData.getUsername(),loginData.getPassword());
        return this.authenticationManager.authenticate(uToken);
    }

//    认证成功逻辑
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserPo user = (UserPo) authResult.getPrincipal();
        List<String> rolue = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = TokenUtil.getToken(user.getUsername(),rolue);
        String refreshToken = TokenUtil.getRefreshToken(user.getUsername(),rolue);

        response.setHeader("Authorization","Bearer "+token);
        response.setHeader("RefreshToken","Bearer "+refreshToken);
    }

    private UserPo pareData(HttpServletRequest request)throws  IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request.getInputStream(),UserPo.class);
    }

}
