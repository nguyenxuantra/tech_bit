package com.tech_bit.tech_bit.controller;

import com.nimbusds.jose.JOSEException;
import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.request.AuthenticationRequest;
import com.tech_bit.tech_bit.dto.request.IntrospectRequest;
import com.tech_bit.tech_bit.dto.response.IntrospectResponse;
import com.tech_bit.tech_bit.service.admin.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<String> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {

        return ApiResponse.<String>builder()
                .code(200)
                .message("Success")
                .result(authenticationService.authenticate(authenticationRequest))
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws JOSEException, ParseException  {
        return ApiResponse.<IntrospectResponse>builder()
                .code(200)
                .message("Success")
                .result(authenticationService.introspect(introspectRequest))
                .build();
    }
}
