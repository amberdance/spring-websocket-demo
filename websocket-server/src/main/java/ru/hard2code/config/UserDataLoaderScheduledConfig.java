package ru.hard2code.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import ru.hard2code.domain.model.User;
import ru.hard2code.service.UserService;

import java.util.UUID;

@EnableScheduling
@Configuration
@Slf4j
@RequiredArgsConstructor
public class UserDataLoaderScheduledConfig {

    private final UserService userService;
    private final ScheduledAnnotationBeanPostProcessor postProcessor;

    @Scheduled(fixedRate = 5000)
    void createUser() {
        var random = UUID.randomUUID().toString().substring(0, 8);
        var user = User.builder()
                .username("@" + random)
                .email("somemail@" + random + ".com")
                .firstName(random)
                .lastName(random)
                .build();

        try {
            userService.createUser(user);
            log.info("User saved: {}", user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            postProcessor.destroy();
        }
    }
}
