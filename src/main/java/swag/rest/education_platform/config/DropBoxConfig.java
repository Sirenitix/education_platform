package swag.rest.education_platform.config;


import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropBoxConfig {
    private static final String token = "sl.BMZ7NxAHeV6ckOiFKy9vui-Q_p7h1_qIERS42x7bnM6biXYCo1vcxnsAO6nVRrbwj5WK-EJIUCzxkUQyVv48eUdGbim57IxzAg_eJZE8q75UWe1qemU6OUkdj8jeQVo7Y8azhC8";
    @Bean
    public DbxClientV2 dropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("/Documents/file").build();
        return new DbxClientV2(config, token);
    }

}
