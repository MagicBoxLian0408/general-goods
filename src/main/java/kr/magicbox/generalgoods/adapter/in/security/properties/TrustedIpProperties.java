package kr.magicbox.generalgoods.adapter.in.security.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "security.trusted")
public class TrustedIpProperties {
    private final List<String> ips;
}