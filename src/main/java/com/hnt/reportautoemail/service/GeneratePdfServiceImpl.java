package com.hnt.reportautoemail.service;

import com.hnt.reportautoemail.model.EmailCode;
import com.hnt.reportautoemail.model.ProductInfo;
import com.hnt.reportautoemail.model.response.BaseResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.activation.URLDataSource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeneratePdfServiceImpl implements GeneratePdfService{

    Logger logger = LoggerFactory.getLogger(GeneratePdfServiceImpl.class);

    @Override
    public BaseResponse preparePdf(List<ProductInfo> productInfoList) throws IOException {
        // Get your data source
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(productInfoList);
        //BufferedImage bufferedImage = ImageIO.read(new File("D:\\JDC\\workspace\\mmcare\\report-auto-email\\src\\main\\resources\\img\\logo.jpg"));

        URLDataSource logo = new URLDataSource(GeneratePdfServiceImpl.class.getResource("/img/logo.jpg"));
        BufferedImage bufferedImage = ImageIO.read(logo.getInputStream());

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
        byte[] imageBytes = data.getData();

        // Add parameters with sample data
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("formTitle", "Test Shop");
        parameters.put("customerName", "Hsu Nyeint Thu");
        parameters.put("merchOrderId", "abc-123");
        parameters.put("transactionDate", LocalDateTime.now());
        parameters.put("amount", 4500);
        parameters.put("paymentType", "MPU");
        parameters.put("logo", /*new ByteArrayInputStream(imageBytes)*/logo.getInputStream());

        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
        try {
            // Compile the Jasper report from .jrxml to .japser
            URLDataSource report = new URLDataSource(GeneratePdfServiceImpl.class.getResource("/report/receipt.jrxml"));
            jasperReport = JasperCompileManager.compileReport(report.getInputStream());
            // Fill the report
            jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters,
                    jrBeanCollectionDataSource
            );
            // Export the report to a PDF file
            byte[] outputFile = JasperExportManager.exportReportToPdf(jasperPrint);

            logger.info("Done generating report");
            return new BaseResponse(EmailCode.Success.code, outputFile, EmailCode.Success.message);
        } catch (JRException e) {
            logger.error(e.getMessage());
            return new BaseResponse(EmailCode.Fail.code, e.getMessage(), EmailCode.Fail.message);
        }
    }
}
