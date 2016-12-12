package com.example.gal.headache.BL.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gal.headache.Models.Contact;
import com.example.gal.headache.Models.ContactHeader;
import com.example.gal.headache.Models.Interfaces.IContactListObject;
import com.example.gal.headache.R;
import com.example.gal.headache.util.ApplicationUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ContactsAdapter extends ArrayAdapter<IContactListObject> {
    private LayoutInflater mInflater;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private ArrayList<IContactListObject> mData = new ArrayList<>();
    private Context context;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private static final int CONTACT_PICKER_RESULT = 1001;


    public ContactsAdapter(Context context, List<IContactListObject> contacts) {
        super(context, 0, contacts);
       this.context = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        divideContactsAndSections(contacts);
    }

    private void divideContactsAndSections(List<IContactListObject> contacts) {
        for (IContactListObject contactListObject : contacts) {
            if (contactListObject instanceof Contact) {
                addItem(contactListObject);
            } else if (contactListObject instanceof ContactHeader) {
                addSectionHeaderItem(contactListObject);
            }
        }
    }

    public void addItem(final IContactListObject item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final IContactListObject item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public IContactListObject getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();

            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.contact_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.tvName);
                    holder.whatsApp  = (ImageButton) convertView.findViewById(R.id.whatsApp);



                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.contact_section, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    holder.textCountContacts =(TextView) convertView.findViewById(R.id.txtCountContacts);

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        IContactListObject contact = mData.get(position);

        holder.textView.setText(mData.get(position).getTitle());

        if (holder.whatsApp != null) {
            holder.whatsApp.setTag(((Contact) contact).getPhone());
            holder.whatsApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWhatsappContact(v.getTag().toString());
                }
            });
        }

        if ( holder.textCountContacts != null) {
            holder.textCountContacts.setText(String.format("(%d)", ((ContactHeader)contact).getSectionNumContacts()));
        }

        return convertView;
    }

    void openWhatsappContact(String number) {
        if (!ApplicationUtils.applicationInstalled(context, "com.whatsapp")) {
            Toast.makeText(context, "Whatsapp isn't installed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!number.contains("+972")) {
            number = "+972" + number.substring(1,number.length());
        }

        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        context.startActivity(Intent.createChooser(i, ""));
    }

    public static class ViewHolder {
        public TextView textView;
        public ImageButton whatsApp;
        public TextView textCountContacts;
    }
}