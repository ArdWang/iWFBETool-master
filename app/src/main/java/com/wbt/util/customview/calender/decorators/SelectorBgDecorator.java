package com.wbt.util.customview.calender.decorators;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.wbt.R;


/**
 *  自定义选中的日期的背景颜色
 */
public class SelectorBgDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public SelectorBgDecorator(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.material_calendar_decorator_selected_bg);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
