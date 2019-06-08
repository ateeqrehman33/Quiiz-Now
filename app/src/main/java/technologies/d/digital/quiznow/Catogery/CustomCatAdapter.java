package technologies.d.digital.quiznow.Catogery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import technologies.d.digital.quiznow.R;


/**
 * Created by root on 3/7/18.
 */

public class CustomCatAdapter extends RecyclerView.Adapter<CustomCatAdapter.MyViewHolder> {

    private ArrayList<CatModel> catModelsset;
    String url,token;
    int qn,dl;




    public CustomCatAdapter (ArrayList<CatModel> catModel,int qusno,int diflvl){
        this.catModelsset = catModel;
        this.qn =qusno;
        this.dl = diflvl;




    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);


        MyViewHolder myViewHolder= new MyViewHolder(view,parent.getContext());
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TextView catname = holder.catname;


        catname.setText(catModelsset.get(position).catname.toUpperCase());




        holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View view) {



                    Intent intent = new Intent(view.getContext(), QuizActivity.class);

                    int cat = position + 9;

                    intent.putExtra("cat", cat);
                    intent.putExtra("quesnum",qn);
                    intent.putExtra("difflevel",dl);
                    view.getContext().startActivity(intent);


                }



        });



    }



    @Override
    public int getItemCount() {
        return catModelsset.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catname;

        Context context;


        public MyViewHolder(View view, Context context) {

            super(view);
            this.catname = (TextView) view.findViewById(R.id.catname);
            this.context = context;





        }
    }


}
