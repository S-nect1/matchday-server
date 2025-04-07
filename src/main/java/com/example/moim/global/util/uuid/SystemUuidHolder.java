package com.example.moim.global.util.uuid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
//@Profile("!test")
@Component
public class SystemUuidHolder implements UuidHolder {
    @Override
    public String randomUuid() {
        return UUID.randomUUID().toString();
    }
}
