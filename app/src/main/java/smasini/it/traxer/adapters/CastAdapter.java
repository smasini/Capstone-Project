package smasini.it.traxer.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    public class MyViewHolder extends ViewHolder{

        @Bind(R.id.textview_actor_name)
        public TextView actorName;
        @Bind(R.id.textview_actor_role)
        public TextView actorRole;
        @Bind(R.id.imageview_actor)
        public ImageView actorImage;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
