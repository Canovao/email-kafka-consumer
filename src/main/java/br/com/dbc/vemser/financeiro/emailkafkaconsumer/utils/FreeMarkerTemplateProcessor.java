package br.com.dbc.vemser.financeiro.emailkafkaconsumer.utils;

import br.com.dbc.vemser.financeiro.emailkafkaconsumer.service.TemplateProcessor;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Component
public class FreeMarkerTemplateProcessor implements TemplateProcessor {

    @Override
    public String process(Template template, Map<String, Object> data) throws IOException, TemplateException {
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
    }
}
