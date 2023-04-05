package br.com.dbc.vemser.financeiro.emailkafkaconsumer.service;

import br.com.dbc.vemser.financeiro.emailkafkaconsumer.dto.EmailDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;
    private final Configuration fmConfiguration;
    private final TemplateProcessor templateProcessor;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmailCreate(EmailDTO emailDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(emailDTO.getEmail());
            mimeMessageHelper.setSubject("Olá, cadastro realizado com sucesso!");
            mimeMessageHelper.setText(getTemplateCreate(emailDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Email não pode ser enviado!");
        }
    }

    private String getTemplateCreate(EmailDTO emailDTO) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", emailDTO.getNome());
        dados.put("numero_conta", emailDTO.getNumeroConta());
        dados.put("agencia", emailDTO.getNumeroAgencia());
        dados.put("numero_cartao", emailDTO.getNumeroCartao());
        dados.put("data_expedicao", emailDTO.getDataExpedicao());
        dados.put("codigo_seguranca", emailDTO.getCodigoSeguranca());
        dados.put("tipo_cartao", emailDTO.getTipoCartao());
        dados.put("data_vencimento", emailDTO.getDataVencimento());
        dados.put("email", from);

        Template template = fmConfiguration.getTemplate("contacreate.ftl");

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public void sendEmailDelete(EmailDTO emailDTO)  {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(emailDTO.getEmail());
            mimeMessageHelper.setSubject("Adeus!");

            mimeMessageHelper.setText(getTemplateDelete(emailDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Email não pode ser enviado!");
        }
    }

    private String getTemplateDelete(EmailDTO emailDTO) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", emailDTO.getNome());
        dados.put("email", from);

        Template template = fmConfiguration.getTemplate("contadelete.ftl");

        return templateProcessor.process(template, dados);
    }

    public void sendEmailBirthAccount(EmailDTO emailDTO)  {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(emailDTO.getEmail());
            mimeMessageHelper.setSubject("Parabéns!");

            mimeMessageHelper.setText(getTemplateBirthAccount(emailDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Email não pode ser enviado!");
        }
    }

    private String getTemplateBirthAccount(EmailDTO emailDTO) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", emailDTO.getNome());
        dados.put("email", from);

        Template template = fmConfiguration.getTemplate("contaaniversario.ftl");

        return templateProcessor.process(template, dados);
    }
}
