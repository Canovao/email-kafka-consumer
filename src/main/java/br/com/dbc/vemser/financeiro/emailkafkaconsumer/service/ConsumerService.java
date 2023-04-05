package br.com.dbc.vemser.financeiro.emailkafkaconsumer.service;

import br.com.dbc.vemser.financeiro.emailkafkaconsumer.dto.EmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
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
            groupId = "${kafka.group-id}",
            topicPartitions = @TopicPartition(topic = "${kafka.topic}", partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0")))
    public void CreateEmailConsumeListener(@Payload String content) throws JsonProcessingException {
        try {
            EmailDTO emailDTO = objectMapper.readValue(content, EmailDTO.class);
            log.info(emailDTO.toString());
            emailService.sendEmailCreate(emailDTO);
        } catch (JsonProcessingException e) {
            log.info("DTO inválido!");
        }
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            containerFactory = "listenerContainerFactory",
            groupId = "${kafka.group-id}",
            topicPartitions = @TopicPartition(topic = "${kafka.topic}", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0")))
    public void DeleteAccountConsumeListener(@Payload String content){
        try {
            EmailDTO emailDTO = objectMapper.readValue(content, EmailDTO.class);
            log.info(emailDTO.toString());
            emailService.sendEmailDelete(emailDTO);

        } catch (JsonProcessingException e) {
            log.info("DTO inválido!");
        }

    }
}
