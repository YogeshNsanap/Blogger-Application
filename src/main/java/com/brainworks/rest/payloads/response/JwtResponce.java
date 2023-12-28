package com.brainworks.rest.payloads.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponce {

    private String token;
    private String username;
}
