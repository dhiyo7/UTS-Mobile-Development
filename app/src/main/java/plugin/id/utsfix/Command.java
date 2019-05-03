package plugin.id.utsfix;

import com.google.gson.annotations.SerializedName;

public class Command {

    @SerializedName("id") private int Id;
    @SerializedName("name") private String Name;
    @SerializedName("email") private String Email;
    @SerializedName("value") private String value;
    @SerializedName("message") private String massage;

    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }


    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }
}
