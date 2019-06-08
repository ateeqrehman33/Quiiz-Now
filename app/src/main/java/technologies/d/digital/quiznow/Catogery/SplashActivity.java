package technologies.d.digital.quiznow.Catogery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import technologies.d.digital.quiznow.Catogery.Contest.QuizContestAct;
import technologies.d.digital.quiznow.R;



public class SplashActivity extends AppCompatActivity {

    String url,token;
    Button button1,quessel,levelsel,contest;
    int diffsel=2,quesnum=10;
    boolean doublleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_splace);
        button1 = (Button)findViewById(R.id.play);

        quessel = findViewById(R.id.selques);

        levelsel = findViewById(R.id.sellevel);

       contest = findViewById(R.id.contest);



        boolean isConnected =AppUtil.isNetworkAvailable(this);

        if(isConnected)
        {
            url = "https://opentdb.com/api_token.php?command=request";

quessel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {



        final NumberPicker picker = new NumberPicker(view.getContext());
        picker.setMinValue(1);
        picker.setMaxValue(20);
        picker.setValue(10);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setWrapSelectorWheel(false);





        final FrameLayout layout = new FrameLayout(view.getContext());
        layout.addView(picker, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));

        new AlertDialog.Builder(view.getContext(),R.style.AppThemedialog)
                .setView(layout)
                .setTitle("Select No Of Questions")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do something with picker.getValue()
                       quesnum = picker.getValue();
                        quessel.setText("Number Of Questions "+quesnum);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();




                    }
});







  levelsel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          final NumberPicker picker = new NumberPicker(view.getContext());
          picker.setMinValue(1);
          picker.setMaxValue(3);
          picker.setValue(2);
          picker.setWrapSelectorWheel(false);
          picker.setDisplayedValues(new String[]{"Easy","Medium","Hard"});
          picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

          final FrameLayout layout = new FrameLayout(view.getContext());
          layout.addView(picker, new FrameLayout.LayoutParams(
                  FrameLayout.LayoutParams.WRAP_CONTENT,
                  FrameLayout.LayoutParams.WRAP_CONTENT,
                  Gravity.CENTER));

          new AlertDialog.Builder(view.getContext(),R.style.AppThemedialog)
                  .setView(layout)

                  .setTitle("Select Difficulty Level")

                  .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          // do something with picker.getValue()
                          diffsel = picker.getValue();
                          levelsel.setText("Difficulty Level "+diffsel);


                      }
                  })
                  .setNegativeButton(android.R.string.cancel, null)
                  .show();


      }
  });


    button1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);



            intent.putExtra("quesnum", quesnum);
            intent.putExtra("difflevel", diffsel);
            startActivity(intent);



    }
});


            contest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SplashActivity.this,QuizContestAct.class);

                    startActivity(intent);
                }
            });













        }

        else {

            Toast.makeText(SplashActivity.this,"Please connect to the internet",Toast.LENGTH_LONG).show();

        }

    }

    private void tokenReq(String url)
    {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);

                             token = json.get("token").toString();

                            ((Token)getApplication()).setToken(token);




                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(SplashActivity.this,"Network error "+e.getMessage(),Toast.LENGTH_LONG).show();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this,"Network error "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


    @Override
    public void onBackPressed() {
        if (doublleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doublleBackToExitPressedOnce=true;
        Toast.makeText(this,"Click BACK again to Exit",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublleBackToExitPressedOnce=false;
            }
        },2000);
    }



}
