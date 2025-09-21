package com.tech_bit.tech_bit.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tech_bit.tech_bit.dto.request.AuthenticationRequest;
import com.tech_bit.tech_bit.dto.request.IntrospectRequest;
import com.tech_bit.tech_bit.dto.response.IntrospectResponse;
import com.tech_bit.tech_bit.entity.User;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.secret}")
    protected String SIGNER_KEY;

    @Override
    public String authenticate(AuthenticationRequest authenticationRequest) {
         User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USERNAME_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        return generateToken(user);
    }
    private String generateToken(User user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

            JWTClaimsSet jwtClaimNames = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("tech_bit.com")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .claim("scope", buildScope(user))
                    .build();
            Payload payload = new Payload(jwtClaimNames.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);

            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            // Return the serialized JWT token
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request)
                throws JOSEException, ParseException {
            String token = request.getToken();
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            boolean verified =  signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .valid(verified && expityTime.after(new Date()))
                    .build();

    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles();
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
