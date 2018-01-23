package com.example.templated;

public class BannerManager {

    private static volatile BannerManager instance;

    private Banner banner;

    private BannerManager(){}

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
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
