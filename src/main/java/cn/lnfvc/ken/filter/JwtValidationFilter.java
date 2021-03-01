package cn.lnfvc.ken.filter;

import cn.lnfvc.ken.util.SecurityConstants;
import cn.lnfvc.ken.util.TokenUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: KenChen
 * @Description: 每次请求的验证过滤器，检查携带的token是否合法
 * @Date: Create in  2021/3/1 上午3:47
 */
@Slf4j
public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //检查token有效期，过期则发一个新token
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        Claims parsedToken;

        try {
            if (!StringUtils.isEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)){
            parsedToken = TokenUtil.parsingToken(token,SecurityConstants.JWT_SECRET);
                if (!TokenUtil.isJwtExpired(parsedToken)){          //如果token没过期
                    UsernamePasswordAuthenticationToken authentication = getAuthentication(parsedToken);
                    if (authentication == null){
                        chain.doFilter(request,response);
                        return;
                    }
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request,response);
                    }
            }else {                 //请求头中无token
                chain.doFilter(request,response);
            }
        }catch (ExpiredJwtException e){                 //监测到token过期
            parsedToken = e.getClaims();
            if (TokenUtil.isJwtExpired(parsedToken)){
                List<String> authorities = ((List<?>) parsedToken
                        .get("rolue")).stream()
                        .map(authority -> (String) authority )
                        .collect(Collectors.toList());
                response.setHeader("Authorization","Bearer "+TokenUtil.getToken(parsedToken.getSubject(),authorities));       //重新生成token并塞进返回头
            }
        } catch (UnsupportedJwtException e){ //不受支持
            log.warn("Request to parse unsupported JWT : {} failed : {}",token,e.getMessage());
        }catch (MalformedJwtException e){       //token无效
            log.warn("Request to parse invalid JWT : {} failed : {}",token,e.getMessage());
        }catch (SignatureException e){          //签名出错
            log.warn("Request to parse JWT with invalid signature : {} failed : {}",token,e.getMessage());
        }catch (IllegalArgumentException e){
            log.warn("Request to parse empty or null JWT : {} failed : {}",token,e.getMessage());
        }

    }

    /**
     * 解析token
     * @param claims
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(Claims claims){
        String username = TokenUtil.getUsernameFromToken(claims);
        List<SimpleGrantedAuthority> authorities = TokenUtil.getAuthFromToken(claims);
                if (!StringUtils.isEmpty(username)){
                    return new UsernamePasswordAuthenticationToken(username,null,authorities);
                }
        return null;
    }

}
