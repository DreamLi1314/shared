package com.geoviswtx.dao;

import com.geoviswtx.domain.StockCandlestickMonthEntity;
import com.geoviswtx.domain.StockCandlestickYearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

/**
 * @author Jack Li
 * @date 2024/02/04 下午1:40
 * @description
 */
public interface StockCandlestickYearRepository extends JpaRepository<StockCandlestickYearEntity, Date>,
   JpaSpecificationExecutor<StockCandlestickYearEntity>
{

}
