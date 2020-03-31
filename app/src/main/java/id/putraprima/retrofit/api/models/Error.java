package id.putraprima.retrofit.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Error {
    @SerializedName("email")
    @Expose
    private List<String> email = null;

    @SerializedName("password")
    @Expose
    private List<String> password = null;

    @SerializedName("name")
    @Expose
    private List<String> name = null;

    @SerializedName("confirmPassword")
    private List<String> confirmPassword;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getPassword() {
        return password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }
    @Override
    public String toString(){
        return
                "Error{" +
                        "password = '" + password + '\'' +
                        ",email = '" + email + '\'' +
                        "}";
    }
    public List<String> getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(List<String> confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
