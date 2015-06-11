package ele.me.lineargrid;

import java.util.List;

public class Category {

    private String text;
    private List<Item> items;
    private int maxVisibleNums;

    private boolean isShowAll;

    public Category() {
    }

    public Category(int maxVisibleNums, String text) {
        this.maxVisibleNums = maxVisibleNums;
        this.text = text;
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(boolean isShowAll) {
        this.isShowAll = isShowAll;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getMaxVisibleNums() {
        return maxVisibleNums;
    }

    public void setMaxVisibleNums(int maxVisibleNums) {
        this.maxVisibleNums = maxVisibleNums;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
