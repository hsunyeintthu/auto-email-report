package com.hnt.reportautoemail.service;

import com.hnt.reportautoemail.model.ProductInfo;
import com.hnt.reportautoemail.model.response.BaseResponse;

import java.io.IOException;
import java.util.List;

public interface GeneratePdfService {

    BaseResponse preparePdf(List<ProductInfo> productInfoList) throws IOException;
}
