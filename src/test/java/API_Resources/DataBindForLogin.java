package API_Resources;

import pojo.LoginForEcommerce;

public class DataBindForLogin {
    public LoginForEcommerce addLoginPayload(String userEmail, String userPassword ){
        LoginForEcommerce login = new LoginForEcommerce();
        login.setUserEmail(userEmail);
        login.setUserPassword(userPassword);
        return login;
    }
}
