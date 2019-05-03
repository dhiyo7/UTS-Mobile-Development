package plugin.id.utsfix;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by haerul on 17/03/18.
 */

public interface ApiInterface {

    @POST("server/POST/readcontacts.php")
    Call<List<Command>> getContacts();

    @GET("retrofit/GET/getcontacts.php")
    Call<List<Command>> getContact(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );

    @FormUrlEncoded
    @POST("retrofit/POST/addcontact.php")
    public Call<Command> insertUser(
            @Field("name") String name,
            @Field("email") String email);

    @FormUrlEncoded
    @POST("retrofit/POST/editcontact.php")
    public Call<Command> editUser(
            @Field("id") String id,
            @Field("name") String name,
            @Field("email") String email);
}
