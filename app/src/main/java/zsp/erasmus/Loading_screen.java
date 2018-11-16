package zsp.erasmus;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by julupukki on 27.09.17.
 */

public class Loading_screen  extends MainActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.loading_screen);

        //Remove title bar
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 2 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(Loading_screen.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
