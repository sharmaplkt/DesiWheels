package com.example.shubham.desiwheels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Button b1;
    TextView terms;
    static EditText name, roll, phone, date, time, room;
    RadioButton rent, hire;
    public static Context context;


    // /personal details
    public static void clear(){
        name.setText("");
        roll.setText("");
        phone.setText("");
        date.setText("");
        time.setText("");
        room.setText("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        b1 = (Button)findViewById(R.id.button1);
        name = (EditText) findViewById (R.id.name);
        roll = (EditText) findViewById (R.id.roll);
        phone = (EditText) findViewById (R.id.phone);
        date = (EditText) findViewById (R.id.date);
        time = (EditText) findViewById (R.id.time);
        room = (EditText) findViewById (R.id.room);
        rent = (RadioButton) findViewById (R.id.rent);
        hire = (RadioButton) findViewById (R.id.hire);

        Log.d("button", "found");
        //b1.setOnClickListener(this);
        //b1.setBackgroundColor(Color.);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                AsyncTaskRunner runner = new AsyncTaskRunner();
                Log.d("runner", "instantiated");
                String link=null;
                if(hire.isChecked()==true)
                {
                    link = "12hire.php";
                    Log.d("Radio", "hire");
                }
                if(rent.isChecked()==true)
                {
                    link = "12rent.php";
                    Log.d("Radio", "rent");
                }
                runner.execute(name.getText().toString(),roll.getText().toString(),phone.getText().toString(),room.getText().toString(),date.getText().toString(),time.getText().toString(),link);
            }
        });

        terms = (TextView) findViewById(R.id.terms);
        terms.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i=new Intent(MainActivity.this,TermsAndConditions.class);
                    startActivity(i);
                }
                catch(Exception e){
                    Log.d("Exception Intent", e.toString());
                    Log.e("comment" , "hello there");
                }

            }
        });


    }

    public static Context getContext() {
        return context;
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
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


}


class AsyncTaskRunner extends AsyncTask<String, String, String> {

    String domain = "http://desiwheels.in/";
    Context context;
    @Override
    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(domain+arg0[6]);

        String result = "Your request is recieved and we'll contact you asap";
        Log.d("async", "started");

        List<NameValuePair> pairs = new ArrayList<NameValuePair>(7);

        pairs.add( new BasicNameValuePair("name", arg0[0]));
        pairs.add( new BasicNameValuePair("roll", arg0[1]));
        pairs.add( new BasicNameValuePair("ph", arg0[2]));
        pairs.add( new BasicNameValuePair("hostel", arg0[3]));
        pairs.add( new BasicNameValuePair("date", arg0[4]));
        pairs.add( new BasicNameValuePair("time", arg0[5]));

        Log.d("upvote", pairs.toString());

        try {
            post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            client.execute(post);
            Log.d("upvoted",post.toString());
        } catch (Exception e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
            result = "error";
        }


        return result;
    }

    @Override
    protected void onPostExecute(String result) {
    Toast.makeText(MainActivity.getContext(), result, Toast.LENGTH_LONG).show();



        try{
            MainActivity.clear();

        }
        catch(Exception e){
            Log.e("Clearing data Exception",e.toString());
        }
    }
}


