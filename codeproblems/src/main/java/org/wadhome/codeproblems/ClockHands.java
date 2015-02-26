package org.wadhome.codeproblems;

// Given a time (hour and minute), determine the angle between the hour hand and minute hand on an analog clock.
public class ClockHands {

    static final int DEGREES_PER_MINUTE = 6;
    public static final int DEGREES_PER_HOUR = 30;

    // specify hour from 1 to 12, minute from 0 to 59
    public static int getAngleInDegrees(int hour, int minute) {

        int minuteHandAngle = minute * DEGREES_PER_MINUTE;

        // Need to add a little to the hour hand, to account for minutes:
        // Minutes divided by 60 results in a percentage of full circle.
        // Multiply that by DEGREES_PER_HOUR, results in just dividing the minutes by two.
        // A right-shift does the same thing, with no remainder.
        int hourHandAngle = (hour * DEGREES_PER_HOUR) + (minute >> 1);

        int angle = minuteHandAngle - hourHandAngle;

        // convert negative angle into positive
        angle += angle < 0 ? 360 : 0;

        // return the smaller of the two angles
        return angle > 180 ? 360 - angle : angle;
    }

}
