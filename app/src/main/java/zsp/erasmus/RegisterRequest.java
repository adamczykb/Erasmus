package zsp.erasmus;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by barto on 30/09/2017.
 */

public class RegisterRequest extends StringRequest {
    static final String REGISTER_REQUEST_URL="http://serwer1727017.home.pl/2ti/ErasmusAPP/register.php";
    Map<String,String> params;
    public RegisterRequest(String name, String surname, String email, String team, String passwd, int country,boolean teacher, Response.Listener<String> listener){
    super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("Name",name);
        params.put("surname",surname);
        params.put("email",email);
        params.put("team",team);
        params.put("passwd",passwd);
        params.put("country",country+"");
        params.put("teacher",teacher+"");
    }
    public Map<String,String> getParams(){
        return params;
    }
}
