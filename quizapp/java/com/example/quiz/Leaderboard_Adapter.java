package com.example.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.databinding.RowsLeaderboardBinding;

import java.util.ArrayList;

public class Leaderboard_Adapter  extends RecyclerView.Adapter<Leaderboard_Adapter.LeaderboardViewHolder> {
   Context context;
   ArrayList<User> users;
    RowsLeaderboardBinding binding;

    public Leaderboard_Adapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rows_leaderboard, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
      User user=users.get(position);
     holder. binding.nameLeaderboard.setText(user.getName());
    holder. binding.coinsleader.setText(String.valueOf(user.getCoins()));
      holder.binding.indexLeaderbrd.setText(String.format("#%d",position+1));
    }

    @Override

    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        RowsLeaderboardBinding binding;

        public LeaderboardViewHolder(@NonNull View itemView) {

            super(itemView);
            binding=RowsLeaderboardBinding.bind(itemView);
        }
    }
}
