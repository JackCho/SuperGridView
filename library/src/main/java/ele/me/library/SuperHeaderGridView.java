package ele.me.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class SuperHeaderGridView extends LinearLayout {

    private GroupAdapter adapter;
    /**
     * Observer for the data changes
     */
    private final DataSetObserver dataSetObserver = new AdapterDataSetObserver();
    private int columsNum = 1;
    private LayoutInflater layoutInflater;

    private OnItemClickListener listener;

    public SuperHeaderGridView(Context context) {
        super(context);
        setOrientation(VERTICAL);
        layoutInflater = LayoutInflater.from(getContext());
    }

    public SuperHeaderGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        layoutInflater = LayoutInflater.from(getContext());
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SuperGridView, 0, 0);
        columsNum = array.getResourceId(R.styleable.SuperGridView_num_columns, 1);
        array.recycle();
    }

    public void setNumColumns(int columsNum) {
        this.columsNum = columsNum;
    }

    public void setAdapter(GroupAdapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("Adapter may not be null");
        }
        if (this.adapter != null) {
            this.adapter.unregisterDataSetObserver(this.dataSetObserver);
        }
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(this.dataSetObserver);
        resetList();
        refreshList();
    }

    private void resetList() {
        this.removeAllViews();
        invalidate();
    }

    private void refreshList() {
        int groupCount = adapter.getGroupCount();
        for (int groupPosition = 0; groupPosition < groupCount; groupPosition++) {
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(VERTICAL);
            final int groupPos = groupPosition;
            View headerView = adapter.getGroupHeaderView(groupPosition, layoutInflater);
            if (headerView != null && listener != null) {
                headerView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onGroupHeaderItemClick(groupPos);
                    }
                });
            }

            SuperGridView gridView = new SuperGridView(getContext());
            gridView.setNumColumns(columsNum);

            SuperGridView.Adapter childAdapter = new SuperGridView.Adapter() {
                @Override
                public View getView(int position) {
                    return adapter.getGroupChildView(groupPos, position, layoutInflater);
                }

                @Override
                public int getCount() {
                    return adapter.getChildCount(groupPos);
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }
            };
            gridView.setAdapter(childAdapter);
            if (listener != null) {
                gridView.setOnItemClickListener(new SuperGridView.OnItemClickListener() {
                    @Override
                    public void onItemClick(LinearLayout parent, View view, int position) {
                        listener.onGroupChildItemClick(groupPos, position);
                    }
                });
            }


            View footerView = adapter.getGroupFooterView(groupPosition, layoutInflater);
            if (footerView != null && listener != null) {
                footerView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onGroupFooterItemClick(groupPos);
                    }
                });
            }

            if (headerView != null) {
                layout.addView(headerView);
            }
            layout.addView(gridView);
            if (footerView != null) {
                layout.addView(footerView);
            }

            addView(layout);
        }
    }

    public void setOnItemClickListener(SuperHeaderGridView.OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * observe data set changes, when the adapter notifyDataSetChanged method called, onChanged
     * method will be called and view will be refreshed.
     */
    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            resetList();
            refreshList();
        }
    }

    public interface OnItemClickListener {
        void onGroupHeaderItemClick(int groupPosition);

        void onGroupFooterItemClick(int groupPosition);

        void onGroupChildItemClick(int groupPosition, int childPosition);
    }

}
