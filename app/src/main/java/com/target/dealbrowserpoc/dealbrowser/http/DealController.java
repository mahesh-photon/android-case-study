package com.target.dealbrowserpoc.dealbrowser.http;

import android.content.Context;

import com.target.dealbrowserpoc.dealbrowser.Utility;
import com.target.dealbrowserpoc.dealbrowser.events.DealReceivedEvent;
import com.target.dealbrowserpoc.dealbrowser.events.NoNetworkEvent;
import com.target.dealbrowserpoc.dealbrowser.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * Created by mcnaik on 9/24/17.
 */

public class DealController implements OnTaskCompleted {

    protected NetController controller;

    private WeakReference<Context> mContext = new WeakReference<>(null);

    public DealController(Context context) {
        setContext(context);
    }

    public void getDeals() {
        final Context context = getContext();

        if (context != null) {
             if (Utility.isNetworkAvailable(context))
            {
                controller = new NetController(context, this);
                controller.setReqType(NetController.DEAL_LIST_REQUEST);
                controller.setUrl(Utility.DEAL_URL);
                controller.execute();
            } else {
                 EventBus.getDefault().post(new NoNetworkEvent());
             }
        }
    }

    protected void setContext(final Context context) {
        this.mContext = new WeakReference<>(context);
    }

    protected Context getContext() {
        if (mContext == null) {
            throw new NullPointerException("setContext null");
        }

        return mContext.get();
    }

    @Override
    public void onTaskCompleted(Response response) {
            if (response.getRequestTypes() == NetController.DEAL_LIST_REQUEST) {
                EventBus.getDefault().post(new DealReceivedEvent(response));
            }
    }

}

