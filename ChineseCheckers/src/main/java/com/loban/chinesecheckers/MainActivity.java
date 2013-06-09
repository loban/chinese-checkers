package com.loban.chinesecheckers;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.loban.chinesecheckers.model.Board;
import com.loban.chinesecheckers.view.BoardView;

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
        setContentView(new BoardView(this, new Board()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
