package pl.lodz.p.ftims.geocaching.GUI;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import pl.lodz.p.ftims.geocaching.R;

public class Koordynaty_mapa extends ActionBarActivity {

    /**
     * powiąanie z layoutem
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koordynaty_mapa2);
    }

    /**
     * dodanie menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_koordynaty_mapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Powrót do layoutu edycja_wskazówki
     * @param v
     */
    public void Cofnij_Koordynaty(View v){
        Button Cofnij = (Button) findViewById(R.id.Powrot_Button);
        Cofnij.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Edycja_wskazowki.class);
                startActivityForResult(intent,0);
            }
        });
    }

    /**
     * zatwierdzenie koordynatów i powrót do layoutu edycja_wskazówki
     * @param v
     */
    public void Zatwierdz_Koordynaty(View v){
        Button Cofnij = (Button) findViewById(R.id.OK_Button);
        Cofnij.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Edycja_wskazowki.class);
                startActivityForResult(intent,0);
            }
        });
    }

}
