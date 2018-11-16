package zsp.erasmus;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by barto on 30/09/2017.
 */

public class LoginRequest extends StringRequest {
    static final String REGISTER_REQUEST_URL="http://serwer1727017.home.pl/2ti/ErasmusAPP/login.php";
    Map<String,String> params;
    public LoginRequest(String email, String passwd, Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("passwd",passwd);
    }
    public Map<String,String> getParams(){
        return params;
    }
}
