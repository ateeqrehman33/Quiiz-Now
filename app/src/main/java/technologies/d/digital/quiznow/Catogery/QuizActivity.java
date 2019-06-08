package technologies.d.digital.quiznow.Catogery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import technologies.d.digital.quiznow.R;

public class QuizActivity extends AppCompatActivity {
	int score=0;
	int qid=0;
	int qcount=0;
	 String url;
	TextView txtQuestion,tvtimer,quesnumber;
	Button opt1,opt2,opt3,opt4;
	Question currentQ, cansQ;
	CountDownTimer timer;


    String token;
     boolean qtype=true;
	ArrayList<Question> question = new ArrayList<Question>();
	ArrayList<Question> cquestion = new ArrayList<Question>();
	int cat;
	int quesnum;
	int difflevel;
    String slevel;
    boolean doublleBackToExitPressedOnce = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		 setContentView(R.layout.activity_quiz);






            tvtimer=(TextView)findViewById(R.id.timer);
    		txtQuestion=(TextView)findViewById(R.id.textView1);
    		quesnumber = (TextView)findViewById(R.id.quesnum);
        opt1 =  findViewById(R.id.opt1);
        opt2 =  findViewById(R.id.opt2);

        opt3 =  findViewById(R.id.opt3);

        opt4 =  findViewById(R.id.opt4);


        opt1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    opt1.setBackgroundColor(Color.parseColor("#e6afafaf"));
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    opt1.setBackgroundColor(Color.parseColor("#ffffff"));

                }

                return false;
            }
        });



        opt2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    opt2.setBackgroundColor(Color.parseColor("#e6afafaf"));
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    opt2.setBackgroundColor(Color.parseColor("#ffffff"));

                }

                return false;
            }
        });




        opt3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    opt3.setBackgroundColor(Color.parseColor("#e6afafaf"));
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    opt3.setBackgroundColor(Color.parseColor("#ffffff"));

                }

                return false;
            }
        });



        opt4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    opt4.setBackgroundColor(Color.parseColor("#e6afafaf"));
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    opt4.setBackgroundColor(Color.parseColor("#ffffff"));

                }

                return false;
            }
        });

















        Bundle bundle = getIntent().getExtras();
        cat = bundle.getInt("cat");
        quesnum = bundle.getInt("quesnum");
        difflevel = bundle.getInt("difflevel");

        if (difflevel == 1){
            slevel = "easy";
        }
        if(difflevel ==2){
          slevel = "medium";
        }
        if (difflevel == 3){
            slevel = "hard";
        }




        LoadQ();


        opt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    try{
                        handleButtonClick(1);
                        opt1.setAlpha(1);
                    }
                    catch (Exception err)
                    {
                        networkDialog();
                    }



                      }
            });

        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{
                    handleButtonClick(2);
                    opt2.setAlpha(1);
                }
                catch (Exception err)
                {
                    networkDialog();
                }


            }
        });

        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    handleButtonClick(3);
                    opt3.setAlpha(1);
                }
                catch (Exception err)
                {
                    networkDialog();
                }




			}

        });

        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    handleButtonClick(4);
                    opt4.setAlpha(1);
                }
                catch (Exception err)
                {
                    networkDialog();
                }

            }
        });



    }


    @Override
    public void onBackPressed() {
        if (doublleBackToExitPressedOnce) {
            super.onBackPressed();
            stoponClick();
            return;
        }
        this.doublleBackToExitPressedOnce=true;
        Toast.makeText(this,"Click BACK again to Quit Quiz",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doublleBackToExitPressedOnce=false;
            }
        },2000);
    }







    public void startonClick() {

        timer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(final long millseclefttofinish) {
                String time = String.valueOf(millseclefttofinish / 1000);
                tvtimer.setText(time);
            }

            @Override
            public void onFinish() {
                timer.cancel();
                tvtimer.setText(".");
                if(qcount<quesnum)
                    setQuestionView();
                else
                    endquiz();




            }
        };
        timer.start();
    }

        public void stoponClick(){
	    timer.cancel();

        }








    void handleButtonClick(int b)
	{


	    stoponClick();
        opt1.setEnabled(false);
        opt2.setEnabled(false);
        opt3.setEnabled(false);
        opt4.setEnabled(false);


       // Toast.makeText(QuizActivity.this,cquestion.get(qid-1).getANSWER(),Toast.LENGTH_LONG).show();

        if(opt1.getText().toString().equals(cquestion.get(qid-1).getANSWER())) {
            opt1.setBackgroundResource(R.color.green);
            opt1.setTextColor(Color.WHITE);
        }
        if(opt2.getText().toString().equals(cquestion.get(qid-1).getANSWER())) {
            opt2.setBackgroundResource(R.color.green);
            opt2.setTextColor(Color.WHITE);
        }
        if(opt3.getText().toString().equals(cquestion.get(qid-1).getANSWER())) {
            opt3.setBackgroundResource(R.color.green);
            opt3.setTextColor(Color.WHITE);
        }
        if(opt4.getText().toString().equals(cquestion.get(qid-1).getANSWER())) {
            opt4.setBackgroundResource(R.color.green);
            opt4.setTextColor(Color.WHITE);
        }


        if(b==1)
        {
            if (opt1.getText().toString().equals(cquestion.get(qid-1).getANSWER()))
            score++;
            else {
                opt1.setBackgroundResource(R.color.red);
                opt1.setTextColor(Color.WHITE);
            }
        }

        if(b==2)
        {
            if (opt2.getText().toString().equals(cquestion.get(qid-1).getANSWER()))
                score++;
            else {
                opt2.setBackgroundResource(R.color.red);
                opt2.setTextColor(Color.WHITE);
            }
        }
        if(b==3)
        {
            if (opt3.getText().toString().equals(cquestion.get(qid-1).getANSWER()))
                score++;
            else {
                opt3.setBackgroundResource(R.color.red);
                opt3.setTextColor(Color.WHITE);
            }
        }
        if(b==4)
        {
            if (opt4.getText().toString().equals(cquestion.get(qid-1).getANSWER()))
                score++;
            else {
                opt4.setBackgroundResource(R.color.red);
                opt4.setTextColor(Color.WHITE);
            }
        }
		if(qcount==quesnum){

         endquiz();

        }
		else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    setQuestionView();


                }
            }, 1800);


		}

    }

