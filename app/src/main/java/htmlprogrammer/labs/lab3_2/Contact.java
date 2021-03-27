package htmlprogrammer.labs.lab3_2;

import android.net.Uri;

import java.net.URI;

public class Contact {
    private String name;
    private String email;
    private String phone;
    private Uri imgRes;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getImgRes() {
        return imgRes;
    }

    public void setImgRes(Uri imgRes) {
        this.imgRes = imgRes;
    }
}
