package com.tech_bit.tech_bit.service.admin;


import com.nimbusds.jose.JOSEException;
import com.tech_bit.tech_bit.dto.request.AuthenticationRequest;
import com.tech_bit.tech_bit.dto.request.IntrospectRequest;
import com.tech_bit.tech_bit.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    String authenticate(AuthenticationRequest authenticationRequest);

    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
}
