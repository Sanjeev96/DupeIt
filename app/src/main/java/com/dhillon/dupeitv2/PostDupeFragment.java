package com.dhillon.dupeitv2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SANJEEV96 on 24/10/2017.
 */

public class PostDupeFragment extends Fragment {

    View myVeiw;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myVeiw = inflater.inflate(R.layout.postdupe_page, container, false);
        return myVeiw;
    }

}
