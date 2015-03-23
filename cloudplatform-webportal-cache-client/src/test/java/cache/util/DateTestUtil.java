package cache.util;

import java.util.Calendar;
import java.util.Date;

public class DateTestUtil {

	/**
	 * get the time by offset to current time
	 * @param type (hour,day,month...)
	 * @param add (offset for current time)
	 * @return
	 */
	public static Date getIndexDayRefCurr(int type, int add){
		Calendar calendar = Calendar.getInstance();
		calendar.add(type, add);
		Date date = calendar.getTime();
		return date;
	}
}
