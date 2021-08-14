package com.hnt.reportautoemail.service;

import com.hnt.reportautoemail.config.AppConfig;
import com.hnt.reportautoemail.model.EmailCode;
import com.hnt.reportautoemail.model.ProductInfo;
import com.hnt.reportautoemail.model.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    @Value("${ae.config.report-filename}")
    String reportFilename;

    JavaMailSender javaMailSender;

    private AppConfig appConfig;

    private GeneratePdfService generatePdfService;

    @Autowired
    public SendEmailServiceImpl(JavaMailSender javaMailSender, AppConfig appConfig, GeneratePdfService generatePdfService) {
        this.javaMailSender = javaMailSender;
        this.appConfig = appConfig;
        this.generatePdfService = generatePdfService;
    }

    @Override
    public void sendingMail(List<ProductInfo> productInfoList, String time)
    {
        MimeMessage message = javaMailSender.createMimeMessage();
        try
        {
            logger.info("Sending with Attached...");
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            String from = "****@gmail.com";
            String[] toMails ={"****@gmail.com"};
            String[] ccMails ={"****@gmail.com"};
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(toMails);
            simpleMailMessage.setCc(ccMails);
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            String text = date.format(formatter);
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            String text1 = date.format(formatter1);
            simpleMailMessage.setSubject("Payment Receipt " + text + " " + time);
            StringBuilder sb = new StringBuilder();
            sb.append("Dear Customer,");
            sb.append("\n");
            sb.append("\n");
            sb.append("Please see attached file for your payment receipt." );
            sb.append("\n").append("\n");
            sb.append("Best Regards,");
            sb.append("\n");
            sb.append("Admin");
            sb.append("\n").append("\n");
            sb.append("This Email is sent by Automated Program.");
            System.out.println(sb.toString());
            simpleMailMessage.setText(sb.toString());

            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setCc(simpleMailMessage.getCc());
            helper.setSubject(simpleMailMessage.getSubject());

            helper.setText(simpleMailMessage.getText());
            //String[] columns = {"No", "Contestant", "Voting Number","EP 9-10 SMS Total Votes","EP 11-12 SMS Total Votes","EP 13-14 SMS Total Votes","EP 15-16 SMS Total Votes","EP 17-18 SMS Total Votes","EP 19-20 SMS Total Votes","EP 21-22 SMS Total Votes","EP 21-22 Web Total Votes","EP 23-24 SMS Total Votes","EP 23-24 Web Total Votes", "EP 25 SMS Votes (Final Result at " + time +")","EP 25 Web Votes (Final Result at " + time +")", "SMS Total Votes", "Web Total Votes"};

            /*ByteArrayInputStream byteArrayInputStream =
                    ExcelGenerator.generateExcelForMMIdolVotingResult1(votingResult, columns);*/

            BaseResponse pdfResponse = generatePdfService.preparePdf(productInfoList);
            if (pdfResponse.getErrorCode() == EmailCode.Fail.code){
                logger.error("Error in generating report");
            }else {
                byte[] outputFile = (byte[]) pdfResponse.getObject();
                ByteArrayResource byteArrayResource = new ByteArrayResource(outputFile);
                helper.addAttachment(reportFilename, byteArrayResource);
            }

            //use this to send attachment
            //helper.addAttachment(reportFilename, new ByteArrayResource(IOUtils.toByteArray(byteArrayInputStream)));

            javaMailSender.send(message);

        } catch (MessagingException | IOException e) {
            throw new MailParseException(e);
        }

        logger.info("Mail sent...");
    }
}
