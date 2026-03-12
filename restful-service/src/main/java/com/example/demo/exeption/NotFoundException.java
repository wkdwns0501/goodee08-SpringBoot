package com.example.demo.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
                                            // reason: 예외 메세지
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
public class NotFoundException extends RuntimeException {
}
