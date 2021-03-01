package cn.lnfvc.ken;

import cn.lnfvc.ken.util.SecurityConstants;
import cn.lnfvc.ken.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
@Slf4j
class KenApplicationTests {

    @Test
    void contextLoads() {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode("123");
            System.out.println(password);
    }

    /**
     * 测试TokenUtil.isJwtExpired方法
     */
    @Test
    void isJwtExpired(){
        String token = "eyJUWVAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb290IiwiaXNzIjoid3d3LmNqbGx5LmNvbSIsInJvbHVlIjpbIlJPTEVfcm9vdCJdLCJleHAiOjE2MTQ2MDk0MTAsImlhdCI6MTYxNDYwOTIzMH0.HQQIv19H7LIjZPai1hB130G88sjHuNYbPcBU0nkqMLFjIuZdyyuMQ5i3tGOd41ooDS74i_h25qWP5G90I27bOA";
        Claims parsedToken;
        try {
            parsedToken = TokenUtil.parsingToken(token,SecurityConstants.JWT_SECRET);
             log.warn("try "+TokenUtil.isJwtExpired(parsedToken));

        }catch (ExpiredJwtException e){
            System.out.println(e.getClaims());
            parsedToken = e.getClaims();
            log.warn("catch "+TokenUtil.isJwtExpired(parsedToken));
            System.out.println(TokenUtil.getUsernameFromToken(parsedToken));
        }
    }


}
