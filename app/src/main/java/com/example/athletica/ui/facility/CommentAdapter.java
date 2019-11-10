package com.example.athletica.ui.facility;

import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.util.Log;
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
import com.example.athletica.data.facility.Facility;
import com.example.athletica.data.facility.Ratings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.ContentValues.TAG;

public class CommentAdapter extends ArrayAdapter<Comments> {


    private Activity context;
    private List<Comments> commentsList;
    private List<Ratings> ratingsList;
    private float userRating;

    public CommentAdapter(Activity context, List<Comments> commentsList,List<Ratings> ratingsList) {
        super(context, R.layout.activity_comment_adapter, commentsList);
        this.context = context;
        this.commentsList = commentsList;
        this.ratingsList=ratingsList;
    }

//    public void addUserRating(float userRating){
//        this.userRating=userRating;
//
//    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.activity_comment_adapter, null, true);
        //DatabaseReference Rating_DB_Reference=FirebaseDatabase.getInstance().getReference("facility_ratings").child(this.rating_userid).child("ratingContent");

        TextView comment_text = (TextView) listView.findViewById(R.id.comment_text_view);
        RatingBar ratingBar=(RatingBar) listView.findViewById((R.id.ratingBar));
        TextView userName=(TextView)listView.findViewById(R.id.comment_user_name);

        Comments com = commentsList.get(position);
        comment_text.setText(com.getCommentContent());
        for (Ratings r :this.ratingsList){
            if((r.getUserID()).equals(com.getUserID()))
                this.userRating=r.getRatingContent();
        }

//        if(ratingsList.get(position).getUserID()==com.getUserID())
//            userRating=ratingsList.get(position).getRatingContent();
        ratingBar.setRating(this.userRating);
        userName.setText(com.getUserName());

        return listView;

    }
}
