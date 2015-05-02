package com.wecamchat.aviddev.viewadapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.wecamchat.aviddev.R;
import com.wecamchat.aviddev.model.bo.ExpandableFilter;
import com.wecamchat.aviddev.model.bo.FilterOptions;

public class FilterExpandableAdapter extends BaseExpandableListAdapter {

    private ExpandableFilter filterList;
    private Context context;
    private TextView headerTextView;
    private List<String> selectedfilterElements;
    private String prefName;
    private SharedPreferences prefs;

    // private ExpandableListView listView;

    public FilterExpandableAdapter(Context context, TextView headerTextView,
            ExpandableListView listView, ExpandableFilter expandableFilter,
            String prefName) {
        this.filterList = expandableFilter;
        this.context = context;
        this.headerTextView = headerTextView;
        this.prefName = prefName;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
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
        String headerText = prefs.getString(prefName, headerTitle);
        headerTextView.setText(headerText);

        String[] listValues = headerText.split(",");
        selectedfilterElements = new ArrayList<String>();

        if (!listValues[0].equals("All")) {
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
                        if (childPosition == 0) {
                            checkAllComponents(isChecked);
                        } else {
                            currentOption.setChecked(isChecked);
                            performCheckChangeLogic(isChecked);
                        }
                    }

                    private void checkAllComponents(boolean isChecked) {
                        headerTextView.setText(filterList.getParent());
                        prefs.edit()
                                .putString(prefName, filterList.getParent())
                                .commit();
                        selectedfilterElements.clear();
                        for (FilterOptions option : filterList.getChildren()) {
                            option.setChecked(isChecked);
                        }
                        notifyDataSetChanged();
                    }

                    private void performCheckChangeLogic(boolean isChecked) {
                        if (isChecked) {
                            // check if the last element of the list is also
                            // selected
                            if (selectedfilterElements.size() == filterList
                                    .getChildren().size() - 2) {
                                checkAllComponents(true);
                            } else {
                                // select only the current component
                                currentOption.setChecked(true);
                                selectedfilterElements.add(currentOption
                                        .getFilterOptionName());
                            }

                        } else {
                            // if all components are selected then unselect the
                            // first(All) field and add check rest of the items
                            if (filterList.getChildren().get(0).isChecked()) {
                                filterList.getChildren().get(0)
                                        .setChecked(false);
                                notifyDataSetChanged();
                                for (FilterOptions option : filterList
                                        .getChildren()) {
                                    selectedfilterElements.add(option
                                            .getFilterOptionName());
                                }
                                selectedfilterElements.remove(filterList
                                        .getParent());
                            }
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
                            prefs.edit()
                                    .putString(prefName, headerText.toString())
                                    .commit();
                        } else {
                            headerTextView.setText(filterList.getParent());
                            prefs.edit()
                                    .putString(prefName, filterList.getParent())
                                    .commit();
                        }
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
