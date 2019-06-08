package technologies.d.digital.quiznow.Catogery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import technologies.d.digital.quiznow.R;


public class ResultActivity extends Activity {

	String token;
	int category;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		final TextView cat=(TextView)findViewById(R.id.textcat);
		TextView que=(TextView)findViewById(R.id.textque);
		TextView res=(TextView)findViewById(R.id.textRes);
		final Button cont = (Button) findViewById(R.id.rcontinue);
		final 	Button cate =(Button) findViewById(R.id.rcategory);



		//get score
		Bundle b = getIntent().getExtras();
		int score= b.getInt("Score");
		category = b.getInt("Category");
		int quesnum = b.getInt("quesnum");
		token = b.getString("token");
		cat.setText("RESULT");
		que.setText("Question : "+quesnum);
		res.setText("Score : "+score);

		cate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


				final Intent intent = new Intent(ResultActivity.this, SplashActivity.class);
				startActivity(intent);



			}
		});

		cont.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				cont.setAlpha(1);

				Intent i = new Intent();


				i.putExtra("cat",category );
				//i.putExtra("token",token);



				setResult(1,i);

				finish();





			}
		});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);
	}
}