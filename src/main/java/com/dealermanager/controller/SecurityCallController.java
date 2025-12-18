package com.dealermanager.controller;

import com.dealermanager.annotation.Access;
import com.dealermanager.config.SecurityCallConfig;
import com.dealermanager.constants.Constants;
import com.dealermanager.entity.SecurityCallConfigEntity;
import com.dealermanager.entity.SecurityCommonConfigEntity;
import com.dealermanager.model.CommonResponse;
import com.dealermanager.model.ResponseEnum;
import com.dealermanager.service.SecurityCallService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "证券报价")
@RestController
@RequestMapping("/yixun/security")
public class SecurityCallController {
    private  static DataFormatter dataFormatter = new DataFormatter();
    private  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    SecurityCallService callService;

    @Autowired
    SecurityCallConfig callConfig;

    @PostMapping("/callConfig")
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    public CommonResponse callConfig() throws Exception {
        callConfig.run(null);
        return new CommonResponse();
    }
    @PostMapping("/call")
    @Access(identity = Constants.USER_IDENTIFY_ANONYMOUS)
    public CommonResponse call() throws IOException {
        CommonResponse rm = new CommonResponse();
        String res = "";
        String PATH = "D:\\";
        FileInputStream inputStream = new FileInputStream(PATH + "A港场外期权报价内部参考1013.xlsx");


        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        int date = Integer.parseInt(formattedDate);

        //1.创建工作簿,使用excel能操作的这边都看看操作
        Workbook workbook = new XSSFWorkbook(inputStream);
        int  sheetSize = workbook.getNumberOfSheets();
        //2.得到表
        for(int i = 0 ; i < sheetSize; i++){
            Map<String, Map<String,Object>> callValueMap = new HashMap<>();
            Sheet sheet = workbook.getSheetAt(i);
            //3.得到表名称
            String sheetName = sheet.getSheetName();
            if(!SecurityCallConfig.commonConfigEntityMap.containsKey(sheetName)){
                res = res +"、"+sheetName;
                continue;
            }
            SecurityCommonConfigEntity commonConfig = SecurityCallConfig.commonConfigEntityMap.get(sheetName);
            List<SecurityCallConfigEntity> callConfigList = SecurityCallConfig.callConfigEntityMap.get(sheetName);
            //4.得到行
            int lastRow = sheet.getLastRowNum();

            for(int j = commonConfig.getBeginRow(); j <= lastRow; j++) {
                Row row = sheet.getRow(j);
                if(row == null){
                    continue;
                }
                int securityCodeCol = commonConfig.getSecurityCodeCol();
                int securityNameCol = commonConfig.getSecurityNameCol();
                int sizeCol = commonConfig.getSizeCol();
                int sizeUnit = commonConfig.getSizeUnit();

                Cell securityCodeCell = row.getCell(securityCodeCol);
                Cell securityNameCell = row.getCell(securityNameCol);


                String securityCode = getValue(securityCodeCell);
                if(StringUtils.isEmpty(securityCode)){
                    continue;
                }
                String securityName = getValue(securityNameCell);

                Long size = -1l;
                if(sizeCol != -1){
                    Cell sizeCell = row.getCell(sizeCol);
                    String sizeVal = getValue(sizeCell);
                    if(StringUtils.isEmpty(sizeVal)){
                        sizeVal = "0";
                    }
                    try {
                        size = (long)(Double.valueOf(sizeVal) * sizeUnit);
                    }catch (Exception e) {
                        size = 0l;
                    }
                }

                for(SecurityCallConfigEntity callConfig : callConfigList){
                    String callIndex =  callConfig.getCallIndex();
                    String callTime = callConfig.getCallTime();
                    int callValueCol = callConfig.getCallValueCol();
                    Cell callValueCell = row.getCell(callValueCol);
                    String callValue = getValue(callValueCell);
                    String key =  sheetName + "_" + securityCode + "_" + callIndex;
                    if(!callValueMap.containsKey(key)){
                        Map<String,Object> valueMap = new HashMap<>();
                        valueMap.put("company",sheetName);
                        valueMap.put("securityCode",securityCode);
                        valueMap.put("securityName",securityName);
                        valueMap.put("callIndex",callIndex);
                        valueMap.put("size",size);
                        valueMap.put("date",date);
                        callValueMap.put(key,valueMap);
                    }
                    Map<String,Object> valueMap = callValueMap.get(key);
                    valueMap.put(callTime,callValue);
                }
            }
            callService.addSecurityValues(date,sheetName,callValueMap.values());
        }
        inputStream.close();
        log.info("导入完成");
        if(StringUtils.isEmpty(res)){
            res = "导入完成";
        }else {
            res = res.substring(1);
            res = "以下sheet页缺少配置:" + res +"企业sheet导入完成";
            rm.setCode(ResponseEnum.CALL_CONFIG_ERROR.getCode());
        }
        rm.setMessage(res);
        return rm;
    }


    public  String getValue(Cell cell){
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
            //匹配类型数据
            String cellValue = "";
            switch (cellType) {
                case STRING: //字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC: //字符串
                    //cellValue = String.valueOf((long)cell.getNumericCellValue());
                    cellValue = dataFormatter.formatCellValue(cell);
                    break;
                case BOOLEAN: //布尔类型
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK: //空
                    break;
                case ERROR:
                    break;
            }
            return cellValue;
        }
        return "";
    }
}
