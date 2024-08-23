package app.blog.model.contact;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private Map<String, List<String>> classifications;

    public Map<String, List<String>> getClassifications() {
        return classifications;
    }

    public void setClassifications(Map<String, List<String>> classifications) {
        this.classifications = classifications;
    }
}
