package org.funding.config;

import org.funding.security.config.SecurityConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class, SecurityConfig.class };
    }


    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { ServletConfig.class, WebConfig.class };
    }


    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "/",
                "/api/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/webjars/**"
        }; // 스프링의 FrontController인 DispatcherServlet이 담당할 URL 매핑 패턴
    }


    protected Filter[] getServletFilters() {
        // UTF-8 문자 인코딩 필터 생성 및 설정
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");       // 요청 데이터 UTF-8 디코딩
        characterEncodingFilter.setForceEncoding(true);     // 응답 데이터도 UTF-8 강제 인코딩
        
        return new Filter[] { characterEncodingFilter };
    }


    // 📍 파일 업로드 설정 상수
    final String LOCATION = "/Users/kimtaeyoung/Desktop/server_image"; // 개인별도로 지정해서 test해
    final long MAX_FILE_SIZE = 1024 * 1024 * 10L;      // 10MB
    final long MAX_REQUEST_SIZE = 1024 * 1024 * 20L;   // 20MB
    final int FILE_SIZE_THRESHOLD = 1024 * 1024 * 5;   // 5MB


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // 📍 404 에러를 Exception으로 변환
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");

        // 📍 Multipart 파일 업로드 설정
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
                LOCATION,           // 업로드 처리 디렉토리 경로
                MAX_FILE_SIZE,      // 업로드 가능한 파일 하나의 최대 크기
                MAX_REQUEST_SIZE,   // 업로드 가능한 전체 최대 크기(여러 파일 업로드)
                FILE_SIZE_THRESHOLD // 메모리 파일의 최대 크기(임계값)
        );
        registration.setMultipartConfig(multipartConfig);
    }



}
