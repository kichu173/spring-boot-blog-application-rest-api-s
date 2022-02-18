package com.sprinboot.blog.exception;

import com.sprinboot.blog.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice // to handle exceptions globally and also auto-detected while component scanning(internally uses @Component)
public class GlobalExceptionHandler {
    // handle specific exceptions
    // To construct error response for custom exceptions.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest WebRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                WebRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception,
                                                                        WebRequest WebRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                WebRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // global exceptions(To handle other type of exceptions which inherits Exception base class like MethodArgumentMismatchException,...)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                               WebRequest WebRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                WebRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
// Error response for handle specific exceptions
//{
//        "timestamp": "2022-02-18T16:46:36.304+00:00",
//        "message": "Post not found with id : '33'",
//        "details": "uri=/api/posts/33"
//}

// Error response for handle global exceptions : GET -> http://localhost:8080/api/posts/"33"
//{
//        "timestamp": "2022-02-18T17:08:45.304+00:00",
//        "message": "Failed to convert value of type 'java.lang.String' to required type 'long'; nested exception is java.lang.NumberFormatException: For input string: \"\"33\"\"",
//        "details": "uri=/api/posts/%2233%22"
//        }
