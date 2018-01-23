package com.example.webapp.webapp;

public class BannerManager {

    private static volatile BannerManager instance;

    private Banner banner;

    private String url;

    private int visibleLoadActivity = 0;

    private BannerManager(){}

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVisibleLoadActivity() {
        return visibleLoadActivity;
    }

    public void setVisibleLoadActivity(int visibleLoadActivity) {
        this.visibleLoadActivity = visibleLoadActivity;
    }

    public static BannerManager getInstanceBannerManager() {
        if(instance == null)
            synchronized (BannerManager.class) {
                if (null == instance) {
                    instance = new BannerManager();
                }
            }
            return instance;
    }
}
