

package com.example.demo;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.oauth2.client.oidc.authentication.ReactiveOidcIdTokenDecoderFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoderFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
public class DemoController {

    private final ReactiveJwtDecoderFactory<ClientRegistration> decoderFactory;
    private final ReactiveClientRegistrationRepository clientRegistrationRepo;


    DemoController(final ObjectProvider<ReactiveJwtDecoderFactory<ClientRegistration>> decoderFactory,
                   final ReactiveClientRegistrationRepository clientRegistrationRepo) {
        this.decoderFactory = decoderFactory.getIfAvailable(ReactiveOidcIdTokenDecoderFactory::new);
        this.clientRegistrationRepo = clientRegistrationRepo;
    }


    @GetMapping("/hello")
    public Mono<String> sayHello(@RequestHeader("X-ID-Token") final String idToken) {
        return getJWT(idToken).map(jwt -> "Contains the following claims: " + jwt.getClaims().entrySet());
    }


    private Mono<Jwt> getJWT(final String idToken) {
        final Mono<ClientRegistration> idmsRegistrationMono = clientRegistrationRepo.findByRegistrationId("sample");

        return idmsRegistrationMono.flatMap(idmsRegistration -> {
            final ReactiveJwtDecoder decoder = decoderFactory.createDecoder(idmsRegistration);
            return decoder.decode(idToken);
        });
    }
}
