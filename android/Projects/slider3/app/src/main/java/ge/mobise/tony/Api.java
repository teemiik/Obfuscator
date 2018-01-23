package ge.mobise.tony;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Artem Koshkin
 * Telegram: @artemkoshkin
 */

public interface Api {

    @GET
    Call<Banner> getBanner(@Url String path);

}
