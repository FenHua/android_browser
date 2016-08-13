package com.example.yan.browserview;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class BrowserView extends Activity
{
    private EditText urlText;
    private Button goButton;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_view);
        //Get a handle to all user interface elements
        urlText=(EditText)findViewById(R.id.url_field);
        goButton=(Button)findViewById(R.id.go_button);
        webView=(WebView)findViewById(R.id.web_view);
        //Setup event handlers
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser();
            }
        });
        urlText.setOnKeyListener(new OnKeyListener()
        {
            public boolean onKey(View view, int keyCode, KeyEvent event)
            {
                if(keyCode==KeyEvent.KEYCODE_ENTER)
                {
                    openBrowser();
                    return true;
                }
                return false;
            }
        });
    }
    //Open a browser on the url specified in the text box
    private void openBrowser()
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlText.getText().toString());
    }
}
