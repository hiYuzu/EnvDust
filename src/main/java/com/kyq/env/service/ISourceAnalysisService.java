package com.kyq.env.service;


import com.kyq.env.model.SourceAnalysisResultModel;

public interface ISourceAnalysisService {

    SourceAnalysisResultModel getPollutionSourceData(int areaId, double longitude, double latitude, String datetime, double radius);
}