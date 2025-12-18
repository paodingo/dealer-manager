package com.dealermanager.service;

import com.dealermanager.entity.WaybillEntity;
import com.dealermanager.entity.WaybillStatEntity;
import com.dealermanager.model.waybill.MonthDeclineModel;
import com.dealermanager.model.waybill.MonthStatisticsModel;
import com.dealermanager.model.waybill.TransportStatisticsModel;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface WayBillService {

    Map queryWaybillInfo(WaybillEntity waybillEntity);

    WaybillEntity queryWaybillInfoByOrderNo(WaybillEntity waybillEntity);

    WaybillEntity queryWaybillInfoByID(Long waybillID);

    List<WaybillStatEntity> queryWaybillStat(WaybillEntity waybillEntity);

    void addWaybillStat(WaybillStatEntity  waybillStatEntity);

    void deleteWaybillStat(Long id);

    void deleteWaybillStatByWayBill(Long waybillID);

    void  addWaybill(WaybillEntity waybillEntity);

    void  addBatchWaybill(List<WaybillEntity> waybillEntities);

    void updateWaybill(WaybillEntity waybillEntity);

    void deleteWaybill(Long id);

    List<TransportStatisticsModel> statisticsByTransport(WaybillEntity waybillEntity);

    List<MonthStatisticsModel> statisticsByMonth(WaybillEntity waybillEntity);

    MonthStatisticsModel statisticThisYear(WaybillEntity waybillEntity);

    Map getYoYDecline(WaybillEntity waybillEntity);

    Map getMoMDecline(WaybillEntity waybillEntity);
}
