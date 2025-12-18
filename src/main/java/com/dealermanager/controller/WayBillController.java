package com.dealermanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.dealermanager.annotation.Access;
import com.dealermanager.constants.Constants;
import com.dealermanager.entity.CompanyEntity;
import com.dealermanager.entity.WaybillEntity;
import com.dealermanager.entity.WaybillStatEntity;
import com.dealermanager.model.CommonResponse;
import com.dealermanager.model.ResponseEnum;
import com.dealermanager.model.waybill.MonthDeclineModel;
import com.dealermanager.model.waybill.MonthStatisticsModel;
import com.dealermanager.model.waybill.TransportStatisticsModel;
import com.dealermanager.service.UserService;
import com.dealermanager.service.WayBillService;
import com.dealermanager.util.UserContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@Controller
@Api(tags="运单管理")
@RequestMapping("/yixun/waybill")
@Slf4j
public class WayBillController {

    @Autowired
    WayBillService wayBillService;

    @Autowired
    UserService userService;


    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="导入运单", notes="添加运单")
    @RequestMapping(value="/addBatchWaybill",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse addBatchWaybill( @RequestBody List<WaybillEntity> paramList){
        CommonResponse rm = new CommonResponse();
        log.info("-----addBatchWaybill------") ;
        try {
            wayBillService.addBatchWaybill(paramList);
            log.info("----addBatchWaybill-----end");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("addBatchWaybill()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="添加运单", notes="添加运单")
    @RequestMapping(value="/addWaybill",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse addWaybill( @RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        log.info("-----addWaybill------" + JSONObject.toJSONString(params));
        try {
            params.setOperatorID(UserContextHolder.getUserId());
            params.setCompanyID(UserContextHolder.getCompanyID());
            wayBillService.addWaybill(params);
            log.info("---addWaybill-----end");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("addWaybill()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }


    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="修改运单", notes="修改运单")
    @RequestMapping(value="/updateWaybill",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse updateWaybill( @RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        log.info("-----updateWaybill------" + JSONObject.toJSONString(params));
        try {
            if(params.getId() == null ){
                return new CommonResponse(ResponseEnum.BILL_ID_NULL);
            }
            WaybillEntity waybillEntity = wayBillService.queryWaybillInfoByID(params.getId());
            if(waybillEntity == null){
                return new CommonResponse(ResponseEnum.BILL_ID_NOT_EXIST);
            }
            params.setUpdatorID(UserContextHolder.getUserId());
            if(!Objects.equals(waybillEntity.getCompanyID(), UserContextHolder.getCompanyID())){
                return new CommonResponse(ResponseEnum.BILL_AND_USER_COMPANY_NOT_CONSTISTENT);
            }
            wayBillService.updateWaybill(params);
            log.info("---updateWaybill----end");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("updateWaybill()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="删除运单", notes="删除运单")
    @RequestMapping(value="/deleteWaybill",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse deleteWaybill( @RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        log.info("-----deleteWaybill------" + JSONObject.toJSONString(params));
        try {
            if(params.getId() == null ){
                return new CommonResponse(ResponseEnum.BILL_ID_NULL);
            }
            WaybillEntity waybillEntity = wayBillService.queryWaybillInfoByID(params.getId());
            if(waybillEntity == null){
                return new CommonResponse(ResponseEnum.BILL_ID_NOT_EXIST);
            }
            params.setUpdatorID(UserContextHolder.getUserId());
            if(!Objects.equals(waybillEntity.getCompanyID(), UserContextHolder.getCompanyID())){
                return new CommonResponse(ResponseEnum.BILL_AND_USER_COMPANY_NOT_CONSTISTENT);
            }
            wayBillService.deleteWaybill(params.getId());
            log.info("---deleteWaybill----end");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("deleteWaybill()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

//    @CrossOrigin
//    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
//    @ApiOperation(value="添加运单状态", notes="添加运单状态")
//    @RequestMapping(value="/addWaybillStat",method = RequestMethod.POST )
//    @ResponseBody
//    CommonResponse addWaybillStat( @RequestBody WaybillStatEntity params){
//        CommonResponse rm = new CommonResponse();
//        log.info("-----addWaybillStat------" + JSONObject.toJSONString(params));
//        try {
//            if(params.getWaybillID() == null ){
//                return new CommonResponse(ResponseEnum.BILL_ID_NULL);
//            }
//            WaybillEntity waybillEntity = wayBillService.queryWaybillInfoByID(params.getWaybillID());
//            if(waybillEntity == null){
//                return new CommonResponse(ResponseEnum.BILL_ID_NOT_EXIST);
//            }
//            params.setOperatorID(UserContextHolder.getUserId());
//            if(!Objects.equals(waybillEntity.getCompanyID(), UserContextHolder.getCompanyID())){
//                return new CommonResponse(ResponseEnum.BILL_AND_USER_COMPANY_NOT_CONSTISTENT);
//            }
//            wayBillService.updateBillStat(params);
//            wayBillService.addWaybillStat(params);
//            log.info("addWaybillStat----end");
//        }catch (Exception e){
//            rm = new CommonResponse(ResponseEnum.FAILED);
//            log.error("addWaybillStat()==",e);
//        }
//        return  rm;
//    }

//    @CrossOrigin
//    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
//    @ApiOperation(value="删除运单状态", notes="删除运单状态")
//    @RequestMapping(value="/deleteWaybillStat",method = RequestMethod.POST )
//    @ResponseBody
//    CommonResponse deleteWaybillStat( @RequestBody WaybillStatEntity params){
//        CommonResponse rm = new CommonResponse();
//        log.info("-----addWaybillStat------" + JSONObject.toJSONString(params));
//        try {
//            if(params.getId()== null || params.getWaybillID() == null){
//                return new CommonResponse(ResponseEnum.BILLSTAT_ID_NULL);
//            }
//            if(params.getWaybillID() == null){
//                return new CommonResponse(ResponseEnum.BILL_ID_NULL);
//            }
//            WaybillEntity waybillEntity = wayBillService.queryWaybillInfoByID(params.getWaybillID());
//            if(waybillEntity == null){
//                return new CommonResponse(ResponseEnum.BILL_ID_NOT_EXIST);
//            }
//            params.setOperatorID(UserContextHolder.getUserId());
//            if(!Objects.equals(waybillEntity.getCompanyID(), UserContextHolder.getCompanyID())){
//                return new CommonResponse(ResponseEnum.BILL_AND_USER_COMPANY_NOT_CONSTISTENT);
//            }
//            wayBillService.deleteWaybillStat(params.getId());
//            log.info("deleteWaybillStat----end");
//        }catch (Exception e){
//            rm = new CommonResponse(ResponseEnum.FAILED);
//            log.error("deleteWaybillStat()==",e);
//        }
//        return  rm;
//    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    @ApiOperation(value="运单查询", notes="运单查询")
    @RequestMapping(value="/queryWaybillInfo",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse queryWaybillInfo(@RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        try {
            log.info("-----queryWaybillInfo------"+JSONObject.toJSONString(params));
            if(UserContextHolder.getUser() != null){
                params.setCompanyID(UserContextHolder.getCompanyID());
            }else {
                if(params.getCompanySec() == null){
                    return new CommonResponse(ResponseEnum.COMPANYSEC);
                }
                CompanyEntity companyEntity = userService.getCompanyBySec(params.getCompanySec());
                if(companyEntity == null){
                    return new CommonResponse(ResponseEnum.COMPANY_NOT_EXIST);
                }
                params.setCompanyID(companyEntity.getId());
            }
            Map wayBill =  wayBillService.queryWaybillInfo(params);//
            rm.setData(wayBill);
            log.info("---queryWaybillInfo---end");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("queryWaybillInfo()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    @ApiOperation(value="运单查询，根据运单号", notes="根据运单号")
    @RequestMapping(value="/queryWaybillInfoByOrderNo",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse queryWaybillInfoByOrderNo(@RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        try {
            log.info("-----queryWaybillInfoByOrderNo------"+JSONObject.toJSONString(params));
            if(UserContextHolder.getUser() != null){
                params.setCompanyID(UserContextHolder.getCompanyID());
            }else {
                if(params.getCompanySec() == null){
                    return new CommonResponse(ResponseEnum.COMPANYSEC);
                }
                CompanyEntity companyEntity = userService.getCompanyBySec(params.getCompanySec());
                if(companyEntity == null){
                    return new CommonResponse(ResponseEnum.COMPANY_NOT_EXIST);
                }
                params.setCompanyID(companyEntity.getId());
            }
            WaybillEntity wayBill =  wayBillService.queryWaybillInfoByOrderNo(params);//
            rm.setData(wayBill);
            log.info("---queryWaybillInfoByOrderNo---end");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("queryWaybillInfoByOrderNo()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    @ApiOperation(value="运单状态查询", notes="运单状态查询")
    @RequestMapping(value="/queryWaybillStatInfo",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse queryWaybillStatInfo(@RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        try {
            log.info("-----queryWaybillStatInfo------"+JSONObject.toJSONString(params));
            if(UserContextHolder.getUser() != null){
                params.setCompanyID(UserContextHolder.getCompanyID());
            }else {
                if(params.getCompanySec() == null){
                    return new CommonResponse(ResponseEnum.COMPANYSEC);
                }
                CompanyEntity companyEntity = userService.getCompanyBySec(params.getCompanySec());
                if(companyEntity == null){
                    return new CommonResponse(ResponseEnum.COMPANY_NOT_EXIST);
                }
                params.setCompanyID(companyEntity.getId());
            }
            List<WaybillStatEntity>waybillList =  wayBillService.queryWaybillStat(params);//
            rm.setData(waybillList);
            log.info("---queryWaybillStatInfo---end");
        }catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("queryWaybillStatInfo()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }


    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="按运输方式统计", notes="按运输方式统计")
    @RequestMapping(value="/statisticsByTransport",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse statisticsByTransport(@RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        try {
            log.info("-----statisticsByTransport------" + JSONObject.toJSONString(params));
            if (UserContextHolder.getUser() != null) {
                params.setCompanyID(UserContextHolder.getCompanyID());
            }
            List<TransportStatisticsModel> res = wayBillService.statisticsByTransport(params);
            rm.setData(res);
        } catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("statisticsByTransport()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }


    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="按月统计货物量", notes="按月统计货物量")
    @RequestMapping(value="/statisticsByMonth",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse statisticsByMonth(@RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        try {
            log.info("-----statisticsByMonth------" + JSONObject.toJSONString(params));
            if (UserContextHolder.getUser() != null) {
                params.setCompanyID(UserContextHolder.getCompanyID());
            }
            List<MonthStatisticsModel> res = wayBillService.statisticsByMonth(params);
            rm.setData(res);
        } catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("statisticsByMonth()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="统计今年货物量", notes="统计今年货物量")
    @RequestMapping(value="/statisticThisYear",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse statisticThisYear(){
        CommonResponse rm = new CommonResponse();
        try {
            WaybillEntity params = new WaybillEntity();
            log.info("-----statisticThisYear------" + JSONObject.toJSONString(params));
            if (UserContextHolder.getUser() != null) {
                params.setCompanyID(UserContextHolder.getCompanyID());
            }
            MonthStatisticsModel res = wayBillService.statisticThisYear(params);
            rm.setData(res);
        } catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("statisticThisYear()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="环比重量下降或增加的客户", notes="环比重量下降20%的客户")
    @RequestMapping(value="/getMoMDeclineCust",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse getMoMDeclineCust(@RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        try {
            if(params == null) {
                params = new WaybillEntity();
            }
            log.info("-----getMoMDeclineCust------" + JSONObject.toJSONString(params));
            if (UserContextHolder.getUser() != null) {
                params.setCompanyID(UserContextHolder.getCompanyID());
            }
            Map res = wayBillService.getMoMDecline(params);
            rm.setData(res);
        } catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("getMoMDeclineCust()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }

    @CrossOrigin
    @Access(identity = Constants.USER_IDENTIFY_FORMAL)
    @ApiOperation(value="同比重量下降或增加的客户", notes="环比重量下降20%的客户")
    @RequestMapping(value="/getYoYDeclineCust",method = RequestMethod.POST )
    @ResponseBody
    CommonResponse getYoYDeclineCust(@RequestBody WaybillEntity params){
        CommonResponse rm = new CommonResponse();
        try {
            if(params == null){
                params = new WaybillEntity();
            }
            log.info("-----getYoYDeclineCust------" + JSONObject.toJSONString(params));
            if (UserContextHolder.getUser() != null) {
                params.setCompanyID(UserContextHolder.getCompanyID());
            }
            Map res = wayBillService.getYoYDecline(params);
            rm.setData(res);
        } catch (Exception e){
            rm = new CommonResponse(ResponseEnum.FAILED);
            log.error("getYoYDeclineCust()==",e);
            if(e != null && e.getMessage() != null){
                rm.setMessage(e.getMessage());
            }
        }
        return  rm;
    }
}
