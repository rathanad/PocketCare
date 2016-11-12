package com.palle.rathan.pocketcare.ui;


import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palle.rathan.pocketcare.R;
import com.palle.rathan.pocketcare.data.MyDatabase;
import com.palle.rathan.pocketcare.model.Transaction;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {


    private Spinner summarytype;
    private ListView listView_display;
    private Button buttonFind;

    MyDatabase myDatabase;

    //Declaration-Visible Componenet

    ArrayList<Transaction> arrayListofSummary = new ArrayList<>();

    //Declaration-Invisible Componenet
    ArrayAdapter<String> arrayAdapterSummary;

    private Adapter myadpter;

    String[] type = {"Select type", "Income", "Expense"};


    public SummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_summary, null);

        myDatabase = new MyDatabase(getActivity());

        //String pos = SummaryFragment.this.type[summarytype.getSelectedItemPosition()];

        buttonFind = (Button) v.findViewById(R.id.button_find);
        summarytype = (Spinner) v.findViewById(R.id.spinner_summary_type);
        listView_display = (ListView) v.findViewById(R.id.list_view_summary);

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String selectedType = type[summarytype.getSelectedItemPosition()];
                if(selectedType.equals(type[0])){
                    Toast.makeText(getActivity(), "Select income or exp", Toast.LENGTH_SHORT).show();
                } else {
                    arrayListofSummary.clear();
                    arrayListofSummary.addAll(myDatabase.getTransactionsForType(selectedType));
                    myadpter.notifyDataSetChanged();
                }
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, type);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sourceArrayList.addAll(database.createTransaction(name, amount,category));

        summarytype.setAdapter(arrayAdapter);

        //String type1 = SummaryFragment.this.type[summarytype.getSelectedItemPosition(arrayAdapter)];

        arrayAdapterSummary = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, type);

        // arrayAdapterSummary=new ArrayAdapter<String>(getActivity(),android.R.layout.support_simple_spinner_dropdown_item,arrayListofSummary);


        myadpter = new Adapter();


        listView_display.setAdapter(myadpter);

        summarytype.setAdapter(arrayAdapterSummary);
        arrayAdapterSummary.notifyDataSetChanged();

        //listView_display.setAdapter(arrayAdapterSummary);
       // arrayListofSummary.addAll(myDatabase.getTransactionsForType(pos));

        return v;
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayListofSummary.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayListofSummary.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {


            // Getting data from source
            Transaction transactions = arrayListofSummary.get(i);


            // Get the xml
            View v = getActivity().getLayoutInflater().inflate(R.layout.income_expense_row, null);

            TextView textViewName = (TextView) v.findViewById(R.id.row_name);
            TextView textViewDate = (TextView) v.findViewById(R.id.row_date);
            TextView textViewamount = (TextView) v.findViewById(R.id.row_ammount);
            TextView textViewCategory = (TextView) v.findViewById(R.id.row_category);

            // Binding data from source to views in row.xml
            textViewName.setText("" + transactions.getName());
            textViewDate.setText(transactions.getDate());
            textViewamount.setText("" + transactions.getAmount());
            textViewCategory.setText(transactions.getCategory());

            return v;

        }
    }


}
