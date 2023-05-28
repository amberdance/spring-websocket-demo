package ru.hard2code.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.hard2code.service.UserService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class UsersWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final ScheduledAnnotationBeanPostProcessor postProcessor;


    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session)
            throws Exception {
        sessions.add(session);
        log.info("Connection established from: {} @ {}", session,
                Instant.now());
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session,
                                      @NonNull CloseStatus closeStatus) {
        sessions.remove(session);
        log.info("Connection closed by: {} with status: {} @ {}", session,
                closeStatus,
                Instant.now());
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session,
                                     @NonNull TextMessage message)
            throws Exception {
        log.info("Message received: {}. From: <-- {}", message, session);

        try {
            for (var webSocketSession : sessions) {
                if (webSocketSession != session) {
                    log.info("Sending message: {} to --> {}", message,
                            webSocketSession);
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    @Scheduled(fixedRate = 5000)
    private void broadcastingUsers() {
        try {
            var user = userService.createRandomUser();

            for (var session : sessions) {
                log.info("Sending created user to: {}", session);
                session.sendMessage(
                        new TextMessage(objectMapper.writeValueAsBytes(user)));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            postProcessor.destroy();
        }
    }

}
