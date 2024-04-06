package de.eisingerf.elp.common.api.rest.list;

import de.eisingerf.elp.common.api.rest.list.input.OffsetPaginationParameter;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;
import org.springdoc.core.converters.models.Sort;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OffsetPaginationHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return OffsetPaginationParameter.class.equals(parameter.getParameterType());
    }

    @Override
    public OffsetPaginationParameter resolveArgument(
            @NotNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NotNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        // This seems stupid. Combining ParameterInfo and RequestParamMethodArgumentResolver should easily be able to
        // support ParameterObjects.
        // Not sure why Spring isn't doing it. I will make this work with the OffsetPaginationParameter and maybe
        // generalize it later if I need to.
        // At least until I find out that spring can already do this with some obscure annotation that's not in the docs
        // and throw it all away again.
        var parameterAnnotations =
                parameter.getParameterType().getConstructors()[0].getParameterAnnotations();
        var offsetParamAnnotation = (RequestParam) Arrays.stream(parameterAnnotations[0])
                .filter(annotation -> RequestParam.class.equals(annotation.annotationType()))
                .findFirst()
                .orElseThrow();
        var limitParamAnnotation = (RequestParam) Arrays.stream(parameterAnnotations[1])
                .filter(annotation -> RequestParam.class.equals(annotation.annotationType()))
                .findFirst()
                .orElseThrow();

        String offsetValue = Optional.ofNullable(webRequest.getParameter(offsetParamAnnotation.name()))
                .orElse(offsetParamAnnotation.defaultValue());
        String limitValue = Optional.ofNullable(webRequest.getParameter(limitParamAnnotation.name()))
                .orElse(limitParamAnnotation.defaultValue());
        String[] sorts = webRequest.getParameterValues("sort");
        Sort sort = sorts != null ? new Sort(Arrays.asList(sorts)) : null;

        return new OffsetPaginationParameter(Integer.valueOf(offsetValue), Integer.valueOf(limitValue), sort);
    }
}
