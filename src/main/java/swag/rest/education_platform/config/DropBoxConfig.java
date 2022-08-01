package swag.rest.education_platform.config;


import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropBoxConfig {
    private static final String token = "sl.BMZEO4V02MTtguiasvcRZFEQ3bzx8SgGgkW1PN8lBX84FuvX-olTBl5EXFIAM-2m1wIfOOv1-nkloQ-H_OJI3e2KaU2VsWavcjcICfthrvSYu9kJsjeaGIVUsgGTZm4-jYCBPmyN";
    @Bean
    public DbxClientV2 dropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("/Documents/file").build();
        return new DbxClientV2(config, token);
    }

}
