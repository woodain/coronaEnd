package com.woodain.corona.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodain.corona.constants.PatientStatusEnum;
import com.woodain.corona.constants.TestStatusEnum;
import com.woodain.corona.exception.CoronaException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class CoronaService {
    private Logger logger = LoggerFactory.getLogger(CoronaService.class);
    /** 질병관리본부 사이트 */
    public String URL = "http://ncov.mohw.go.kr/";
    /** 코로나 현황 표시 수 */
    public static final int CORONA_STATUS_PRINT_CNT = 4;
    /** 코로나 검사 표시 수 */
    public static final int CORONA_TEST_PRINT_CNT = 3;

    /** jackson mapper*/
    ObjectMapper objectMapper;

    @Autowired
    public CoronaService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getCoronaDomesticInfo()
    {
        logger.info("Start : getCoronaDomesticInfo ");
        Document doc;
        try {
            doc = Jsoup.connect(URL).get();
            if ( doc == null ) {
                throw new CoronaException("국내 코로나 정보가 존재하지 않습니다.");
            }
        } catch (IOException e) {
            throw new CoronaException("질병관리 본부사이트 접속에 실패했습니다.", e);
        }

        Map<String, Map<String,String>> domesticMap = new HashMap<>();
        //현재시간
        Elements elements = doc.body().select("span.livedate");
        if ( null == elements )
        {
            throw new CoronaException("현재 일자정보가 존재하지 않습니다.");
        }
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("date", elements.get(0).text());
        domesticMap.put("date", dateMap);

        Map<String, String> confirmMap = new HashMap<>();
        //국내 환자 현황
        elements = doc.body().select("ul.liveNum li");
        if ( null == elements || 0 == elements.size() )
        {
            throw new CoronaException("국내 코로나 환자정보가 존재하지 않습니다.");
        }

        if ( CORONA_STATUS_PRINT_CNT != elements.size() )
        {
            throw new CoronaException("국내 코로나 환자정보 수가 맞지 않습니다.");
        }


        for (int i = 0; i < elements.size() ; i++) {
            String num = elements.get(i).select("span.num").text()
                    .replaceAll("[^0-9]","");
            String beforeNum = elements.get(i).select("span.before").text()
                    .replaceAll("[^0-9+-]","");
            confirmMap.put(PatientStatusEnum.values()[i].getTitle(),
                    StringUtils.trimAllWhitespace(num+"("+beforeNum)+")");
        }
        domesticMap.put("confirmStatus", confirmMap);

        //국내 검진 현황
        Map<String, String> testMap = new HashMap<>();
        elements = doc.body().select("ul.suminfo li");
        if ( null == elements || 0 == elements.size() )
        {
            throw new CoronaException("국내 코로나 검사정보가 존재하지 않습니다.");
        }

        if ( CORONA_TEST_PRINT_CNT > elements.size() )
        {
            throw new CoronaException("국내 코로나 검사정보 수가 맞지 않습니다.");
        }

        for (int i = 0; i < elements.size() - 1 ; i++) {
            testMap.put(TestStatusEnum.values()[i].getTitle(),
                    elements.get(i).text().replaceAll("[^0-9.]",""));
        }
        domesticMap.put("testStatus", testMap);

        //지역별 환자현황
        Map<String, String> areaMap = new HashMap<>();
        elements = doc.body().select("div#main_maplayout button");
        for (int i = 0; i < elements.size(); i++) {
            areaMap.put(elements.get(i).select("span.name ").text(),
                    elements.get(i).select("span.num").text()+elements.get(i).select("button span.before").text());
        }
        domesticMap.put("areaStatus", areaMap);

        String resultJsonStr;
        try {
            resultJsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(domesticMap);
        } catch (JsonProcessingException e) {
            throw new CoronaException("국내 코로나 데이터 처리 중 에러가 발생했습니다.", e);
        }
        logger.info("Result : "+ resultJsonStr);
        logger.info("End : getCoronaDomesticInfo ");
        return resultJsonStr;
    }
}
