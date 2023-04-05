package br.com.dbc.vemser.financeiro.emailkafkaconsumer.service;

import br.com.dbc.vemser.financeiro.emailkafkaconsumer.dto.EmailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @KafkaListener(
            topics = "${kafka.topic}",
            containerFactory = "listenerContainerFactory",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic}", partitions = {"0", "1", "2"})}, groupId = "${kafka.group-id}")
    public void CreateEmailConsumeListener(@Payload String content, @Header(value = KafkaHeaders.RECEIVED_PARTITION_ID) Integer partitionID) {
        try {
            EmailDTO emailDTO = objectMapper.readValue(content, EmailDTO.class);
            switch (partitionID) {
                case 0 -> {
                    emailService.sendEmailCreate(emailDTO);
                }
                case 1 -> {
                    emailService.sendEmailDelete(emailDTO);
                }
                case 2 -> {
                    emailService.sendEmailBirthAccount(emailDTO);
                }
            }
        } catch (Exception e) {
            log.info("DTO inválido!");
        }
    }
}
