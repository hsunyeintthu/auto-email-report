package com.hnt.reportautoemail.service;

import com.hnt.reportautoemail.config.AppConfig;
import com.hnt.reportautoemail.model.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
class ReportService{

    Logger logger = LoggerFactory.getLogger(ReportService.class);

    private AppConfig appConfig;
    private SendEmailService sendEmailService;

    public ReportService(AppConfig appConfig, SendEmailService sendEmailService) {
        this.appConfig = appConfig;
        this.sendEmailService = sendEmailService;
    }

    @Scheduled(cron = "${ae.config.email-report-interval8-a-m}")
    public void sendReport() {

        //preparing sample data
        List<ProductInfo> productInfoList = Arrays.asList(
                new ProductInfo(1, "Milk", 3000.00, 1, "MMK"),
                new ProductInfo(2, "Bread", 500.00, 3, "MMK"));

        logger.info("Running Time="+ LocalDate.now());

        if(productInfoList != null && productInfoList.size() != 0)
            sendEmailService.sendingMail(productInfoList, "9AM");
        else
            logger.warn("There is no data for voting result");

    }
}
