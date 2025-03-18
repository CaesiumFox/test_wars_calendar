package org.itmo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class MagicCalendarTest {
    @Test
    public void testScheduleOne_success() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleClosing_success() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "23:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleCrossing_success() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "23:59", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testSchedule24_fail() {
        MagicCalendar calendar = new MagicCalendar();
        assertFalse(calendar.scheduleMeeting("Alice", "24:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleOvertime_fail() {
        MagicCalendar calendar = new MagicCalendar();
        assertFalse(calendar.scheduleMeeting("Alice", "99:99", MagicCalendar.MeetingType.WORK));
    }

    @ParameterizedTest
    @CsvSource(value = { "11:00", "09:00", "11:01", "08:59" })
    public void testScheduleTwo_success(String time) {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", time, MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleDifferentTypes_success() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.PERSONAL));
    }

    @ParameterizedTest
    @CsvSource(value = { "10:00", "10:59", "09:01" })
    public void testScheduleTwo_fail(String time) {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertFalse(calendar.scheduleMeeting("Alice", time, MagicCalendar.MeetingType.WORK));
    }

    @ParameterizedTest
    @CsvSource(value = { "09:00", "10:00", "11:00" })
    public void testScheduleTwoPeople_success(String time) {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", time, MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleFour_success() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "14:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "16:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleFive_success() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "14:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "16:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "18:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleSix_fail() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "14:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "16:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "18:00", MagicCalendar.MeetingType.WORK));
        assertFalse(calendar.scheduleMeeting("Alice", "20:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testScheduleSixWithTwoPeople_success() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "12:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Alice", "14:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", "16:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", "18:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", "20:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testGetMeetingsEmpty() {
        MagicCalendar calendar = new MagicCalendar();
        assertEquals(0, calendar.getMeetings("Alice").size());
    }

    @Test
    public void testGetMeetingsOne() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        var list = calendar.getMeetings("Alice");
        assertEquals(1, list.size());
        assertEquals("10:00", list.getFirst());
    }

    @Test
    public void testGetMeetingsOther() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        var list = calendar.getMeetings("Bob");
        assertEquals(0, list.size());
    }

    @Test
    public void testCancelMeeting() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.cancelMeeting("Alice", "10:00"));
        assertFalse(calendar.cancelMeeting("Alice", "10:00"));
    }

    @Test
    public void testCancelMeetingPersonal() {
        MagicCalendar calendar = new MagicCalendar();
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertFalse(calendar.cancelMeeting("Alice", "10:00"));
    }
}
