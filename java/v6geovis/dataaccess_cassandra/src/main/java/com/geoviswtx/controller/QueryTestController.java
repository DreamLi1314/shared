package com.geoviswtx.controller;

import com.geoviswtx.service.GridQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class QueryTestController {

    private final GridQueryService gridQueryService;

    public QueryTestController(GridQueryService gridQueryService) {
        this.gridQueryService = gridQueryService;
    }

    @GetMapping("/test/future24h/{datasetType}/{elem}/{lat}/{lon}/{z}")
    public List<Double> future24h(@PathVariable("datasetType") String datasetType,
                                  @PathVariable("elem") String elem,
                                  @PathVariable("lat") Double lat,
                                  @PathVariable("lon") Double lon,
                                  @PathVariable("z") Double z,
                                  @RequestParam("baseTime") Date baseTime)
    {
        return gridQueryService.queryFuture24Hour(baseTime, datasetType, elem, lat, lon, z);
    }

}
