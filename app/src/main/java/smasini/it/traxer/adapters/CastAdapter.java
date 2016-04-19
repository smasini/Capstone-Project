package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import smasini.it.traxer.R;
import smasini.it.traxer.viewmodels.CastViewModel;

/**
 * Created by Simone Masini on 03/04/2016.
 */
public class CastAdapter extends BaseAdapter<CastViewModel> {

    public CastAdapter(Context context, OnClickHandler clickHandler) {
        super(context, clickHandler, R.layout.cast_item);
    }

    @Override
    public void onBindCustomViewHolder(ViewHolder viewHolder, int position, CastViewModel viewModel) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.actorName.setText(viewModel.getName());
        myViewHolder.actorRole.setText(viewModel.getRole());
        Picasso.with(mContext)
                .load(viewModel.getImageUrl())
                .into(myViewHolder.actorImage);
        myViewHolder.actorImage.setContentDescription(mContext.getString(R.string.accessibility_actor_photo));
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }

    private class MyViewHolder extends ViewHolder{

        public final TextView actorName;
        public final TextView actorRole;
        public final ImageView actorImage;

        public MyViewHolder(View view) {
            super(view);
            actorRole = (TextView) view.findViewById(R.id.textview_actor_role);
            actorName = (TextView) view.findViewById(R.id.textview_actor_name);
            actorImage = (ImageView) view.findViewById(R.id.imageview_actor);
        }
    }
}
