package com.ucinbir.xplugin.neredesin.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.adapter.KimlerRecyclerViewAdapter;
import com.ucinbir.xplugin.neredesin.background.ArkadasSorgulaAsyncTask;
import com.ucinbir.xplugin.neredesin.dialog.ArkadasEkleDialogFragment;

public class KimlerFragment extends BaseFragment {

    private KimlerRecyclerViewAdapter.KimlerItemClickListener kimlerItemClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kimler, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        ekranKontrolleriniOlustur();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        kimlerItemClickListener = (KimlerRecyclerViewAdapter.KimlerItemClickListener) activity;
    }

    private void ekranKontrolleriniOlustur() {

        final FragmentActivity activity = getActivity();

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        ArkadasSorgulaAsyncTask task = new ArkadasSorgulaAsyncTask(activity, kimlerItemClickListener);
        task.execute();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_kimler, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.kimlerActionEkleMenuItem:
                DialogFragment dialogFragment = new ArkadasEkleDialogFragment();
                dialogFragment.show(getFragmentManager(), "ArkadasEkleDialogFragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
