package vananh.work.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContext;
import javax.servlet.http.HttpServletRequest;

/**
 * MessageUtil
 */
@Component
public class MessageUtil {

    @Autowired
    MessageSource messageSource;

    /**
     * Get mes from MessageSource
     *
     * @param code
     * @param defaulMessage
     * @param request
     * @return
     */
    public String getMessage(String code, String defaulMessage, HttpServletRequest request) {
        return messageSource.getMessage(code, null, defaulMessage, new RequestContext(request).getLocale());
    }
}
