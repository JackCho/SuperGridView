package ele.me.lineargrid;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ele.me.library.SuperGridView;

public class GridViewDemo extends Activity {

    private List<String> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        SuperGridView gridView = new SuperGridView(this);
        gridView.setNumColumns(3);
        dataset = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataset.add("test" + i);
        }
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new SuperGridView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearLayout parent, View view, int position) {
                Toast.makeText(GridViewDemo.this, "onItemClick:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        Button button = new Button(this);
        button.setText("add one item");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataset.add("added item");
                adapter.notifyDataSetChanged();
            }
        });

        layout.addView(button);
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(gridView);
        layout.addView(scrollView);
        setContentView(layout);
    }

    private SuperGridView.Adapter adapter = new SuperGridView.Adapter() {

        @Override
        public View getView(int position) {
            View view = LayoutInflater.from(GridViewDemo.this).inflate(R.layout.text_line_item, null);
            ((TextView) view.findViewById(R.id.textview)).setText(dataset.get(position));
            return view;
        }

        @Override
        public int getCount() {
            return dataset.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    };

}
