package ru.waveaccess.conference.utils;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.waveaccess.conference.dto.UserDto;
import ru.waveaccess.conference.services.MailService;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class EmailAspect {
    private final MailService mailService;

    @Pointcut("@annotation(ru.waveaccess.conference.utils.SendEmail)")
    public void emailPointcut() {
    }

    @Around(value = "emailPointcut()")
    public Object formBookingMail(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SendEmail sendEmailAnno = method.getAnnotation(SendEmail.class);
        MailType mailType = sendEmailAnno.mailType();

        Object returnedValue = null;
        try {
            returnedValue = joinPoint.proceed();
        } finally {
            if (returnedValue != null) {
                switch (mailType) {
                    case CONFIRMATION: {
                        UserDto user = (UserDto) returnedValue;
                        mailService.sendEmailConfirmationLink(
                                user.getFirstName(),
                                user.getEmail(),
                                user.getConfirmLink()
                        );
                    }
                }
            }
        }
        return returnedValue;
    }
}
