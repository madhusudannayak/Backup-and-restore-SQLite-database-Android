package www.studyviewer.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Model> arrayList;

    public CustomAdapter(Context context,  ArrayList<Model> arrayList) {
        super();

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Model getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.custom_layout, null);


        View listItemView = convertView;
//        if(listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.custom_layout, parent, false);
//        }
        Model currentNumber = getItem(position);

        TextView nametext = (TextView) listItemView.findViewById(R.id.fullname);
        nametext.setText(currentNumber.getName());

        TextView phonetext = (TextView) listItemView.findViewById(R.id.phonenumber);
        phonetext.setText(currentNumber.getPhone());

        TextView emailtext = (TextView) listItemView.findViewById(R.id.email);
        emailtext.setText(currentNumber.getEmail());

        final ImageView delImageView = convertView.findViewById(R.id.delete);

        delImageView.setTag(position);

        final View finalConvertView = convertView;

        delImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = (int) v.getTag();
                Animation animSlideRight = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
                animSlideRight.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Fires when animation starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // ...
                        deleteItem(pos);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // ...
                    }
                });
                finalConvertView.startAnimation(animSlideRight);
            }
        });


        Model dataModel = arrayList.get(position);
        nametext.setText(dataModel.getName());
        phonetext.setText(dataModel.getPhone());
        emailtext.setText(dataModel.getEmail());



        return listItemView;
    }

    public void deleteItem(int position) {
        deleteItemFromDb(arrayList.get(position).getName(), arrayList.get(position).getPhone(), arrayList.get(position).getEmail());
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    //Delete item from database
    public void deleteItemFromDb(String name, String phone,String email) {
        dbmanager databaseHelper = new dbmanager(context);
        try {
            databaseHelper.deleteData(name, phone, email);
            toastMsg("Deleted Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            toastMsg("Something went wrong");
        }
    }
    public void toastMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
