package app.blog.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "spring.email")
public class EmailProperty {
    private Map<String, String> classifications;

    public Map<String, String> getClassifications() {
        return classifications;
    }

    public void setClassifications(Map<String, String> classifications) {
        this.classifications = classifications;
    }
}
