package com.dspassov.kovapi.interceptors;

import com.dspassov.kovapi.exceptions.IllegalParamException;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Component
public class QueryStringSanitizeInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        for (String[] params : request.getParameterMap().values()) {
            for (String param : params) {
                if (!this.isClean(param)) {
                    throw new IllegalParamException(ResponseMessageConstants.UNEXPECTED_CHARS);
                }
            }
        }

        return super.preHandle(request, response, handler);
    }

    private boolean isClean(String param) {
        return HtmlUtils.htmlEscape(param).equals(param) && !param.contains("<script>");
    }
}
