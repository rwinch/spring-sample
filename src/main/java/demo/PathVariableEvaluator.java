package demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Component
public class PathVariableEvaluator {
	private final PathMatcher matcher = new AntPathMatcher();

	public Map<String, String> extract(HttpServletRequest request, String pattern) {
		String path = UrlUtils.buildRequestUrl(request);
		return matcher.extractUriTemplateVariables(pattern, path);
	}
}
