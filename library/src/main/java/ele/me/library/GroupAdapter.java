package ele.me.library;

import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class GroupAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_GROUP_HEADER = 0;
    private static final int VIEW_TYPE_GROUP_FOOTER = VIEW_TYPE_GROUP_HEADER + 1;
    private static final int VIEW_TYPE_GROUP_CHILD = VIEW_TYPE_GROUP_FOOTER + 1;
    private static final int MAX_VIEW_TYPE_COUNT = VIEW_TYPE_GROUP_CHILD + 1;

    private SparseIntArray childPositionCache;
    private SparseIntArray groupPositionCache;
    private LayoutInflater layoutInflater;

    private int cachedGroupCount;

    public GroupAdapter() {
        cachedGroupCount = -1;
        childPositionCache = new SparseIntArray();
        groupPositionCache = new SparseIntArray();
    }

    public final int getItemViewType(int position) {
        if (isGroupHeaderView(position)) {
            return VIEW_TYPE_GROUP_HEADER;
        }
        if (isGroupFooterView(position)) {
            return VIEW_TYPE_GROUP_FOOTER;
        }
        return VIEW_TYPE_GROUP_CHILD;
    }

    public final int getViewTypeCount() {
        return MAX_VIEW_TYPE_COUNT;
    }

    public long getItemId(int position) {
        return position;
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return convertView(position, parent);
    }

    public View convertView(int position, ViewGroup parent) {
        int groupPosition = getGroupPosition(position);
        switch (getItemViewType(position)) {
            case VIEW_TYPE_GROUP_HEADER:

                return getGroupHeaderView(groupPosition, layoutInflater);
            case VIEW_TYPE_GROUP_FOOTER:

                return getGroupFooterView(groupPosition, layoutInflater);
            case VIEW_TYPE_GROUP_CHILD:

                return getGroupChildView(groupPosition, getChildPosition(position), layoutInflater);

            default:
                return null;
        }
    }

    public final int getGroupPosition(int position) {
        // first try to retrieve values from cache
        int cachedSection = groupPositionCache.get(position, -1);
        if (cachedSection >= 0) {
            return cachedSection;
        }
        int groupHeaderPosition = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            int childCount = getChildCount(i);
            int groupFooterPosition = groupHeaderPosition + childCount + 1;
            if (position >= groupHeaderPosition && position <= groupFooterPosition) {
                return i;
            }
            groupHeaderPosition = groupFooterPosition + 1;
        }
        return 0;
    }

    public final int getChildPosition(int position) {
        int cachedPosition = childPositionCache.get(position, -1);
        if (cachedPosition >= 0) {
            return cachedPosition;
        }
        int groupHeaderPosition = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            int childCount = getChildCount(i);
            //add footer and header
            int groupFooterPosition = groupHeaderPosition + childCount + 1;
            if (position >= groupHeaderPosition && position < groupFooterPosition) {
                int positionInGroup = position - groupHeaderPosition - 1;
                childPositionCache.put(position, positionInGroup);
                return positionInGroup;
            }
            groupHeaderPosition = groupFooterPosition + 1;
        }
        return 0;
    }

    public final boolean isGroupHeaderView(int position) {
        int groupHeaderPosition = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            if (position == groupHeaderPosition) {
                return true;
            } else if (position < groupHeaderPosition) {
                return false;
            }
            int childCount = getChildCount(i);
            int groupFooterPosition = groupHeaderPosition + childCount + 1;
            groupHeaderPosition = groupFooterPosition + 1;
        }
        return false;
    }

    public final boolean isGroupFooterView(int position) {
        int groupStartAtPosition = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            int childCount = getChildCount(i);
            int groupEndAtPosition = groupStartAtPosition + childCount + 1;
            if (position == groupEndAtPosition) {
                return true;
            } else if (position < groupEndAtPosition) {
                return false;
            }
            groupStartAtPosition = groupEndAtPosition + 1;
        }
        return false;
    }

    @Override
    public final int getCount() {
        if (cachedGroupCount >= 0) {
            return cachedGroupCount;
        }
        int count = 0;
        for (int i = 0; i < getGroupCount(); i++) {
            count += getChildCount(i);
            // for the header footer view
            count += 2;
        }
        cachedGroupCount = count;
        return count;
    }

    @Override
    public void notifyDataSetChanged() {
        reset();
        super.notifyDataSetChanged();
    }

    private void reset() {
        cachedGroupCount = -1;
        childPositionCache.clear();
        groupPositionCache.clear();
    }

    @Override
    public void notifyDataSetInvalidated() {
        reset();
        super.notifyDataSetInvalidated();
    }

    public abstract int getChildCount(int groupPosition);
    public abstract int getGroupCount();
    public abstract View getGroupHeaderView(int groupPosition, LayoutInflater layoutInflater);
    public abstract View getGroupFooterView(int groupPosition, LayoutInflater layoutInflater);
    public abstract View getGroupChildView(int groupPosition, int childPosition, LayoutInflater layoutInflater);
}
