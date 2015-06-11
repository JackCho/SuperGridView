package ele.me.lineargrid;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ele.me.library.GroupAdapter;
import ele.me.library.SuperHeaderGridView;

/**
 * Created by caoyubin on 15/6/11.
 */
public class HeaderGridViewDemo extends Activity {

    private List<Category> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataset = fakeData();

        SuperHeaderGridView gridView = new SuperHeaderGridView(this);
        gridView.setNumColumns(3);
        gridView.setOnItemClickListener(new SuperHeaderGridView.OnItemClickListener() {
            @Override
            public void onGroupHeaderItemClick(int groupPosition) {
                Category category = dataset.get(groupPosition);
                category.setIsShowAll(!category.isShowAll());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onGroupFooterItemClick(int groupPosition) {
                Toast.makeText(HeaderGridViewDemo.this, "click groupPosition:" + groupPosition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGroupChildItemClick(int groupPosition, int childPosition) {
                Toast.makeText(HeaderGridViewDemo.this, "click groupPosition:" + groupPosition + " childPosition:" + childPosition, Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setAdapter(adapter);

        setContentView(gridView);
    }

    private GroupAdapter adapter = new GroupAdapter() {
        @Override
        public int getChildCount(int groupPosition) {
            int childCount;
            Category category = dataset.get(groupPosition);
            if (category.isShowAll()) {
                childCount = category.getItems().size();
            } else {
                childCount = category.getMaxVisibleNums();
            }
            return childCount;
        }

        @Override
        public int getGroupCount() {
            return dataset.size();
        }

        @Override
        public View getGroupHeaderView(int groupPosition, LayoutInflater layoutInflater) {
            View convertView = layoutInflater.inflate(R.layout.header_item, null);
            ((TextView) convertView.findViewById(R.id.category)).setText(dataset.get(groupPosition).getText());
            ImageView arrow = (ImageView) convertView.findViewById(R.id.arrow);
            arrow.setImageResource(dataset.get(groupPosition).isShowAll() ? R.drawable.grey_triangle_down_icon : R.drawable.grey_triangle_up_icon);
            return convertView;
        }

        @Override
        public View getGroupFooterView(int groupPosition, LayoutInflater layoutInflater) {
            return null;
        }

        @Override
        public View getGroupChildView(int groupPosition, int childPosition, LayoutInflater layoutInflater) {
            View convertView = layoutInflater.inflate(R.layout.text_line_item, null);
            ((TextView) convertView.findViewById(R.id.textview)).setText(dataset.get(groupPosition).getItems().get(childPosition).getText());
            return convertView;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }
    };

    private List<Category> fakeData() {
        List<Category> list = new ArrayList<>();

        Category category1 = new Category(6, "Header 0");
        List<Item> items1 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Item item = new Item("item " + i);
            items1.add(item);
        }
        category1.setItems(items1);

        Category category2 = new Category(3, "Header 1");
        List<Item> items2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Item item = new Item("item " + i);
            items2.add(item);
        }
        category2.setItems(items2);

        list.add(category1);
        list.add(category2);
        return list;
    }

}
