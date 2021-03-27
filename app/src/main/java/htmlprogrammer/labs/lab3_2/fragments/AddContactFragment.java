package htmlprogrammer.labs.lab3_2.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import htmlprogrammer.labs.lab3_2.Contact;
import htmlprogrammer.labs.lab3_2.DataViewModel;
import htmlprogrammer.labs.lab3_2.R;

import static android.app.Activity.RESULT_OK;


public class AddContactFragment extends Fragment {
    private ImageView imageView;
    private Uri uri;
    private DataViewModel model;
    private static final int IMAGE_CAPTURE_REQUEST_CODE = 7777;

    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            String fileName = "contact_" + System.currentTimeMillis() + ".png";

            File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
            FileOutputStream fileStream = null;

            try{
                fileStream = new FileOutputStream(outputFile);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileStream);

                uri = Uri.fromFile(outputFile);
                fileStream.flush();
                fileStream.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = ViewModelProviders.of(requireActivity()).get(DataViewModel.class);
        imageView = view.findViewById(R.id.image);

        EditText nameEdit = view.findViewById(R.id.nameEdit);
        EditText emailEdit = view.findViewById(R.id.emailEdit);
        EditText phoneEdit = view.findViewById(R.id.phoneEdit);

        Button addContactButton = view.findViewById(R.id.addBtn);
        addContactButton.setOnClickListener(v -> {
            if (uri == null) {
                Toast.makeText(requireContext(), "Contact image not set!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = nameEdit.getText().toString();
            String email = emailEdit.getText().toString();
            String phone = phoneEdit.getText().toString();

            Contact c = new Contact();
            c.setImgRes(uri);
            c.setEmail(email);
            c.setName(name);
            c.setPhone(phone);

            model.addContact(c);
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        Button takePhotoButton = view.findViewById(R.id.photoBtn);
        takePhotoButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(requireContext(), "Error while trying to open camera app", Toast.LENGTH_SHORT).show();
            }
        });

        Button cancelButton = view.findViewById(R.id.cancelBtn);
        cancelButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }
}
