package br.com.taskregister.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var issue = new Issue();
        issue.setStatus(status.value());
        issue.setTitle(ex.getMessage());
        issue.setDateTime(LocalDateTime.now());

        return handleExceptionInternal(ex, issue, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        var fieldList = new ArrayList<Issue.Field>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fieldList.add(new Issue.Field(name, message));
        }

        var issue = new Issue();
        issue.setStatus(status.value());
        issue.setTitle("One or more fields are wrong. "
                + "Fix and try again.");
        issue.setDateTime(LocalDateTime.now());
        issue.setFieldList(fieldList);

        return super.handleExceptionInternal(ex, issue, headers, status, request);
    }

}

