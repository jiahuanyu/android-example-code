package com.github.jiahuanyu.example.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.github.jiahuanyu.example.R;

/**
 * Created by doom on 16/4/11.
 */
public class PreferenceApplyFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_apply);
    }
}
