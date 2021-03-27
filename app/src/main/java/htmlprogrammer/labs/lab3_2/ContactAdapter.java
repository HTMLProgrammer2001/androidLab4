package htmlprogrammer.labs.lab3_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contacts;
    private LayoutInflater inflater;

    final static int EMPTY = 0;
    final static int LIST = 1;


    public ContactAdapter(Context ctx, List<Contact> contacts){
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.size() > 0 ? LIST : EMPTY;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View contactView;

        if(i == EMPTY)
            contactView = inflater.inflate(R.layout.no_contact, viewGroup, false);
        else
            contactView = inflater.inflate(R.layout.contact_layout, viewGroup, false);

        return new ContactViewHolder(contactView, inflater.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder viewHolder, int i) {
        if(contacts.size() == 0)
            return;

        Contact contact = contacts.get(i);

        viewHolder.nameView.setText(contact.getName());
        viewHolder.emailView.setText(contact.getEmail());
        viewHolder.phoneView.setText(contact.getPhone());
        viewHolder.imgView.setImageURI(contact.getImgRes());

        viewHolder.delete.setOnClickListener((View view) -> {
            contacts.remove(i);
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return Math.max(contacts.size(), 1);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView nameView;
        TextView emailView;
        TextView phoneView;
        TextView delete;

        ContactViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            imgView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            emailView = itemView.findViewById(R.id.email);
            phoneView = itemView.findViewById(R.id.phone);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
