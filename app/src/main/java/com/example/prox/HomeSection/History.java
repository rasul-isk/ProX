package com.example.prox.HomeSection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.HistoryAdapter.AdapterHistory;
import com.example.prox.HistoryAdapter.HistoryItem;
import com.example.prox.HistoryAdapter.ListenerHistory;
import com.example.prox.R;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment  implements ListenerHistory {

    SharedPreferences sp;
    TextView back_to_home;
    LinearLayout heading_history;

    String url,username;

    private RecyclerView recyclerView;
    private List<HistoryItem> HistoryList;
    AdapterHistory adapterHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        back_to_home = view.findViewById(R.id.back_to_home_history);
        heading_history = view.findViewById(R.id.heading_history);
        username = sp.getString("username", "");
        url = sp.getString("ip","");


        if (!username.equals("")) {
            String[] field = new String[1];
            field[0] = "username";
            //Creating array for data
            String[] data = new String[1];
            data[0] = username;

            PutData getReviews = new PutData("http://" + url + "/SearchDisplay/searchHistory.php", "POST", field, data);

            if (getReviews.startPut()) {
                if (getReviews.onComplete()) {
                    HistoryList = new ArrayList<>();
                    recyclerView = view.findViewById(R.id.recyclerViewHistory);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

                    String[] result = getReviews.getResult().split("/");
                    if (!result[0].isEmpty()) {
                        int loop = 0;

                        heading_history.setVisibility(View.VISIBLE);

                        for (String row : result) {
                            if (loop < 10) {
                                String[] items = row.split(",");
                                //Toast.makeText(getActivity(), row, Toast.LENGTH_SHORT).show();
                                HistoryList.add(new HistoryItem(items[0], items[1]));
                                loop++;
                            } else {
                                break;
                            }
                            adapterHistory = new AdapterHistory(getActivity(), HistoryList, this);
                            recyclerView.setAdapter(adapterHistory);
                        }
                    } else {
                        heading_history.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home newFragment = new Home();
                ReplaceFragment(newFragment);
            }
        });

        return view;
    }

    @Override
    public void onItemClicked(HistoryItem historyItem) {

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("search",historyItem.getSearch());
        editor.commit();

        Search newFragment = new Search();
        ReplaceFragment(newFragment);
    }


    protected void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, null);
        fragmentTransaction.commit();
    }

}