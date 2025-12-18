package com.dealermanager.mapper_dealer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dealermanager.entity.WaybillEntity;
import com.dealermanager.entity.WaybillStatEntity;
import com.dealermanager.model.waybill.MonthDeclineModel;
import com.dealermanager.model.waybill.MonthStatisticsModel;
import com.dealermanager.model.waybill.TransportStatisticsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface WayBillMapper {

    IPage<WaybillEntity> queryWaybillInfo(IPage<WaybillEntity> page, @Param("params")WaybillEntity waybillEntity);

    WaybillEntity queryWaybillInfoByOrderNo(@Param("params")WaybillEntity waybillEntity);

    WaybillEntity queryWaybillInfoByID(@Param("waybillID")Long waybillID);

    List<WaybillStatEntity> queryWaybillStat(@Param("params")WaybillEntity waybillEntity);

    int addWaybillStat(@Param("params")WaybillStatEntity waybillStatEntity);

    int deleteWaybillStat(@Param("id") Long id);

    int deleteWaybillStatByWayBill(@Param("waybillID")Long waybillID);

    int addWaybill(@Param("params")WaybillEntity waybillEntity);

    int updateWaybill(@Param("params")WaybillEntity waybillEntity);

    int deleteWaybill(@Param("id") Long id);

    List<TransportStatisticsModel> statisticsByTransport(@Param("params")HashMap params);

    List<MonthStatisticsModel> statisticsByMonth(@Param("params") HashMap params);

    MonthStatisticsModel statisticThisYear(@Param("params") HashMap params);

    IPage<MonthDeclineModel> queryWeightDeclineCust(IPage<Map> page,@Param("params") HashMap params);
}
