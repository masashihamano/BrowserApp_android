package misao.edu.browserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WebView mywebView;
    private EditText urlText;
    private static final String INITIAL_WEBSITE = "https://www.yahoo.co.jp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mywebView = findViewById( R.id.myWebView );
        urlText = findViewById( R.id.urlText );

        mywebView.getSettings().setJavaScriptEnabled( true );
        mywebView.setWebViewClient( new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                getSupportActionBar().setSubtitle( mywebView.getTitle() );
                urlText.setText( url );

            }
        } );

        mywebView.loadUrl( INITIAL_WEBSITE );
    }

    public void clearUrl(View view) {
        urlText.setText( "" );
    }


    public void showWebsite(View view) {
        String url = urlText.getText().toString().trim();

        if (!Patterns.WEB_URL.matcher( url ).matches()) {
            urlText.setError( "Invalid URL" );
        } else {
            if (!url.startsWith( "http://" ) && !url.startsWith( "https://" )) {
                url = "http://" + url;
            }
            mywebView.loadUrl( url );
        }
    }


    @Override
    public void onBackPressed() {
        if (mywebView.canGoBack()) {
            mywebView.goBack();
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mywebView != null) {
            mywebView.stopLoading();
            mywebView.setWebViewClient( null );
            mywebView.destroy();
        }
        mywebView = null;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem forwardItem = (MenuItem)menu.findItem( R.id.action_forward );
        MenuItem backItem = (MenuItem)menu.findItem( R.id.action_back );
        forwardItem.setEnabled( mywebView.canGoForward() );
        backItem.setEnabled( mywebView.canGoBack() );
        return super.onPrepareOptionsMenu( menu );
    }


    //menu/main.xmlファイルを作成
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_reload:
                mywebView.reload();
                return true;
            case R.id.action_forward:
                mywebView.goForward();
                return true;
            case R.id.action_back:
                mywebView.goBack();
                return true;
            default:

            return super.onOptionsItemSelected( item );
        }

    }
}