public void endquiz(){

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            Intent i = new Intent(QuizActivity.this,ResultActivity.class);

            qcount=0;

            i.putExtra("Score",score);
            i.putExtra("Category",cat);
            i.putExtra("token",token);
            i.putExtra("quesnum",quesnum);
            i.putExtra("difflevel",difflevel);
            startActivityForResult(i,1);


        }
    }, 1500);


}



    @Override
    protected void onResume() {
        super.onResume();
           }

    void parseJson(String out) {


		try {
			JSONObject resJson = new JSONObject(out);

              String rescode=	resJson.getString("response_code");
		      int code =Integer.parseInt(rescode);
			if(code==0)
			{
				String result=	resJson.getString("results");
				JSONArray resArray = new JSONArray(result);

				for (int i=0; i<resArray.length();i++)
				{
					JSONObject qjson = resArray.getJSONObject(i);
			 		String q = qjson.getString("question");
					String cans = qjson.getString("correct_answer");
					String ians = qjson.getString("incorrect_answers");
					JSONArray ans = new JSONArray(ians);
					String ians1 = ans.getString(0);
					String ians2;
					String ians3;
					if(cans.equalsIgnoreCase("True") || cans.equals("False"))

					{
						ians2 = "";
						ians3="";
					}

					else
					{
					ians2 = ans.getString(1);
					ians3 = ans.getString(2);
					}

					Random rand = new Random();

					int  n = rand.nextInt(4) + 1;

					Question que=null;

					switch(n)
					{
					case 1 : que = new Question(q,ians1,ians2,ians3,cans);
					break;
					case 2 :  que = new Question(q,ians1,ians2,cans,ians3);
					break;
					case 3 :  que = new Question(q,ians1,cans,ians3,ians2);
					break;
					case 4 :  que = new Question(q,cans,ians2,ians3,ians1);
					break;

					}
					Question cq = new Question(q,cans);

					question.add(que);
					cquestion.add(cq);

				}

                setQuestionView();

			}

			else if(code==1)
			{

              //  Toast.makeText(this, "This category is Locked for 1 hour", Toast.LENGTH_LONG).show();

                //finish();
                ((Token)getApplication()).setToken(null);
                LoadQ();




				}
			else if(code==3)
			{


                ((Token)getApplication()).setToken(null);
                        LoadQ();

				}
			else if(code==4)
			{

                ((Token)getApplication()).setToken(null);
                LoadQ();
			}

			else {

				Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	
	private void setQuestionView()
	{



        currentQ=question.get(qid);
        cansQ = cquestion.get(qid);
        txtQuestion.setText(Html.fromHtml(currentQ.getQUESTION()));
		opt1.setText(Html.fromHtml(currentQ.getOPTA()));
		opt2.setText(Html.fromHtml(currentQ.getOPTB()));
		opt3.setText(Html.fromHtml(currentQ.getOPTC()));
		opt4.setText(Html.fromHtml(currentQ.getANSWER()));




		if(opt1.getText().toString().equals("")) {
            opt1.setVisibility(View.GONE);
            qtype=false;
        }
		else {
            opt1.setVisibility(View.VISIBLE);
        }
		if(opt2.getText().toString().equals("")) {
            opt2.setVisibility(View.GONE);
            qtype=false;
        }
		else {
            opt2.setVisibility(View.VISIBLE);
        }
		if(opt3.getText().toString().equals("")) {
            qtype=false;
            opt3.setVisibility(View.GONE);
        }
		else {
            opt3.setVisibility(View.VISIBLE);

        }
		if(opt4.getText().toString().equals("")) {
            qtype=false;
            opt4.setVisibility(View.GONE);
        }
		else {
            opt4.setVisibility(View.VISIBLE);
        }


        opt1.setBackgroundResource(R.color.white);
        opt1.setTextColor(Color.BLACK);

        opt2.setBackgroundResource(R.color.white);
        opt2.setTextColor(Color.BLACK);

        opt3.setBackgroundResource(R.color.white);
        opt3.setTextColor(Color.BLACK);


        opt4.setBackgroundResource(R.color.white);
        opt4.setTextColor(Color.BLACK);



        opt1.setEnabled(true);
        opt2.setEnabled(true);
        opt3.setEnabled(true);
        opt4.setEnabled(true);

        qcount++;
        int Qnum = qcount;
        int totques = quesnum;
        quesnumber.setText(+Qnum+"/"+totques+"");
        qid++;

        startonClick();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Bundle bundle = getIntent().getExtras();
        cat = bundle.getInt("cat");

        LoadQ();


    }

    void LoadQ()
    {
        score=0;
        token = ((Token)getApplication()).getToken();

        if ((token!=null))
        url =  "token="+token+"&amount="+quesnum+"&category="+cat+"&difficulty="+slevel+"&type=multiple";
        else
            url ="amount="+quesnum+"&category="+cat;
        url = "https://opentdb.com/api.php?"+url;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json = new JSONObject(response);

                            if(response!=null)
                            {
                                parseJson(response);
                            }

                            else{
                               networkDialog();
                            }






                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(),"Loading... Please wait...",Toast.LENGTH_LONG).show();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Loading... Please wait...",Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void networkDialog() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        LoadQ();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this,R.style.AppThemedialog);
        builder.setMessage("Network error occured: Retry?").setPositiveButton("Yes",dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }


}
