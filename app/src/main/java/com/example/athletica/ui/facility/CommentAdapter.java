package com.example.athletica.ui.facility;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.athletica.R;
import com.example.athletica.data.facility.Comments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comments> {


    private Activity context;
    private List<Comments> commentsList;
    private float userRating;
    private String userName;


    public CommentAdapter(Activity context, List<Comments> commentsList) {
        super(context, R.layout.activity_comment_adapter, commentsList);
        this.context = context;
        this.commentsList = commentsList;
    }

    public void addUserRating(float userRating){
        this.userRating=userRating;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.activity_comment_adapter, null, true);

        TextView comment_text = (TextView) listView.findViewById(R.id.comment_text_view);
        RatingBar ratingBar=(RatingBar) listView.findViewById((R.id.ratingBar));
        TextView userName=(TextView)listView.findViewById(R.id.comment_user_name);

        ratingBar.setRating(this.userRating);
        userName.setText(this.userName);
        Comments com = commentsList.get(position);
        comment_text.setText(com.getCommentContent());

        return listView;

    }
}
