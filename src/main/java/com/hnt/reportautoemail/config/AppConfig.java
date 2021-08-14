package com.hnt.reportautoemail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "ae.config")
public class AppConfig {

    public String reportFilename;
    public String emailReportInterval8AM;

    public String getReportFilename() {
        return reportFilename;
    }

    public void setReportFilename(String reportFilename) {
        this.reportFilename = reportFilename;
    }

    public String getEmailReportInterval8AM() {
        return emailReportInterval8AM;
    }

    public void setEmailReportInterval8AM(String emailReportInterval8AM) {
        this.emailReportInterval8AM = emailReportInterval8AM;
    }
}
