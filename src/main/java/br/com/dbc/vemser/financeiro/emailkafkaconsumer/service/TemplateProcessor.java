package br.com.dbc.vemser.financeiro.emailkafkaconsumer.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

public interface TemplateProcessor {
    String process(Template template, Map<String, Object> data) throws IOException, TemplateException;
}
