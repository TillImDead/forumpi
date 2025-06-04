package esprit.collaborative_space.config;

import esprit.collaborative_space.service.CommentsServiceImp;
import esprit.collaborative_space.service.ICommentsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ICommentsService commentsService() {
        return new CommentsServiceImp();
    }
}