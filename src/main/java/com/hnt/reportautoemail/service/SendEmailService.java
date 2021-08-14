package com.hnt.reportautoemail.service;

import com.hnt.reportautoemail.model.ProductInfo;

import java.util.List;

public interface SendEmailService {

    void sendingMail(List<ProductInfo> productInfoList, String time);
}
