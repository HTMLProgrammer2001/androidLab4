package htmlprogrammer.labs.lab3_2.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import htmlprogrammer.labs.lab3_2.ContactAdapter;
import htmlprogrammer.labs.lab3_2.DataViewModel;
import htmlprogrammer.labs.lab3_2.R;

public class ContactsFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private ContactAdapter adapter;
    private DataViewModel model;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = ViewModelProviders.of(requireActivity()).get(DataViewModel.class);
        model.getData().observe(getViewLifecycleOwner(), contacts -> {
            adapter.notifyDataSetChanged();
        });

        mRecyclerView = view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ContactAdapter(requireContext(), model.getData().getValue());
        mRecyclerView.setAdapter(adapter);

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.container, new AddContactFragment(), null)
                    .addToBackStack("add_contact")
                    .commit();
        });
    }
}
