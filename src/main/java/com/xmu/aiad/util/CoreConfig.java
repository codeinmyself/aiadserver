package com.xmu.aiad.util;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by yyp on 2016/10/26.
 */
public class CoreConfig {

    //打包模式
    public static final String PACKAGING_MODE_DEVELOP = "develop";
    public static final String PACKAGING_MODE_PRODUCT = "product";

    //loginUrl
    public static final String PACKING_LOGIN_URL_DEV = "module/index/login.ftl";
    public static final String PACKING_LOGIN_URL_PRO = "/user/login.html?source=SNS";
    //uploadUrl
    public static final String PACKING_IMAGE_UPLOAD_URL_DEV = "/static/upload";
    public static final String PACKING_IMAGE_UPLOAD_URL_PRO = "/srv/www/htdocs/sss/upload";
    //staticUrl
    public static final String PACKING_STATIC_URL_DEV = "http://127.0.0.1:8080/sns/static";
    public static final String PACKING_STATIC_URL_PRO = "/sss";



    private static Map<String, PackingConfig> configMap = new HashMap<String, PackingConfig>();

    public static final String PACKAGING_MODE = PACKAGING_MODE_DEVELOP;

    public CoreConfig() {

        //开发环境配置
        PackingConfig developConfig = new PackingConfig();
        developConfig.setLoginUrl(PACKING_LOGIN_URL_DEV);
        developConfig.setStaticUrl(PACKING_STATIC_URL_DEV);
        developConfig.setImageUploadUrl(PACKING_IMAGE_UPLOAD_URL_DEV);

        configMap.put(PACKAGING_MODE_DEVELOP, developConfig);

        //生产环境配置
        PackingConfig productConfig = new PackingConfig();
        productConfig.setLoginUrl(PACKING_LOGIN_URL_PRO);
        productConfig.setStaticUrl(PACKING_STATIC_URL_PRO);
        productConfig.setImageUploadUrl(PACKING_IMAGE_UPLOAD_URL_PRO);

        configMap.put(PACKAGING_MODE_PRODUCT, productConfig);

    }

    public static final CoreConfig getInstance() {

        return CoreConfigHolder.INSTANCE;
    }

    public static String getPackagingMode() {
        return PACKAGING_MODE;
    }

    public boolean isDevelopMode() {
        return PACKAGING_MODE_DEVELOP.equals(getPackagingMode());
    }

    public PackingConfig getPackingConfig() {
        String packingMode = getPackagingMode();
        if (configMap.containsKey(packingMode)) {
            return configMap.get(packingMode);
        }
        return configMap.get(PACKAGING_MODE_PRODUCT);
    }

    private static class CoreConfigHolder {

        private static final CoreConfig INSTANCE = new CoreConfig();

    }

    public class PackingConfig {

        private String loginUrl;//登录页面
        private String imageUploadUrl;//上传图片路径
        private String staticUrl;//静态资源路径

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        public String getImageUploadUrl() {
            return imageUploadUrl;
        }

        public void setImageUploadUrl(String imageUploadUrl) {
            this.imageUploadUrl = imageUploadUrl;
        }


        public String getStaticUrl() {
            return staticUrl;
        }

        public void setStaticUrl(String staticUrl) {
            this.staticUrl = staticUrl;
        }
    }
}

