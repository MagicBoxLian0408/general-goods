package kr.magicbox.generalgoods.adapter.in.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.magicbox.generalgoods.adapter.in.security.properties.TrustedIpProperties;
import kr.magicbox.generalgoods.domain.vo.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class UserInfoExtractFilter extends OncePerRequestFilter {
    private final TrustedIpProperties trustedIpProperties;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();
        if (!isTrustedIp(clientIp, trustedIpProperties.getIps())) {
            filterChain.doFilter(request, response);
            return;
        }

        String userIdRequestHeader = request.getHeader("X-User-Id");
        if (!isValidUserId(userIdRequestHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserId userId = UserId.of(Long.valueOf(userIdRequestHeader));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }


    private boolean isTrustedIp(String clientIp, List<String> trustedIps) {
        return trustedIps.stream()
                .anyMatch(trusted -> new IpAddressMatcher(trusted).matches(clientIp));
    }

    private boolean isValidUserId(String userIdRequestHeader) {
        try {
            return Long.parseLong(userIdRequestHeader) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}