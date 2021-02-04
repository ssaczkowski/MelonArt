package digitalhouse.com.a0319cpmoacn01arce_4.view.homePage;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import digitalhouse.com.a0319cpmoacn01arce_4.R;
import digitalhouse.com.a0319cpmoacn01arce_4.model.ScoreItem;

public class ScoreAdapter extends RecyclerView.Adapter{

    private List<ScoreItem> scoreItemList;
    private Context ctx;

    public ScoreAdapter(List<ScoreItem> scoreItemList) {
        this.scoreItemList = scoreItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflador = LayoutInflater.from(viewGroup.getContext());
        ctx = viewGroup.getContext();
        View view = inflador.inflate(R.layout.score_item, viewGroup,false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ScoreItem scoreItem = scoreItemList.get(i);
        ((ScoreViewHolder) viewHolder).bindScoreItem(scoreItem);
    }

    private class ScoreViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageFlag;
        private TextView ranking;
        private TextView score;
        private TextView userName;


        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageFlag = itemView.findViewById(R.id.flag_image);
            this.ranking = itemView.findViewById(R.id.ranking);
            this.score = itemView.findViewById(R.id.score);
            this.userName = itemView.findViewById(R.id.user_name);

        }

        public void bindScoreItem(ScoreItem scoreItem){

            Glide.with(this.itemView.getContext())
                    .load(getImage(scoreItem.getImageFlag())).into(imageFlag);

            this.ranking.setText((getAdapterPosition()+1)+"º");
            this.score.setText(scoreItem.getScore() + "");
            this.userName.setText(scoreItem.getUserName());

        }
    }


    public int getImage(String imageName) {

        int drawableResourceId = ctx.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());

        return drawableResourceId;
    }

    @Override
    public int getItemCount() {
        return this.scoreItemList.size();
    }
}
