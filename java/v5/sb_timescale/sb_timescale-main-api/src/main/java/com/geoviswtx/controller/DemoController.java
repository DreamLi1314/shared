package com.geoviswtx.controller;

import com.geoviswtx.dao.StockCandlestickDailyRepository;
import com.geoviswtx.dao.StockCandlestickMonthRepository;
import com.geoviswtx.dao.StockCandlestickYearRepository;
import com.geoviswtx.domain.StockCandlestickDailyEntity;
import com.geoviswtx.domain.StockCandlestickMonthEntity;
import com.geoviswtx.domain.StockCandlestickYearEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

/**
 * DemoController
 *
 * @Author JackLi 2024/11/5 10:22
 */
@RestController
public class DemoController {

    private final StockCandlestickDailyRepository repository;
    private final StockCandlestickMonthRepository monthRepository;
    private final StockCandlestickYearRepository yearRepository;

    public DemoController(StockCandlestickDailyRepository repository,
                          StockCandlestickMonthRepository monthRepository,
                          StockCandlestickYearRepository yearRepository)
    {
        this.repository = repository;
        this.monthRepository = monthRepository;
        this.yearRepository = yearRepository;
    }

    @GetMapping("/list/day")
    public List<StockCandlestickDailyEntity> listDay() {
        return repository.findAll();
    }

    @GetMapping("/day/findAllDayByTimeAndSymbol")
    public List<StockCandlestickDailyEntity> findAllDayByTimeAndSymbol(
            String symbol, @RequestParam(required = false) Date time)
    {
        Specification<StockCandlestickDailyEntity> specification = (root, criteriaQuery, cb) ->
        {
            //增加筛选条件
            Predicate predicate = cb.conjunction();
            final List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(time != null) {
                expressions.add(cb.equal(root.get("day"), time));
            }

            expressions.add(cb.equal(root.get("symbol"), symbol));

            return predicate;
        };

        return repository.findAll(specification);
    }

    @GetMapping("/list/month")
    public List<StockCandlestickMonthEntity> listMonth() {
        return monthRepository.findAll();
    }

    @GetMapping("/day/findAllMonthByTimeAndSymbol")
    public List<StockCandlestickMonthEntity> findAllMonthByTimeAndSymbol(
            String symbol, @RequestParam(required = false) Date time)
    {
        Specification<StockCandlestickMonthEntity> specification = (root, criteriaQuery, cb) ->
        {
            //增加筛选条件
            Predicate predicate = cb.conjunction();
            final List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(time != null) {
                expressions.add(cb.equal(root.get("mt"), time));
            }

            expressions.add(cb.equal(root.get("symbol"), symbol));

            return predicate;
        };

        return monthRepository.findAll(specification);
    }

    @GetMapping("/list/year")
    public List<StockCandlestickYearEntity> listYear() {
        return yearRepository.findAll();
    }

    @GetMapping("/day/findAllYearByTimeAndSymbol")
    public List<StockCandlestickYearEntity> findAllYearByTimeAndSymbol(
            String symbol, @RequestParam(required = false) Date time)
    {
        Specification<StockCandlestickYearEntity> specification = (root, criteriaQuery, cb) ->
        {
            //增加筛选条件
            Predicate predicate = cb.conjunction();
            final List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(time != null) {
                expressions.add(cb.equal(root.get("yt"), time));
            }

            expressions.add(cb.equal(root.get("symbol"), symbol));

            return predicate;
        };

        return yearRepository.findAll(specification);
    }

}
