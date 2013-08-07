package com.example.refreshviewpager;

import com.dolphin.content.pulltorefresh.PullToRefreshBase;
import com.dolphin.content.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.dolphin.content.pulltorefresh.PullToRefreshViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VerticalViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnRefreshListener<VerticalViewPager> {

    private PullToRefreshViewPager mPullToRefreshViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
        mPullToRefreshViewPager.setOnRefreshListener(this);

        VerticalViewPager vp = mPullToRefreshViewPager.getRefreshableView();
        vp.setAdapter(new SamplePagerAdapter());
    }

    @Override
    public void onRefresh(PullToRefreshBase<VerticalViewPager> refreshView) {
        new GetDataTask().execute();
    }

    static class SamplePagerAdapter extends PagerAdapter {

        private static int[] sDrawables = { R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
                R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper };

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(sDrawables[position]);

            // Now just add ImageView to ViewPager and return it
            container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mPullToRefreshViewPager.onRefreshComplete();
            super.onPostExecute(result);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Horzontal View Activty");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, PullToRefreshHorizontalViewPagerActivity.class));
                return true;

            default:
                break;
        }

        return true;
    }
}
