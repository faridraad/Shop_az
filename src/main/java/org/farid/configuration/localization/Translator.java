package org.farid.configuration.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
public class Translator {


    private static ResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String key) {

        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servlReqAttr = (ServletRequestAttributes) reqAttr;
        HttpServletRequest request = servlReqAttr.getRequest();
        String language =  request.getHeader("language");
        if(language==null)
            language = "en";
//        String language = ApplicationUtilities.getInstance().getCurrentHttpRequest().getHeader("Accept-Language");
        Locale.setDefault(new Locale("en"));
        return messageSource.getMessage
                (
                        key,
                        null,
                        language == null || language.isEmpty() ? Locale.getDefault() : new Locale(language)
                );
    }

}
