package lsh.framgia.com.androidadvancedemo.contactdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.contactdemo.model.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bindData(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts != null ? mContacts.size() : 0;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewName;
        private TextView mTextViewMobile;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.text_view_name);
            mTextViewMobile = itemView.findViewById(R.id.text_view_mobile);
        }

        void bindData(Contact contact) {
            mTextViewName.setText(contact.getName());
            mTextViewMobile.setText(contact.getMobileNumber());
        }
    }
}
