package cn.lnfvc.ken.filter;


import cn.lnfvc.ken.util.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/2/27 上午11:47
 */
public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null){
            chain.doFilter(request,response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

        try {
            if (!StringUtils.isEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)){
                Jws<Claims> parsedToken = Jwts.parserBuilder()
                        .setSigningKey(SecurityConstants.JWT_SECRET)     //解析签名
                        .build()
                        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX,""));

                String username = parsedToken
                        .getBody()
                        .getSubject();

                List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                        .get("rolue")).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority ))
                        .collect(Collectors.toList());

                if (!StringUtils.isEmpty(username)){
                    return new UsernamePasswordAuthenticationToken(username,null,authorities);
                }
            }
        }catch (ExpiredJwtException e){
            System.out.println(token+" "+e.getMessage());
        }
    return null;
    }

}
