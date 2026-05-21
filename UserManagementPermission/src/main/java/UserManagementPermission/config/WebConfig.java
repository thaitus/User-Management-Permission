package UserManagementPermission.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Phục vụ file động từ thư mục "uploads" bên ngoài
        Path uploadDir = Paths.get("uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // 2. KHAI BÁO LẠI CẤU HÌNH MẶC ĐỊNH CHO TĨNH (Để cứu Background/Logo)
        // Spring Boot thỉnh thoảng sẽ lờ đi cấu hình mặc định nếu ta khai báo WebMvcConfigurer
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") // Áp dụng cho mọi URL
                .excludePathPatterns("/css/**", "/js/**", "/images/**"); // Trừ tài nguyên tĩnh
    }
}