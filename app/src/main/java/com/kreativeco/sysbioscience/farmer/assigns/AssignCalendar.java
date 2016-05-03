package com.kreativeco.sysbioscience.farmer.assigns;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JuanC on 03/05/2016.
 */
public class AssignCalendar extends SectionActivity {

    String [] daysOfWeek = {"Lunes",
            "Martes",
            "Miércoles",
            "Jueves",
            "Viernes",
            "Sábado",
            "Domingo"
    };

    private CompactCalendarView compactCalendarView;
    private TextView textViewCalendar;
    private Button btnPreviousMonth, btnNextMonth;
    private Date date;
    private String strYear;
    private String strMonth;
    private String strDay;
    private boolean isCalendarEnable = true;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.assign_calendar);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);

        btnPreviousMonth    = (Button) findViewById(R.id.btn_previous_month);
        btnNextMonth        = (Button) findViewById(R.id.btn_next_month);
        textViewCalendar    = (TextView) findViewById(R.id.tv_current_date);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compact_calendar_view);

        compactCalendarView.drawSmallIndicatorForEvents(true);
        compactCalendarView.setDayColumnNames(daysOfWeek);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setShouldDrawDaysHeader(true);
        compactCalendarView.shouldScrollMonth(true);

        setListeners();
        setHeaderDate();
        setInitialDates();

    }


    private void setListeners(){

        //compactCalendarView Listener
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if(isCalendarEnable){
                    strDay      = (String) android.text.format.DateFormat.format("dd", dateClicked);
                    strMonth    = (String) android.text.format.DateFormat.format("MM", dateClicked);
                    strYear     = (String) android.text.format.DateFormat.format("yyyy", dateClicked);

                    updateTime();

                }

                //setDatesOnRecyclerView();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setHeaderDate();
            }
        });


        //Button listeners, move previous or next month
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showNextMonth();
            }
        });

        btnPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.showPreviousMonth();
            }
        });

    }

    private void setInitialDates() {

        date     = compactCalendarView.getFirstDayOfCurrentMonth();

        strDay      = (String) android.text.format.DateFormat.format("dd", date);
        strMonth    = (String) android.text.format.DateFormat.format("MM", date);
        strYear     = (String) android.text.format.DateFormat.format("yyyy", date);

    }

    public void updateTime() {

        Constants.setAssignCalendarDate(strYear + "-" + strMonth + "-"
                + strDay);

        finish();
        AssignCalendar.this.overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
    }

    private void setHeaderDate(){

        date     = compactCalendarView.getFirstDayOfCurrentMonth();
        String txtMonth = setStringMonth((String) android.text.format.DateFormat.format("MM", date));
        String txtYear = (String) android.text.format.DateFormat.format("yyyy", date);

        String currentMonth = txtMonth + " " + txtYear;

        textViewCalendar.setText(currentMonth);

    }

    private String setStringMonth(String stringMonth){

        switch (stringMonth){
            case "01":
                stringMonth = "Enero";
                break;
            case "02":
                stringMonth = "Febrero";
                break;
            case "03":
                stringMonth = "Marzo";
                break;
            case "04":
                stringMonth = "Abril";
                break;
            case "05":
                stringMonth = "Mayo";
                break;
            case "06":
                stringMonth = "Junio";
                break;
            case "07":
                stringMonth = "Julio";
                break;
            case "08":
                stringMonth = "Agosto";
                break;
            case "09":
                stringMonth = "Septiembre";
                break;
            case "10":
                stringMonth = "Octubre";
                break;
            case "11":
                stringMonth = "Noviembre";
                break;
            case "12":
                stringMonth = "Diciembre";
                break;
        }

        return stringMonth;
    }
}
