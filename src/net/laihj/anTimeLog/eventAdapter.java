package net.laihj.anTimeLog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;


import net.laihj.anTimeLog.eventItem;

import java.util.List;

public class eventAdapter extends BaseAdapter {
    private final Context context;
    private final List<eventItem> events;

    public eventAdapter(Context context,List<eventItem> events) {
	this.context = context;
	this.events = events;
    }

    public int getCount() {
	return this.events.size();
    }

    public Object getItem(int Position) {
	return this.events.get(Position);
    }

    public long getItemId(int Position) {
	return Position;
    }

    public View getView(int Position, View convertView, ViewGroup parent) {
	eventItem event = this.events.get(Position);
	return new eventListView(this.context, event);
    }

    private class eventListView extends RelativeLayout {
        public eventItem myEvent;
	private TextView event;
	private TextView theTime;
	private TextView type;
	
	//	private Button endBtn;
	//	private Button EditBtn;
	private Context context;
	final static int CLICKITEM = 1984;

	public eventListView(Context context, eventItem event) {
	    super(context);
            this.myEvent = event;
	    this.context = context;
	    this.setFocusable(true);
	    this.setLongClickable(true);
	    RelativeLayout.LayoutParams rlEvent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
										  ViewGroup.LayoutParams.WRAP_CONTENT);
	    this.event = new TextView(context);
	    this.event.setId(1);
	    this.event.setText(myEvent.event);
	    this.event.setTextSize(19f);
	    this.event.setTextColor(Color.WHITE);
	    this.addView(this.event,rlEvent);

	    RelativeLayout.LayoutParams rlType = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
										 ViewGroup.LayoutParams.WRAP_CONTENT);
	  
	    rlType.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    
	    this.type = new TextView(context);
	    this.type.setPadding(5,5,5,5);
	    this.type.setId(2);
	    this.type.setText(event.type);
	    this.type.setTextSize(14f);
	    this.type.setTextColor(Color.WHITE);
	    this.addView(this.type,rlType);
            final RelativeLayout.LayoutParams rltheTime = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
										  ViewGroup.LayoutParams.WRAP_CONTENT);
	    rltheTime.addRule(RelativeLayout.BELOW,1);
	    this.theTime = new TextView(context);
	    this.theTime.setId(3);
	    this.theTime.setText(myEvent.getDuration());
	    this.theTime.setTextSize(14f);
	
	    this.theTime.setTextColor(Color.GRAY);
	    this.addView(this.theTime,rltheTime);
	    setOnClickListener(new OnClickListener(){
		public void onClick(View v) {
		    ((anTimeLog) eventListView.this.context).selectedEvent = eventListView.this.myEvent;
		    ((Activity) eventListView.this.context).showDialog(CLICKITEM);
		    }
		});
	}
    }
}