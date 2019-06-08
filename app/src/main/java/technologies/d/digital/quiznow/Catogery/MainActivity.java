package technologies.d.digital.quiznow.Catogery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import technologies.d.digital.quiznow.R;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CatModel> catModel;
    String token;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        catModel = new ArrayList<CatModel>();
        for(int i = 1; i <CatData.catnamearray.length ; i++){
            catModel.add(new CatModel(CatData.catnamearray[i]

                    )
            );
        }




        Bundle bundle = getIntent().getExtras();
       int quesnum = bundle.getInt("quesnum");
       int difflevel = bundle.getInt("difflevel");




        adapter = new CustomCatAdapter(catModel,quesnum,difflevel);
        recyclerView.setAdapter(adapter);




    }




}
