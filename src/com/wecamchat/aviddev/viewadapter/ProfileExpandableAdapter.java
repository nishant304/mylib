package com.wecamchat.aviddev.viewadapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.fragment.ProfileFragment.SelectionChangeListener;
import com.wecamchat.aviddev.model.bo.ExpandableFilter;
import com.wecamchat.aviddev.model.bo.FilterOptions;

public class ProfileExpandableAdapter extends BaseExpandableListAdapter {

    private ExpandableFilter filterList;
    private Context context;
    private TextView headerTextView;
    private List<String> selectedfilterElements;
    private SelectionChangeListener selectionChangeListener;

    // private ExpandableListView listView;

    public ProfileExpandableAdapter(Context context, TextView headerTextView,
            ExpandableListView listView, ExpandableFilter expandableFilter,
            SelectionChangeListener selectionChangeListener) {
        this.filterList = expandableFilter;
        this.context = context;
        this.headerTextView = headerTextView;
        this.selectionChangeListener = selectionChangeListener;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return filterList.getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return filterList.getParent();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return filterList.getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_filter_header,
                    null);
            // headerTextView = (TextView) convertView
            // .findViewById(R.id.adapter_filter_heading_textview);
        }

        if (headerTextView.getText().equals("")) {
            // headerTextView.setText(headerTitle);
            setHeaderText(headerTitle);
        }
        return convertView;
    }

    private void setHeaderText(String headerTitle) {
        headerTextView.setText(headerTitle);

        String[] listValues = headerTitle.split(",");
        selectedfilterElements = new ArrayList<String>();

        if (!listValues[0].equals(context
                .getString(R.string.text_not_specified))) {
            for (String string : listValues) {
                selectedfilterElements.add(string.trim());
            }
            for (FilterOptions option : filterList.getChildren()) {
                if (selectedfilterElements.contains(option
                        .getFilterOptionName())) {
                    option.setChecked(true);
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        FilterHolder holder;
        if (convertView == null) {
            holder = new FilterHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.adapter_filter_children, null);
            holder.fieldSelectorCheckBox = (CheckBox) convertView
                    .findViewById(R.id.adapter_filter_child_radio_button);
            holder.fieldDescTextView = (TextView) convertView
                    .findViewById(R.id.adapter_filter_child_textview);
            convertView.setTag(holder);
        } else {
            holder = (FilterHolder) convertView.getTag();
        }

        final FilterOptions currentOption = (FilterOptions) getChild(
                groupPosition, childPosition);

        holder.fieldSelectorCheckBox
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkbox = (CheckBox) v;
                        boolean isChecked = checkbox.isChecked();
                        currentOption.setChecked(isChecked);
                        performCheckChangeLogic(isChecked);
                    }

                    private void performCheckChangeLogic(boolean isChecked) {
                        if (isChecked) {
                            // select only the current component
                            currentOption.setChecked(true);
                            selectedfilterElements.add(currentOption
                                    .getFilterOptionName());

                        } else {
                            currentOption.setChecked(false);
                            selectedfilterElements.remove(currentOption
                                    .getFilterOptionName());
                        }
                        if (selectedfilterElements.size() > 0) {
                            StringBuffer headerText = new StringBuffer(
                                    selectedfilterElements.get(0));
                            for (int i = 1; i < selectedfilterElements.size(); i++) {
                                headerText.append(", "
                                        + selectedfilterElements.get(i));
                            }
                            headerTextView.setText(headerText);
                        } else {
                            headerTextView.setText(context
                                    .getString(R.string.text_not_specified));
                        }

                        selectionChangeListener

                                .onSelectionChanged(headerTextView.getText()
                                        .toString());
                    }
                });

        setData(holder, currentOption);
        return convertView;
    }

    private void setData(FilterHolder holder, FilterOptions child) {
        if (child.isChecked()) {
            holder.fieldSelectorCheckBox.setChecked(true);
        } else {
            holder.fieldSelectorCheckBox.setChecked(false);
        }
        holder.fieldDescTextView.setText(child.getFilterOptionName());
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class FilterHolder {
        CheckBox fieldSelectorCheckBox;
        TextView fieldDescTextView;
    }
}
