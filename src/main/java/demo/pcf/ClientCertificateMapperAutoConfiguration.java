/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.pcf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.security.cert.CertificateException;

@Configuration
public class ClientCertificateMapperAutoConfiguration {

    @Bean
    ClientCertificateMapper clientCertificateMapper() throws CertificateException {
        return new ClientCertificateMapper();
    }

    @Bean
    FilterRegistrationBean filterRegistrationBean(ClientCertificateMapper mapper) {
        FilterRegistrationBean result = new FilterRegistrationBean(mapper);
        result.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return result;
    }
}
