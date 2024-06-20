package ru.mp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;

@RedisHash("refresh_token")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RefreshTokenEntity {

    @Id
    @Indexed
    private Integer id;

    @Indexed
    private Integer userId;

    @Indexed
    private String token;

    @Indexed
    private Instant expireDate;
}
