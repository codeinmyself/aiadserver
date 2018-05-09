package com.xmu.aiad.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.xmu.aiad.util.BaseController;
import com.xmu.aiad.util.JsonResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

@Controller
public class FileController extends BaseController {
    private Logger logger = Logger.getLogger(this.getClass());

    private static String[] videosName = new String[6];
    private static String[] pathsName = new String[6];
    private static final long FILE_MAX_SIZE = 1024 * 1024 * 200;
    private static final String VIDEO_PATH = "C:\\ffmpeg-shared\\bin\\protects\\AIAD\\resourcefiles\\video\\";
    private static final String Render_Save_Path="C:\\ffmpeg-shared\\bin\\protects\\AIAD\\userfiles\\";
    private static final String Result_Path="C:\\ffmpeg-shared\\bin\\protects\\AIAD\\dealfiles\\deal\\";
    private static File file = new File("C:\\Users\\Administrator\\Desktop\\classify\\caffe-master", "Classify_result.txt");

    private static JSONObject jsonObject = new JSONObject();

    public int getRandomCode(){
        return (int)((Math.random()*9+1)*1000);
    }

    @RequestMapping(value = "/getVerificationCode",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getVerificationCode(HttpServletRequest request, HttpServletResponse response){
        long telephone=Long.parseLong(request.getParameter("telephone"));

        System.out.println(telephone);
        int randomCode=getRandomCode();

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
//替换成你的AK
        final String accessKeyId = "LTAIYRTUMuyt5M1M";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "Qx6fIA4rdXcwqvOum6sIQH2HAKJTJY";//你的accessKeySecret，参考本文档步骤2
//初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try{
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request1 = new SendSmsRequest();
            //使用post提交
            request1.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request1.setPhoneNumbers(telephone+"");
            //必填:短信签名-可在短信控制台中找到
            request1.setSignName("AIAD");
            //必填:短信模板-可在短信控制台中找到
            request1.setTemplateCode("SMS_134220241");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request1.setTemplateParam("{\"name\":\"Tom\", \"code\":\""+randomCode+"\"}");
            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request1.setOutId("yourOutId");
            //请求失败这里会抛ClientException异常
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request1);
            if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {//请求成功
                System.out.println("good");
            }else{
                System.out.println(sendSmsResponse.getCode());
            }
        }catch (ClientException e){
            System.out.println("ClientException Error");
        }
        /*JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",randomCode);*/
        return  renderJsonSucc(randomCode);
    }

    /**
     * 接收上传的图片
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/uploadProductImage", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadProductImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long startTime=System.currentTimeMillis();
        if(file.exists()){
            file.delete();
        }
        System.out.println("upload image ");
        if (!fileHelper(request,"C:\\Users\\Administrator\\Desktop\\classify\\caffe-master\\examples\\images\\"))
            return renderJsonError(null);

        runbat();
        long endTime;
        int timer=0;
        while(!file.exists()){
            endTime=System.currentTimeMillis();
            if((endTime-startTime)/1000>25){
                System.out.println("时间太长，返回吧");
                return renderJsonError("运行时间太长");
            }else{
                try{
                    Thread.sleep(1000);
                    System.out.println(++timer);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        String type[] = new String[5];
        for (int i = 0; i < 5; i++) {
            type[i] = readFileByLines(file, i + 2).substring(20, readFileByLines(file, i + 2).length() - 1);
            jsonObject.put("type" + (i + 1), type[i]);
            System.out.println(type[i]);
        }
        return renderJsonSucc(jsonObject);
    }

    /**
     * 调用bat文件，进行图像识别
     */
    private void runbat() {
        String cmd = "cmd /c start C:\\Users\\Administrator\\Desktop\\classify\\caffe-master\\classify.bat";
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(cmd);
            InputStream input = ps.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String szline;
            while ((szline = reader.readLine())!= null) {
                System.out.println(szline);
            }
            reader.close();
            ps.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ps.destroy();
    }


    @RequestMapping(value = "/getStyle")
    @ResponseBody
    private JsonResult getStyle(HttpServletRequest request, HttpServletResponse response){
        String style=request.getParameter("style");
        if ("清新".equals(style)){
            randomPickVideo("fresh");
        }else{
            randomPickVideo("vintage");
        }
        return renderJsonSucc("ok");
    }
    /**
     * 初始化模板视频路径
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/getVideosName")
    @ResponseBody
    public JsonResult getVideosName(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("调用了getVideosName");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("0", videosName[0]);
        jsonObject.put("1", videosName[1]);
        jsonObject.put("2", videosName[2]);
        jsonObject.put("3", videosName[3]);
        jsonObject.put("4", videosName[4]);
        jsonObject.put("5", videosName[5]);

        return renderJsonSucc(jsonObject);
    }

    /**
     * 接收用户视频
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadVideo(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("调用了uploadVideo");
        if (!fileHelper(request,Render_Save_Path))
            return renderJsonError(null);
        return renderJsonSucc(null);
    }


    /**
     * 接收DateRecoding.txt文档
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/uploadTxt", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadTxt(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        System.out.println("uploadTxt");
        if (!fileHelper(request,Render_Save_Path))
            return renderJsonError(null);
        File file =new File(Render_Save_Path,"DateRecoding.txt");
        return renderJsonSucc(null);
    }

    /**
     * 文件下载工具
     * @param request
     * @param path
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean fileHelper(HttpServletRequest request,String path) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        RequestContext requestContext = new ServletRequestContext(request);
        if (FileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding("UTF-8");
            fileUpload.setFileSizeMax(FILE_MAX_SIZE);
            List<FileItem> items = new ArrayList<FileItem>();
            try {
                items = fileUpload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            Iterator<FileItem> it = items.iterator();
            while (it.hasNext()) {
                FileItem fileItem = (FileItem) it.next();
                if (fileItem.isFormField()) {
                    System.out.println(fileItem.getFieldName()
                            + " "
                            + fileItem.getName()
                            + " "
                            + new String(fileItem.getString().getBytes(
                            "ISO-8859-1"), "GBK"));
                } else {
                    System.out.println(fileItem.getFieldName() + " "
                            + fileItem.getName() + " " + fileItem.isInMemory()
                            + " " + fileItem.getContentType() + " "
                            + fileItem.getSize());
                    if (fileItem.getName() != null && fileItem.getSize() != 0) {
                        File fullFile = new File(fileItem.getName());
                        File newFile = new File(path
                                + fullFile.getName());
                        try {
                            fileItem.write(newFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("no file choosen or empty file");
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 运行渲染器
     */
    private void executeRender(){
        File file = new File("C:\\dll\\aiad.exe");
        try{
            Desktop.getDesktop().open(file);
            System.out.println("成功打开软件和文件！");
        }catch (Exception e){
            System.out.println("打开软件失败");
        }
    }

    /**
     * 下载合成视频
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downloadVideo/{timestamp}")
    public ResponseEntity<byte[]> download(@PathVariable("timestamp") String timestamp) throws IOException {
        executeRender();
        System.out.println("调用了download/timeStamp");
        File file = new File(Render_Save_Path+timestamp+"\\merge1.mp4");
        System.out.println(Render_Save_Path+timestamp+"\\merge1.mp4");
        File file1 = new File(Render_Save_Path+timestamp+"\\endinghint.txt");
        System.out.println(Render_Save_Path+timestamp+"\\endinghint.txt");

        while(!file1.exists()){
            System.out.println("endinghint.txt不存在！");
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("endinghint.txt已经存在！");
        System.out.println("merge1.mp4:"+file.exists());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }


    /**
     * 下载模板视频
     * @param id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downloadVideos/{id}")
    public ResponseEntity<byte[]> downloads(@PathVariable int id) throws IOException {
        System.out.println("调用了downloadsVideos");
        File file;
        switch (id) {
            case 0:
                System.out.println(pathsName[0]);
                file = new File(pathsName[0]);
                if (file.exists()) {
                    System.out.println(pathsName[0]+"存在");
                }
                System.out.println("1");
                break;
            case 1:
                System.out.println(pathsName[1]);
                file = new File(pathsName[1]);
                if (file.exists()) {
                    System.out.println(pathsName[1]+"存在");
                }
                System.out.println("2");
                break;
            case 2:
                System.out.println(pathsName[2]);
                file = new File(pathsName[2]);
                if (file.exists()) {
                    System.out.println(pathsName[2]+"存在");
                }
                System.out.println("3");
                break;
            case 3:
                System.out.println(pathsName[3]);
                file = new File(pathsName[3]);
                if (file.exists()) {
                    System.out.println(pathsName[3]+"存在");
                }
                System.out.println("4");
                break;
            case 4:
                System.out.println(pathsName[4]);
                file = new File(pathsName[4]);
                if (file.exists()) {
                    System.out.println(pathsName[4]+"存在");
                }
                System.out.println("5");
                break;
            case 5:
                System.out.println(pathsName[5]);
                file = new File(pathsName[5]);
                if (file.exists()) {
                    System.out.println(pathsName[5]+"存在");
                }
                System.out.println("6");
                break;
            default:
                file = new File(pathsName[5]);
                break;
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
    }


    /**
     * 读取指定行
     * @param file
     * @param number
     * @return
     * @throws IOException
     */
    public String readFileByLines(File file, int number) throws IOException {

        FileReader fileReader = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(fileReader);
        String txt = "";
        int lines = 0;
        while (txt != null) {
            lines++;
            txt = reader.readLine();
            if (lines == number) {
                System.out.println("第" + reader.getLineNumber() + "的内容是：" + txt + "\n");
                reader.close();
                fileReader.close();
                return txt;
            }
        }
        return null;
    }

    /**
     * 随机挑选视频
     * @param style
     */
    public void randomPickVideo(String style) {
        int[] videono = new int[6];
        videono = randomCommon(1, 40, 6);

        for (int i = 0; i < 6; i++) {
            String videoPath = VIDEO_PATH + style;//视频路径
            if (videono[i] > 9) {
                pathsName[i] = videoPath + videono[i] + ".mp4";
                videosName[i] = style + videono[i];
                System.out.println("pathsName[" + i + "]" + pathsName[i]);
            } else {
                pathsName[i] = videoPath + "0" + videono[i] + ".mp4";
                videosName[i] = style + "0" + videono[i];
                System.out.println("pathsName[" + i + "]" + pathsName[i]);
            }
        }
    }

    /**
     * 产生随机数（不重复）
     * @param min
     * @param max
     * @param n
     * @return
     */
    private static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}
