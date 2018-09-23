package krist.car;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder  extends RecyclerView.ViewHolder{


    public TextView mNisja, mMberritja,mOra,mData;
    View mView;



    public ViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        mNisja = mView.findViewById(R.id.trips_txt_nisja);
        mMberritja = mView.findViewById(R.id.trips_txt_mberritja);
         mOra = mView.findViewById(R.id.trips_txt_ora);
         mData = mView.findViewById(R.id.trips_txt_data);
    }

    //set details to recycler view row

    public void setDetails(Context ctx, String nisja, String mberritja, String ora, String data){
        //Views

        TextView mNisja = mView.findViewById(R.id.trips_txt_nisja);
        TextView mMberritja = mView.findViewById(R.id.trips_txt_mberritja);
        TextView mOra = mView.findViewById(R.id.trips_txt_ora);
        TextView mData = mView.findViewById(R.id.trips_txt_data);

        //set data to views

        mNisja.setText(nisja);
        mMberritja.setText(mberritja);
        mOra.setText(ora);
        mData.setText(data);

    }

}
