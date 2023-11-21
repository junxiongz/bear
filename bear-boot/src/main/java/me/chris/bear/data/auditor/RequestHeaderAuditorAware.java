package me.chris.bear.data.auditor;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.chris.bear.error.ErrorDetailException;
import me.chris.bear.error.Errors;
import me.chris.bear.http.BearMediaType;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * @Author Christopher
 * @Since 2023/11/21 11:18 AM
 */
@Slf4j
public class RequestHeaderAuditorAware implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {

        long currentUser = 0L;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null)
            throw new ErrorDetailException(Errors.internalServerError());

        HttpServletRequest request = attributes.getRequest();
        String userId = request.getHeader(BearMediaType.CURRENT_USER);

        try {
            currentUser = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            log.warn("this request current-user is invalid , check value : {}",userId);
        }
        log.info("current-user is :{}",currentUser);
        return Optional.of(currentUser);
    }
}
