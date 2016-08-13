package com.example.yan.localbrowser;

import android.app.Activity;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocalBrowser extends Activity
{
    private static final String TAG="localBrowser";
    private final Handler handler=new Handler();
    private WebView webView;
    private TextView textView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_browser);
        //Find the android controls on the screen
        webView=(WebView)findViewById(R.id.web_view);
        textView=(TextView)findViewById(R.id.text_view);
        button=(Button)findViewById(R.id.button);
        webView.loadUrl("file:///android_asset/index.html");
        //Turn on JavaScript in the embedded browser
        webView.getSettings().setJavaScriptEnabled(true);
        //Expose a java object to Javascript in the browser
        webView.addJavascriptInterface(new AndroidBridge(),"android");
        //Set up a function to be called when javascript tries
        // to open an alert window
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(final WebView view,final String url,final String message,JsResult result )
            {
                Log.d(TAG,"onJsAlert("+view+","+url+","+message+","+result+")");
                Toast.makeText(LocalBrowser.this,message,3000).show();
                result.confirm();
                return true;//I handled it
            }
        });
        //This function will be called when the user presses the button on the android side
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick("+view+")");
                webView.loadUrl("javascript:callJS('Hello from Android')");
            }
        });
    }
    //Object exposed to Javascript
    private class AndroidBridge
    {
        public void callAndroid(final String arg)
        {
            //must be final
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"callAndroid("+arg+")");
                    textView.setText(arg);
                }
            });
        }
    }
}

