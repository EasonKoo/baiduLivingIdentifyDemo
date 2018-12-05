package com.baidu.ai.demo.controller;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * Java doc
 *
 * @author songqingyun@baidu.com on 2018/5/15.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    /**
     * 百度云 应用管理相关配置， 建议放在配置文件中
     */
    private String appId = "14753033"; // https://cloud.baidu.com 应用管理获取 AppID
    private String apiKey = "ly4SSQD4OmR3U1Caf8kYcfEW";// https://cloud.baidu.com 应用管理获取 API Key
    private String secretKey = "ZQrMwPoP7R6FivGWGmUCNy29mirE0Qhz"; // https://cloud.baidu.com 应用管理获取 Secret Key

    @RequestMapping("/pc")
    public String pc() {
        return "pc";
    }

    @RequestMapping("/mobile")
    public String mobile() {
        return "mobile";
    }

    @RequestMapping(value = "/aidemo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String aidemo(String apiType, String type, HttpServletRequest request) {
        AipFace client = new AipFace(appId, apiKey, secretKey);

        // 可选：设置网络连接参数
        JSONObject rs = new JSONObject();
        rs.put("errno", 0);
        rs.put("msg", "success");

        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        if ("faceliveness_sessioncode".equalsIgnoreCase(type)) {
            JSONObject aaa= client.videoSessioncode(null);
            rs.put("data", aaa);
        } else {
            String sessionId = request.getParameter("session_id");
            String base64 = request.getParameter("video_base64");

            JSONObject json = client.videoFaceliveness(sessionId, Base64.getDecoder().decode(base64), null);
            // TODO 获取到接口返回的内容可以在这儿写 其他逻辑
            rs.put("data", json);
        }
        return rs.toString();
    }
}
