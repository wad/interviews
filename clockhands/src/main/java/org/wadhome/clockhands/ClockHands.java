package org.wadhome.clockhands;

public class ClockHands {

    static final int DEGREES_PER_MINUTE = 6;
    public static final int DEGREES_PER_HOUR = 30;

    // specify hour from 1 to 12, minute from 0 to 59
    public static int getAngleInDegrees(int hour, int minute) {

        // The hour needs to use 0 instead of 12
        hour = hour == 12 ? 0 : hour;

        int minuteHandAngle = minute * DEGREES_PER_MINUTE;

        // Minutes divided by 60 results in a percentage of full circle. Multiply that by DEGREES_PER_HOUR.
        // So it's just dividing the minutes by two. A right-shift does the same thing, with no remainder.
        int additionalDegreesForHourHandAngle = minute >> 1;
        int hourHandAngle = (hour * DEGREES_PER_HOUR) + additionalDegreesForHourHandAngle;

        int angle = minuteHandAngle - hourHandAngle;

        // convert negative angle into positive
        angle += angle < 0 ? 360 : 0;

        // return the smaller of the two angles
        return angle > 180 ? 360 - angle : angle;
    }

}
