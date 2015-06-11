# SuperGridView
An custom component which implements GridView with LinearLayout, and supports header too

### **SuperGridView**


```java
 SuperGridView gridView = new SuperGridView(this);
 gridView.setNumColumns(3);
 SuperGridViewAdapter adapter = new SuperGridViewAdapter();
 gridView.setAdapter(adapter);
 gridView.setOnItemClickListener(new SuperGridView.OnItemClickListener() {
     @Override
     public void onItemClick(LinearLayout parent, View view, int position) {
     }
 });
```

### **SuperHeaderGridView**


```java
SuperHeaderGridView gridView = new SuperHeaderGridView(this);
gridView.setNumColumns(3);
gridView.setOnItemClickListener(new SuperHeaderGridView.OnItemClickListener() {
    @Override
    public void onGroupHeaderItemClick(int groupPosition) {

    }

    @Override
    public void onGroupFooterItemClick(int groupPosition) {

    }

    @Override
    public void onGroupChildItemClick(int groupPosition, int childPosition) {

    }
});
SuperHeaderGridViewAdapter adapter = new SuperHeaderGridViewAdapter();
gridView.setAdapter(adapter);

```