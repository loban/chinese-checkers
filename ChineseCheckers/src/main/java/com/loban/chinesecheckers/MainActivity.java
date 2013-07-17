package com.loban.chinesecheckers;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.loban.chinesecheckers.model.Game;
import com.loban.chinesecheckers.view.GameView;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class MainActivity extends Activity {

    // Activity overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new GameView(this, new Game(2)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
