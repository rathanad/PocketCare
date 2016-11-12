package com.palle.rathan.pocketcare.ui;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.palle.rathan.pocketcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
//public class AddCategoryFragment extends Fragment {

public class AddCategoryFragment extends AppCompatDialogFragment {

    LayoutInflater inflater;
    View view;
    NewCategoryListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            listener = (NewCategoryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NewCategoryListener");
        }
    }

    public interface NewCategoryListener {
        public boolean categoryAddedToDatabase(String categoryName);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.fragment_add_category, null);

        // Extract
        final EditText editTextCategory = (EditText) view.findViewById(R.id.edit_text_add_category);
        Button buttonAddCategory = (Button) view.findViewById(R.id.button_add);
        Button buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        final TextInputLayout textInputLayoutAddSource = (TextInputLayout) view.findViewById(R.id.input_layout_add_category);

        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = editTextCategory.getText().toString().trim();
                if (categoryName.length() < 1) {
                    textInputLayoutAddSource.setError(getString(R.string.error_message_empty_category));
                } else if (!listener.categoryAddedToDatabase(categoryName)) {
                    textInputLayoutAddSource.setError("Category exists");
                } else {
                    textInputLayoutAddSource.setErrorEnabled(false);
                    dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();
    }
}

