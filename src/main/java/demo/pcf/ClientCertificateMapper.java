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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.logging.Logger;

/**
 * A Servlet {@link Filter} that translates the {@code X-Forwarded-Client} HTTP header to the {@code javax.servlet.request.X509Certificate} Servlet attribute.  This implementation handles both
 * multiple headers as well as the <a href=https://tools.ietf.org/html/rfc7230#section-3.2.2>RFC 7230</a> comma delimited equivalent.
 */
final class ClientCertificateMapper implements Filter {

    static final String ATTRIBUTE = "javax.servlet.request.X509Certificate";

    static final String HEADER = "X-Forwarded-Client-Cert";

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final CertificateFactory certificateFactory;

    ClientCertificateMapper() throws CertificateException {
        this.certificateFactory = CertificateFactory.getInstance("X.509");
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            try {
                List<X509Certificate> certificates = getCertificates((HttpServletRequest) request);

                if (!certificates.isEmpty()) {
                    request.setAttribute(ATTRIBUTE, certificates.toArray(new X509Certificate[certificates.size()]));
                }
            } catch (CertificateException e) {
                this.logger.warning("Unable to parse certificates in X-Forwarded-Client-Cert");
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private X509Certificate decodeCertificate(String rawCertificate) throws CertificateException, IOException {
        try (InputStream in = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(rawCertificate))) {
            return (X509Certificate) this.certificateFactory.generateCertificate(in);
        }
    }

    private List<X509Certificate> getCertificates(HttpServletRequest request) throws CertificateException, IOException {
        List<X509Certificate> certificates = new ArrayList<>();

        for (String rawCertificate : getRawCertificates(request)) {
            certificates.add(decodeCertificate(rawCertificate));
        }

        return certificates;
    }

    @SuppressWarnings("unchecked")
    private List<String> getRawCertificates(HttpServletRequest request) {
        Enumeration<String> candidates = request.getHeaders(HEADER);

        if (candidates == null) {
            return Collections.emptyList();
        }

        List<String> rawCertificates = new ArrayList<>();
        while (candidates.hasMoreElements()) {
            String candidate = candidates.nextElement();

            if (hasMultipleCertificates(candidate)) {
                rawCertificates.addAll(Arrays.asList(candidate.split(",")));
            } else {
                rawCertificates.add(candidate);
            }
        }

        return rawCertificates;
    }

    private boolean hasMultipleCertificates(String candidate) {
        return candidate.indexOf(',') != -1;
    }

}
