package com.dealermanager.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dealermanager.entity.WaybillEntity;
import com.dealermanager.entity.WaybillStatEntity;
import com.dealermanager.mapper_dealer.WayBillMapper;
import com.dealermanager.model.waybill.MonthDeclineModel;
import com.dealermanager.model.waybill.MonthStatisticsModel;
import com.dealermanager.model.waybill.TransportStatisticsModel;
import com.dealermanager.service.WayBillService;
import com.dealermanager.util.DateUtils;
import com.dealermanager.util.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    WayBillMapper wayBillMapper;


    @Override
    public Map queryWaybillInfo(WaybillEntity waybillEntity) {
        Page<WaybillEntity> page = new Page<>(waybillEntity.getPage(),waybillEntity.getLimit());
        IPage<WaybillEntity> wayBill =  wayBillMapper.queryWaybillInfo(page,waybillEntity);
        Map<String,Object> res = new HashMap();
        res.put("items",wayBill.getRecords());
        res.put("total",wayBill.getTotal());
        res.put("current",wayBill.getCurrent());
        return res;
    }

    @Override
    public WaybillEntity queryWaybillInfoByOrderNo(WaybillEntity waybillEntity) {
        return wayBillMapper.queryWaybillInfoByOrderNo(waybillEntity);
    }

    @Override
    public WaybillEntity queryWaybillInfoByID(Long waybillID) {
        return wayBillMapper.queryWaybillInfoByID(waybillID);
    }


    @Override
    public List<WaybillStatEntity> queryWaybillStat(WaybillEntity waybillEntity) {
        return wayBillMapper.queryWaybillStat(waybillEntity);
    }

    @Override
    public void addWaybillStat(WaybillStatEntity waybillStatEntity) {
        wayBillMapper.addWaybillStat(waybillStatEntity);
    }

    @Override
    public void deleteWaybillStat(Long id) {
        wayBillMapper.deleteWaybillStat(id);
    }

    @Override
    public void deleteWaybillStatByWayBill(Long waybillID){
        wayBillMapper.deleteWaybillStat(waybillID);
    }

    @Override
    @Transactional
    public void   addWaybill(WaybillEntity waybillEntity) {
        WaybillEntity existBill = wayBillMapper.queryWaybillInfoByOrderNo(waybillEntity);
        if(existBill == null) {
            wayBillMapper.addWaybill(waybillEntity);
        }else {
            waybillEntity.setId(existBill.getId());
            wayBillMapper.updateWaybill(waybillEntity);
        }
        if(waybillEntity.getPackageStat() != null){
            Long billId = waybillEntity.getId();
            WaybillStatEntity billStat= new WaybillStatEntity();
            billStat.setWaybillID(billId);
            billStat.setOperatorID(waybillEntity.getOperatorID());
            billStat.setPackageStat(waybillEntity.getPackageStat());
            wayBillMapper.addWaybillStat(billStat);
        }
    }

    @Override
    @Transactional
    public void addBatchWaybill(List<WaybillEntity> waybillEntities){
        for(WaybillEntity waybill:waybillEntities){
            waybill.setOperatorID(UserContextHolder.getUserId());
            waybill.setCompanyID(UserContextHolder.getCompanyID());
            addWaybill(waybill);
        }
    }

    @Override
    public void   updateWaybill(WaybillEntity waybillEntity) {
        wayBillMapper.updateWaybill(waybillEntity);
        if(waybillEntity.getPackageStat() != null){
            Long billId = waybillEntity.getId();
            WaybillStatEntity billStat= new WaybillStatEntity();
            billStat.setWaybillID(billId);
            billStat.setOperatorID(waybillEntity.getOperatorID());
            billStat.setPackageStat(waybillEntity.getPackageStat());
            wayBillMapper.addWaybillStat(billStat);
        }

    }


    @Override
    @Transactional
    public void  deleteWaybill(Long id) {
         wayBillMapper.deleteWaybill(id);
         wayBillMapper.deleteWaybillStatByWayBill(id);
    }

    @Override
    public List<TransportStatisticsModel> statisticsByTransport(WaybillEntity waybillEntity){
        HashMap params = new HashMap();
        params.put("companyID",waybillEntity.getCompanyID());
        params.put("beginMonth",waybillEntity.getBeginDate().replace("-",""));
        params.put("endMonth",waybillEntity.getEndDate().replace("-",""));
        return wayBillMapper.statisticsByTransport(params);
    }

    @Override
    public List<MonthStatisticsModel> statisticsByMonth(WaybillEntity waybillEntity) {
        HashMap params = new HashMap();
        params.put("companyID", waybillEntity.getCompanyID());
        params.put("custNo",waybillEntity.getCustNo());
        params.put("beginMonth", waybillEntity.getBeginDate().replace("-", ""));
        params.put("endMonth", waybillEntity.getEndDate().replace("-", ""));
        List<MonthStatisticsModel> res = wayBillMapper.statisticsByMonth(params);

        if(res == null) res = new ArrayList<>();
        Map<String, MonthStatisticsModel> resMap = res.stream().collect(Collectors.toMap(MonthStatisticsModel::getMonth, Function.identity(), (key1, key2) -> key2));

        List<String> monthList = DateUtils.getMonthBetween(waybillEntity.getBeginDate(), waybillEntity.getEndDate());
        List<MonthStatisticsModel> resList = new ArrayList<>();
        for (int i = 0; i < monthList.size(); i++) {
            if (resMap.containsKey(monthList.get(i))) {
                resList.add(resMap.get(monthList.get(i)));
            } else {
                resList.add(new MonthStatisticsModel(monthList.get(i), 0l, 0d, 0d, 0d, 0l));
            }
        }
        return  resList;
    }


    @Override
    public MonthStatisticsModel statisticThisYear(WaybillEntity waybillEntity){
        HashMap params = new HashMap();
        params.put("companyID",waybillEntity.getCompanyID());
        return wayBillMapper.statisticThisYear(params);
    }

    @Override
    public Map  getYoYDecline(WaybillEntity waybillEntity){
        HashMap<String,Object> params = new HashMap();
        params.put("companyID",waybillEntity.getCompanyID());
        params.put("custNo",waybillEntity.getCustNo());
        params.put("percent",waybillEntity.getPercent() == null? null : waybillEntity.getPercent()/100.0);
        params.put("difference",waybillEntity.getDifference());
        params.put("compareFlag",waybillEntity.getCompareFlag());
        params.put("thisMonth",waybillEntity.getMonth().replace("-", ""));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
        try {
            Calendar lastDate = Calendar.getInstance();
            lastDate.setTime(sdf.parse(waybillEntity.getMonth()));
            lastDate.add(Calendar.YEAR,-1);
            String lastMonth = sdf.format(lastDate.getTime());
            params.put("lastMonth",lastMonth.replace("-", ""));
        }catch (ParseException e) {
            log.error("日期转换异常",e);
        }

        Page<Map> page = new Page<>(waybillEntity.getPage(),waybillEntity.getLimit());
        IPage<MonthDeclineModel> wayBill =  wayBillMapper.queryWeightDeclineCust(page,params);
        Map<String,Object> res = new HashMap();
        res.put("items",wayBill.getRecords());
        res.put("total",wayBill.getTotal());
        res.put("current",wayBill.getCurrent());
        return res;
    }

    public Map  getMoMDecline(WaybillEntity waybillEntity){
        HashMap<String,Object> params = new HashMap();
        params.put("companyID",waybillEntity.getCompanyID());
        params.put("custNo",waybillEntity.getCustNo());
        params.put("percent",waybillEntity.getPercent() == null? null : waybillEntity.getPercent()/100.0);
        params.put("difference",waybillEntity.getDifference());
        params.put("compareFlag",waybillEntity.getCompareFlag());
        params.put("thisMonth",waybillEntity.getMonth().replace("-", ""));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
        try {
            Calendar lastDate = Calendar.getInstance();
            lastDate.setTime(sdf.parse(waybillEntity.getMonth()));
            lastDate.add(Calendar.MONTH,-1);
            String lastMonth = sdf.format(lastDate.getTime());
            params.put("lastMonth",lastMonth.replace("-", ""));
        }catch (ParseException e) {
            log.error("日期转换异常",e);
        }
        Page<Map> page = new Page<>(waybillEntity.getPage(),waybillEntity.getLimit());
        IPage<MonthDeclineModel> wayBill =  wayBillMapper.queryWeightDeclineCust(page,params);
        Map<String,Object> res = new HashMap();
        res.put("items",wayBill.getRecords());
        res.put("total",wayBill.getTotal());
        res.put("current",wayBill.getCurrent());
        return res;
    }
}
