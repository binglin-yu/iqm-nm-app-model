/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tr.rdss.generic.model.iqm.concordance.util;

import com.tr.rdss.generic.model.iqm.concordance.ModelMetadataVO;
import java.util.List;

/**
 *
 * @author U8015921
 */
public interface AttributeConverterService {
    public Long getCurrencyPermIdByCode(String currencyCode) throws InvalidServiceMethodCallException;
    public String getCurrencyCodeByPermId(Long currencyPermId) throws InvalidServiceMethodCallException;
    public Long getModelMetadataPermIdByLevelNameValue(String entityLevel, String propertyName,
        String propertyValue) throws InvalidServiceMethodCallException;
    public String getMetadataValueByEnuPermid(Long enuPermid) throws InvalidServiceMethodCallException;
    public List<ModelMetadataVO> getModelMetadataProperties() throws InvalidServiceMethodCallException;
} 
