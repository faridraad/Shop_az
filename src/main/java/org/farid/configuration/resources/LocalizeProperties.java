package org.farid.configuration.resources;

import org.springframework.stereotype.Service;
import org.farid.configuration.localization.Translator;

@Service
public class LocalizeProperties {


    public String getProperty(String name) {

        return Translator.toLocale(name);
//        return environment.getProperty(name).trim();
    }

    public Integer getCode(String name) {
        return Integer.parseInt(Translator.toLocale(name).trim());
    }
}
