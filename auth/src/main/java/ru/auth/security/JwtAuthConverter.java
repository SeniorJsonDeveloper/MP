package ru.auth.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;


import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String REALM_ACCESS = "realm_access";

    private static final String ROLES = "roles";


    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        return
                new JwtAuthenticationToken(
                        source,extractAuthorities(source).collect(Collectors.toSet())
                        ,getSubject(source)

        );
    }


    public String getSubject(Jwt source) {
        String claimName = JwtClaimNames.SUB;
        return source.getClaim(claimName);
    }

    private Stream<? extends GrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, Object> claims;
        Collection<String> roles;
        if (jwt.getClaim(REALM_ACCESS) == null){
            return Stream.of();
        }
        claims = jwt.getClaim(REALM_ACCESS);
        roles = (Collection<String>) claims.get(ROLES);
        return roles.stream().map(SimpleGrantedAuthority::new);
    }
}
