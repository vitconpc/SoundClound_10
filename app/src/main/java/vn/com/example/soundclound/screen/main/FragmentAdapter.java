package vn.com.example.soundclound.screen.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.TabType;
import vn.com.example.soundclound.screen.offline.OfflineFragment;
import vn.com.example.soundclound.screen.online.OnlineFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public FragmentAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case TabType.OFFLINE:
                frag = new OfflineFragment();
                break;
            case TabType.ONLINE:
                frag = new OnlineFragment();
                break;
            default:
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return Constants.NUMBER_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = Constants.STRING_EMPTY;
        switch (position) {
            case TabType.OFFLINE:
                title = mContext.getResources().getString(R.string.string_offline);
                break;
            case TabType.ONLINE:
                title = mContext.getResources().getString(R.string.string_online);
                break;
            default:
                break;
        }
        return title;
    }
}
