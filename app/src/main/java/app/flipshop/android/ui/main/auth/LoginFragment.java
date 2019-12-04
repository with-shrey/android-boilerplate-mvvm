package app.flipshop.android.ui.main.auth;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import app.flipshop.android.AppExecutors;
import app.flipshop.android.R;
import app.flipshop.android.di.Injectable;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements Injectable {
    @Inject
    AppExecutors mAppExecutors;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mAppExecutors.mainThread().execute(() -> Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show());
    }
}
