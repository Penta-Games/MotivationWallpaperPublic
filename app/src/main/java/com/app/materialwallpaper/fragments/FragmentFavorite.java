package com.app.materialwallpaper.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.materialwallpaper.Config;
import com.app.materialwallpaper.R;
import com.app.materialwallpaper.activities.ActivityImageSlider;
import com.app.materialwallpaper.activities.MainActivity;
import com.app.materialwallpaper.adapters.AdapterWallpaper;
import com.app.materialwallpaper.models.Wallpaper;
import com.app.materialwallpaper.utilities.Constant;
import com.app.materialwallpaper.utilities.DBHelper;
import com.app.materialwallpaper.utilities.ItemOffsetDecoration;

import java.util.ArrayList;

public class FragmentFavorite extends Fragment {

    DBHelper dbHelper;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    AdapterWallpaper adapterImage;
    ArrayList<Wallpaper> itemPhotos;
    LinearLayout lyt_no_favorite;
    private MainActivity mainActivity;
    private Toolbar toolbar;

    public FragmentFavorite() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        dbHelper = new DBHelper(getActivity());

        toolbar = view.findViewById(R.id.toolbar);
        lyt_no_favorite = view.findViewById(R.id.lyt_no_favorite);

        gridLayoutManager = new GridLayoutManager(getActivity(), Config.NUM_OF_COLUMNS);

        itemPhotos = dbHelper.getAllFavorite(Constant.TABLE_FAVORITE);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.grid_space_wallpaper);
        recyclerView.addItemDecoration(itemDecoration);

        adapterImage = new AdapterWallpaper(getActivity(), itemPhotos);
        recyclerView.setAdapter(adapterImage);
        onItemClickListener();

        if (itemPhotos.size() == 0) {
            lyt_no_favorite.setVisibility(View.VISIBLE);
        } else {
            lyt_no_favorite.setVisibility(View.INVISIBLE);
        }

        setupToolbar();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            if (itemPhotos != null) {
                itemPhotos = dbHelper.getAllFavorite(Constant.TABLE_FAVORITE);
                adapterImage = new AdapterWallpaper(getActivity(), itemPhotos);
                recyclerView.setAdapter(adapterImage);
                onItemClickListener();
                if (itemPhotos.size() == 0) {
                    lyt_no_favorite.setVisibility(View.VISIBLE);
                } else {
                    lyt_no_favorite.setVisibility(View.INVISIBLE);
                }
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        if (itemPhotos != null && adapterImage.getItemCount() > 0) {
            itemPhotos = dbHelper.getAllFavorite(Constant.TABLE_FAVORITE);
            adapterImage = new AdapterWallpaper(getActivity(), itemPhotos);
            recyclerView.setAdapter(adapterImage);
            onItemClickListener();
            if (itemPhotos.size() == 0) {
                lyt_no_favorite.setVisibility(View.VISIBLE);
            } else {
                lyt_no_favorite.setVisibility(View.INVISIBLE);
            }
        }
        super.onResume();
    }

    public void onItemClickListener() {
        adapterImage.setOnItemClickListener(new AdapterWallpaper.OnItemClickListener() {
            @Override
            public void onItemClick(View v, Wallpaper obj, int position) {
                Intent intent = new Intent(getActivity(), ActivityImageSlider.class);
                intent.putExtra("POSITION_ID", position);
                Constant.arrayList.clear();
                Constant.arrayList.addAll(itemPhotos);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.drawer_favorite));
        mainActivity.setSupportActionBar(toolbar);
    }

}
