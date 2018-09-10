package lsh.framgia.com.androidadvancedemo.contactdemo.fragment;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.contactdemo.adapter.ContactAdapter;
import lsh.framgia.com.androidadvancedemo.contactdemo.database.ContactDbHelper;
import lsh.framgia.com.androidadvancedemo.contactdemo.model.Contact;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private RecyclerView mRecyclerContact;
    private ContactDbHelper mDbHelper;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDbHelper = new ContactDbHelper(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Contact> contacts = getContacts();
        saveContactsToDb(contacts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        mRecyclerContact = view.findViewById(R.id.recycler_contact);
        mRecyclerContact.setHasFixedSize(true);
        mRecyclerContact.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerContact.setAdapter(new ContactAdapter(mDbHelper.readContacts()));
    }

    private List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor == null || cursor.getCount() <= 0) return null;
        while (cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));

            if (cursor.getInt(cursor.getColumnIndex(
                    ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                Cursor phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                if (phoneCursor == null) return null;
                phoneCursor.moveToNext();
                String phoneNo = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact.setId(id);
                contact.setName(name);
                contact.setMobileNumber(phoneNo);
                phoneCursor.close();
            }
        }
        cursor.close();
        return contacts;
    }

    private void saveContactsToDb(List<Contact> contacts) {
        mDbHelper.deleteContacts();
        for (Contact contact : contacts) {
            mDbHelper.insertContact(contact);
        }
    }
}
