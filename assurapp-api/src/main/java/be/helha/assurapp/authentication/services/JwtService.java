package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {
    private final String KEY = "ldkqlsk478jghfdq3wcxv53G17TR73DS5Q4FZ87ERQDS42V1Q3753QDSF42V46Q7ZRQ144sdz7erds4f5z7sd8zsfdjqzyjqshfuqheqziruojqksfdjsqiujrejdksfq54fq8ze75qsdf487qzer545sd7f7qez5r4sq7r5e4z8q7fqds48fez7rdsjkhfjksdhuqezjhsd78sqdf4q7fg54ze87qd5sf4qez";
    private UserService userService;

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDate(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();

        final Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "role", user.getRole(),
                Claims.EXPIRATION, new Date(currentTime + 3600 * 1000 * 5),
                Claims.SUBJECT, user.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + 3600 * 1000 * 2))
                .setSubject(user.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public Map<String, String> generate(String username) {
        User user = this.userService.loadUserByUsername(username);
        return this.generateJwt(user);
    }
}
