package com.example.roomwebapp.exception.handler;


import com.example.roomwebapp.exception.dto.AppResponse;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // 400

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.warn(ex.getMessage());
        //
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }
        final AppResponse apiError = new AppResponse(errors);
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final AppResponse apiError = new AppResponse(errors);
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        final AppResponse apiError = new AppResponse(error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getRequestPartName() + " part is missing";
        final AppResponse apiError = new AppResponse(error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getParameterName() + " parameter is missing";
        final AppResponse apiError = new AppResponse(error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        final AppResponse apiError = new AppResponse(error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final List<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }

        final AppResponse apiError = new AppResponse(errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppInputErrorException.class)
    public ResponseEntity<Object> handleAppInputErrorException(AppInputErrorException ex) {
        final AppResponse apiErrorResponse;
        if (ex.getErrors() != null) {
            apiErrorResponse = new AppResponse(ex.getErrors());
        } else {
            apiErrorResponse = new AppResponse(ex.getMessage());
        }
        return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppInputFieldErrorsException.class)
    public ResponseEntity<Object> handleAppInputErrorException(AppInputFieldErrorsException ex) {
        if (ex.getErrors() != null) {
            final AppResponse apiErrorResponse = new AppResponse(ex.getErrors());
            return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            final AppResponse apiErrorResponse = new AppResponse(ex.getMessage());
            return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        AppResponse apiErrorResponse;
        String message = ex.getLocalizedMessage();
        if (message.contains("problem:")) {
            apiErrorResponse = new AppResponse(message.substring(message.indexOf("problem:") + 9, message.indexOf("; nested exception is")));
        } else {
            apiErrorResponse = new AppResponse("operation.invalid.request");
        }
        logger.warn(String.format("%s %s", ex.getClass().getName(), ex.getLocalizedMessage()));
        return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 404

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final AppResponse apiError = new AppResponse(error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        final AppResponse apiError = new AppResponse(builder.toString());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());
        //
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

        final AppResponse apiError = new AppResponse(builder.substring(0, builder.length() - 2));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /*@ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
    public ResponseEntity<Object> handleFileSizeLimitExceededException(FileUploadBase.FileSizeLimitExceededException e) {
        logger.info(e.getClass().getName());
        logger.error(e.getMessage());

        final AppResponse apiError = new AppResponse("error.file-exceeds-max-permitted-size");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }*/

    //StandardServletMultipartResolver
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleMultipartException(MultipartException e) {
        logger.info(e.getClass().getName());

        final AppResponse apiError = new AppResponse(e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //CommonsMultipartResolver
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        logger.info(e.getClass().getName());

        final AppResponse apiError = new AppResponse("error.file.upload.limit");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LockAcquisitionException.class)
    public ResponseEntity<Object> lockAcquisitionExceptionException(LockAcquisitionException e) {
        logger.info(e.getClass().getName());
        System.out.println("LockAcquisitionException: Error another user is currently modifying this record");

        final AppResponse apiError = new AppResponse("error.concurrent.modification");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotAcquireLockException.class)
    public ResponseEntity<Object> cannotAcquireLockExceptionException(CannotAcquireLockException e) {
        logger.info(e.getClass().getName());
        System.out.println("CannotAcquireLockException: Error another user is currently modifying this record");

        final AppResponse apiError = new AppResponse("error.concurrent.modification");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<Object> valueInstantiationExceptionException(ValueInstantiationException e) {
        logger.info(e.getClass().getName());

        final AppResponse apiError = new AppResponse(e.getMessage());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 500

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        logger.error(ex.getMessage());

        logger.info(ex.getClass().getName());
        logger.error("error-uncaught", ex);
        ex.printStackTrace();

        //500
        final AppResponse apiError = new AppResponse("error occurred");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
