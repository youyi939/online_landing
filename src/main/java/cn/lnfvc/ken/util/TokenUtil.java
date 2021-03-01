package cn.lnfvc.ken.util;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @Author: KenChen
 * @Description: JWT-Token工具类
 * @Date: Create in  2021/2/28 下午9:33
 */
public class TokenUtil {

    /**
     * 获取用户信息，创建一个token
     * 有效期：30s，短
     * @param username
     * @return token
     */
    public static String getToken(String username, List<String> rolue){
        String token = Jwts.builder()
                .setHeaderParam("TYP",SecurityConstants.TOKEN_TYPE)
                .setSubject(username)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .claim("rolue",rolue)
                .setExpiration(new Date(System.currentTimeMillis() +  30000))    // 设置过期时间，30s
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 设置当前时间
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.JWT_SECRET).compact();
        return token;
    }

    /**
     * 获取用户信息，创建refreshToken，token过期之后凭它来换取新token
     * 有效期：3min，长
     * @param username
     * @return refreshToken
     */
    public static String getRefreshToken(String username,List<String> rolue){
        String refreshToken = Jwts.builder()
                .setHeaderParam("TYP",SecurityConstants.TOKEN_TYPE)
                .setSubject(username)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .claim("rolue",rolue)
                .setExpiration(new Date(System.currentTimeMillis() +  180000))    // 设置过期时间，3min
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 设置当前时间
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.JWT_SECRET).compact();
        return refreshToken;
    }

    /**
     * 检测token是否过期
     * @param claims
     * @return true:已过期，false:未过期
     */
    public static boolean isJwtExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }


    /**
     * 解析token
     * @param token
     * @param singKey
     * @return claims
     */
    public static Claims parsingToken(String token,String singKey){
        return Jwts.parser()
                .setSigningKey(singKey)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX,"")).getBody();
    }

    /**
     * 返回username
     * @param claims
     * @return 当前token的username
     */
    public static String getUsernameFromToken(Claims claims){
        return claims.getSubject();
    }

    /**
     * 返回token中的权限List
     * @param claims
     * @return List<SimpleGrantedAuthority>
     */
    public static List<SimpleGrantedAuthority> getAuthFromToken(Claims claims){
        return ((List<?>) claims
               .get("rolue")).stream()
               .map(authority -> new SimpleGrantedAuthority((String) authority ))
               .collect(Collectors.toList());
    }

}







