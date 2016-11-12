package com.palle.rathan.pocketcare.ui;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palle.rathan.pocketcare.R;
import com.palle.rathan.pocketcare.Utils.Constants;
import com.palle.rathan.pocketcare.data.MyDatabase;
import com.palle.rathan.pocketcare.model.Source;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment /*implements AddCategoryFragment.NewCategoryListener */{

    public TransactionFragment() {
        // Required empty public constructor
    }

    private MyDatabase database;
    private Spinner spinnerIncomeExpense, spinnerCategory;
    private Button save, buttonAddCategory,buttonClear;
    private EditText name, amount;

    private Button buttonDate;
    private TextView viewDate;
    private int mYear, mDay, mMonth;

    ArrayList sourceArrayList = new ArrayList<>();
    ArrayList<String> categoryArrayList = new ArrayList<>();
    private MySourceAdapter sourceAdapter;
    ArrayAdapter<String> mCategoryAdapter;


    String[] type;
    String[] category;
    String dateDeatils;

    public void updateCategorySpinner(String lastAddedCategory, Context context){

        categoryArrayList.clear();

        database = new MyDatabase(context);

        category = context.getResources().getStringArray(R.array.category);
        Collections.addAll(categoryArrayList, category);
        categoryArrayList.addAll(database.getAllCategories());

        mCategoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, categoryArrayList);
        mCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCategoryAdapter.notifyDataSetChanged();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_transaction, container, false);


        type = getResources().getStringArray(R.array.type);
        category = getResources().getStringArray(R.array.category);
        sourceAdapter = new MySourceAdapter();

        Collections.addAll(categoryArrayList, category);

        database = new MyDatabase(getActivity());


        name = (EditText) view.findViewById(R.id.edit_text_name);
        amount = (EditText) view.findViewById(R.id.edit_text_amount);

        spinnerIncomeExpense = (Spinner) view.findViewById(R.id.spinner_income_expense);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinner_type);

        buttonAddCategory= (Button) view.findViewById(R.id.button_add_category);
        save= (Button) view.findViewById(R.id.button_save);
        buttonClear= (Button) view.findViewById(R.id.button_clear);

        buttonDate = (Button) view.findViewById(R.id.button_select_date);
        viewDate = (TextView) view.findViewById(R.id.text_view_date);

        ArrayAdapter<String> summaryType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, type);
        summaryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryArrayList.addAll(database.getAllCategories());

        mCategoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categoryArrayList);
        mCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sourceArrayList.addAll(database.createTransaction(name, amount,category));


        spinnerIncomeExpense.setAdapter(summaryType);
        spinnerCategory.setAdapter(mCategoryAdapter);


        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCategoryFragment addcategoryFragment = new AddCategoryFragment();
                addcategoryFragment.show(getFragmentManager(), Constants.ADD_CATEGORY_DIALOG_TAG);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });
        //region Date
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String strMonth = String.valueOf(month), strDay = String.valueOf(day), strYear = String.valueOf(year);

                        if (month < 10) {
                            strMonth = "0" + strMonth;
                        }

                        if (day < 10) {
                            strDay = "0" + strDay;
                        }

                        // Handle the data here
                         viewDate.setText(strDay + " - " + strMonth + " - " + strYear);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gName=name.getText().toString();
                String gAmount=amount.getText().toString();
                Double amount= Double.parseDouble(gAmount);
                String date= viewDate.getText().toString();

                String type = TransactionFragment.this.type[spinnerIncomeExpense.getSelectedItemPosition()];

                String category = TransactionFragment.this.category[spinnerCategory.getSelectedItemPosition()];

                if(gName.equalsIgnoreCase("")|| date.equalsIgnoreCase("")||amount.equals("") || date.equalsIgnoreCase("")){

                    Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }

                long result = database.createTransaction(gName,amount,category,date,type);

                if (result != -1) {
                    //Snackbar.make(mEditTextMeaning, word + " has been added to your dictionary. ", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Transaction Saved Successfully", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(getActivity(), "Transaction Not saved", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }



    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_sumtrans,menu);
       // inflater.inflate(R.menu.menu_sumtrans,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_clear_data:
                clearData();
                break;
            case R.id.action_Logout:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_quit:
                getExitTransition();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/
    private void clearData() {
        name.setText("");
        amount.setText("");
        viewDate.setText("");
        spinnerIncomeExpense.setSelection(0);
        spinnerCategory.setSelection(0);
    }

   /* @Override
    public boolean categoryAddedToDatabase(String categoryName) {

        String category1 = TransactionFragment.this.category[spinnerCategory.getSelectedItemPosition()];

        long result = database.insertSource(categoryName);
        if (result > -1) {
           // Snackbar.make(mEditTextMeaning, sourceName + " added to your list of sources.", Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "added to your list of category", Toast.LENGTH_SHORT).show();
            sourceArrayList.clear();
            sourceArrayList.addAll(database.getTransactionsForType(category1));
            sourceAdapter.notifyDataSetChanged();
            spinnerCategory.setSelection((int) result - 1);
            return true;
        } else {
            //Snackbar.make(mEditTextMeaning, "Error adding " + sourceName + " to database. ", Snackbar.LENGTH_LONG).show();
            return false;
        }


    }*/

    @Override
    public void onResume() {
        super.onResume();
        updateCategorySpinner("",getActivity());
    }

    private class MySourceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sourceArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return sourceArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Source source = (Source) sourceArrayList.get(position);

            // Pending - View Holder Pattern
            View view = getLayoutInflater(null).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);

            textView.setText(source.getName());

            return view;
        }
    }
    }
