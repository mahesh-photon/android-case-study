package com.target.dealbrowserpoc.dealbrowser;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.target.dealbrowserpoc.dealbrowser.events.DealClickedEvent;
import com.target.dealbrowserpoc.dealbrowser.events.DealReceivedEvent;
import com.target.dealbrowserpoc.dealbrowser.events.NoNetworkEvent;
import com.target.dealbrowserpoc.dealbrowser.fragments.DealDetailsFragment;
import com.target.dealbrowserpoc.dealbrowser.fragments.DealListFragment;
import com.target.dealbrowserpoc.dealbrowser.http.DealController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindBool;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    public static final String DEAL_LIST_FRAGMENT = "DealListFragment";
    @BindBool(R.bool.portrait_only)
    protected boolean portraitOnly;
    @BindBool(R.bool.tablet)
    protected boolean isTablet;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(portraitOnly) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        showLoadingDialog();
        new DealController(this).getDeals();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public boolean isTablet() {
        return isTablet;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(NoNetworkEvent event) {
        dismissLoadingDialog();
        // We can show No Network Screen, or previously loaded screen
        Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onEvent(DealClickedEvent event) {

        if (findViewById(R.id.deal_details_fragment) != null) {
            DealDetailsFragment newFragment = DealDetailsFragment.newInstance(event.getDealItem());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.deal_details_fragment, newFragment);
            transaction.addToBackStack(DEAL_LIST_FRAGMENT);
            transaction.commit();

        } else {
            DealDetailsFragment newFragment = DealDetailsFragment.newInstance(event.getDealItem());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(DEAL_LIST_FRAGMENT);
            transaction.commit();
        }
    }

    @Subscribe
    public void onEvent(DealReceivedEvent event){
        dismissLoadingDialog();
        if (event.isSuccessful()) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, DealListFragment.newInstance(event.getResponseObject()), "TAG")
                    .commit();
        } else {
            Toast.makeText(this, R.string.server_error, Toast.LENGTH_LONG).show();
        }
    }
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setTitle(getString(R.string.please_wait));
            loadingDialog.setMessage(getString(R.string.loading_deals));
        }
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
