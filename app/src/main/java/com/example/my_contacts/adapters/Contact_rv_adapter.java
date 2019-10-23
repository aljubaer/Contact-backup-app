package com.example.my_contacts.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_contacts.MainActivity;
import com.example.my_contacts.R;
import com.example.my_contacts.models.ModelContact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Contact_rv_adapter extends RecyclerView.Adapter<Contact_rv_adapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;


    private List<ModelContact> contactList;

    public Contact_rv_adapter(Context context, List<ModelContact> contactList) {

        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.items_contacts,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        // Item Call
         viewHolder.button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intentCall = new Intent(Intent.ACTION_CALL);
                 String number1 = viewHolder.number.getText().toString();
                 Toast.makeText(context, "Enable call phone", Toast.LENGTH_LONG).show();

                 intentCall.setData(Uri.parse("tel:"+number1));

                 if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                         != PackageManager.PERMISSION_GRANTED){
                     Toast.makeText(context, "Enable call phone in the settings", Toast.LENGTH_LONG).show();
                     MainActivity mainActivity = new MainActivity();
                     mainActivity.askCallPermission();
                 }else {
                     context.startActivity(intentCall);
                 }
             }
         });
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull Contact_rv_adapter.ViewHolder holder, int position) {

        TextView name, number;
        name = holder.name;
        number = holder.number;

        name.setText(contactList.get(position).getName());
        number.setText(contactList.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, number;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.number);
            button = itemView.findViewById(R.id.contact_button);

        }
    }

}