package net.test.servlet;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WebServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        //MultipartFile file = req.getParameter("file");

        req.setCharacterEncoding("utf-8");
        System.out.println(Arrays.toString(req.getParameterValues("week"))+123);
        resp.sendRedirect("/index.jsp");
    }

    @RequestMapping(value = "/jiaoshiModel/getDoc", method = RequestMethod.GET)
    public void getSpb(HttpServletRequest request, HttpServletResponse response) {
        //根据ftl文件生成审批表
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String bianhao = request.getParameter("bianhao");
        JiaoshiModel model = jiaoshiService.queryJiaoshiByBianhao(bianhao);
        if (model != null) {
            //model -> map
            ModelUtils.getModelMap(model, dataMap);
            //公共代码en转cn
            List<CodeModel> codeModelList = codeService.getListByParents(new String[]{Constants.ZJLX, Constants.JSLB, Constants.JKJB});
            for (CodeModel m : codeModelList) {
                //证件类型
                if (model.getZjlx() != null && model.getZjlx().equals(m.getCodekey())) {
                    dataMap.put("zjlx", m.getCodename());
                }
                //教师类别
                if (model.getLb() != null && model.getLb().equals(m.getCodekey())) {
                    dataMap.put("lb", m.getCodename());
                }
                //讲课级别
                if (model.getJkjb() != null && model.getJkjb().equals(m.getCodekey())) {
                    dataMap.put("jkjb", m.getCodename());
                }
            }
            //教师类别区别证件类型之职工号
            if (model.getLb().equals("157")) {
                dataMap.put("zjlx", "职工号");
            }
            //照片BASE64字符串存储
            UploadfileModel newUploadFile = uploadfileService.queryOneByObjectidAndType(String.valueOf(model.getBianhao()), UploadFileUtil.FILETYPE_JS_PHOTO);
            if (newUploadFile != null) {
                String photoPath = FileTool.getWebPath(request) + File.separator + UploadFileUtil.UPLOAD_FILE_PATH +
                        newUploadFile.getType() + File.separator + newUploadFile.getDownloadname();
                dataMap.put("photo", getImageString(photoPath));
            }

            DocumentHandler doc = new DocumentHandler();
            String filePath = FileTool.getWebPath(request) + File.separator + "WEB-INF/file";
            String templateName = "teacher.ftl";
            File templatesFile = new File(filePath + File.separator + templateName);
            if (!templatesFile.exists()) {
                throw new RuntimeException("教师模版文件不存在");
            }
            response.reset();
            //设置文件MIME类型
            response.setContentType("application/msword;charset=UTF-8");
            String xm = null;
            String newDocName = null;
            try {
                xm = model.getXm();
                newDocName = new String(model.getXm().concat(".doc").getBytes("UTF-8"), "ISO_8859_1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            doc.createDoc(dataMap, filePath, templateName, response, newDocName);


        }
    }

    // 将图片转换成BASE64字符串
    public static String getImageString(String imaPath) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imaPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return data != null ? encoder.encode(data) : "";
    }
}
