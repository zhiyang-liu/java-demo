package com.excise.demo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

/**
 * java 8日期相关类使用示例
 * Created by liuzhiyang on 2019/10/7 下午3:15
 */
public class LocalDateTimeTest {

    /**
     * 构造Instant
     */
    public static void instantTest() {
        // 获取当前时间戳
        Instant instant = Instant.now();
        System.out.println(instant);

        // 指定系统时间戳
        Instant instant1 = Instant.ofEpochMilli(System.currentTimeMillis());
        System.out.println(instant1);

        // 解析指定时间戳
        Instant instant2 = Instant.parse("2019-06-10T03:42:39Z");
        System.out.println(instant2);
    }

    /**
     * 构造LocalDateTime
     */
    public static void localDateTimeTest() {
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // 指定时间
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, 06, 10, 10, 30,30);
        System.out.println(localDateTime1);

        // 解析时间
        LocalDateTime localDateTime2 = LocalDateTime.parse("2019-06-10 11:55:04", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(localDateTime2);
    }

    /**
     * 构造ZonedDateTime
     */
    public static void zonedDateTimeTest() {
        // 获取当前时区当前时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);

        // 指定时间及时区
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(2019, 06, 10, 10, 30,30, 00,  ZoneId.of("UTC"));
        System.out.println(zonedDateTime1);

        // 解析指定时间
        ZonedDateTime zonedDateTime2 = ZonedDateTime.parse("2019-06-10T12:03:19.367+08:00[Asia/Shanghai]", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        System.out.println(zonedDateTime2);
    }

    /**
     * 时间对象转换
     */
    public static void transTest() {
        Instant instant = Instant.now();

        // 时间戳转LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(localDateTime);

        // 时间戳转时区时间
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);

        // LocalDateTime转时间戳，因为LocalDateTime不带时区信息，因此需要指定当前时区到UTC的offset
        Instant instant1 = localDateTime.toInstant(ZoneOffset.ofHours(8));  // ZoneOffset.of("+08:00"), ZoneOffset.UTC
        System.out.println(instant1);

        // 时区时间转时间戳，ZonedDateTime自带时区信息
        Instant instant2 = zonedDateTime.toInstant();
        System.out.println(instant2);

        // LocalDateTime转时区时间（为时间加上时区信息）
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Tokyo"));
        System.out.println(zonedDateTime1);

        // 时区时间转换为LocalDateTime，将时区时间的时区信息去除
        LocalDateTime localDateTime1 = zonedDateTime.toLocalDateTime();
        System.out.println(localDateTime1);
    }

    /**
     * LocalDateTime与Date转换
     */
    public static void dateAndLocalDateTimeTrans() {
        Date date = Date.from(Instant.now());
        System.out.println(date);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println(date);

        Instant instant = date.toInstant();
        System.out.println(instant);
    }

    /**
     * 时区转换
     */
    public static void zoneTransTest() {
        // 初始化北京时间
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);

        // 北京时间转换为UTC时间
        ZonedDateTime utcZonedDateTime = zonedDateTime.toInstant().atZone(ZoneId.of("UTC"));
        System.out.println(utcZonedDateTime);

        // 初始化东京时间
        ZonedDateTime tokyoDateTime = ZonedDateTime.of(2019, 06, 10, 11, 20, 20, 00, ZoneId.of("Asia/Tokyo"));
        System.out.println(tokyoDateTime);

        // 东京时间转换为芝加哥时间
        ZonedDateTime chicagoDateTime = tokyoDateTime.toInstant().atZone(ZoneId.of("America/Chicago"));
        System.out.println(chicagoDateTime);
    }

    /**
     * 时间调整
     */
    public static void temporalAdjusterTest() {
        LocalDateTime localDateTime = LocalDateTime.now();

        // 本年本月最后一天
        System.out.println(localDateTime.with(TemporalAdjusters.lastDayOfMonth()));

        // 本年本月第一天
        System.out.println(localDateTime.with(TemporalAdjusters.firstDayOfMonth()));

        // 本年下一月第一天
        System.out.println(localDateTime.with(TemporalAdjusters.firstDayOfNextMonth()));

        // 下一年第一天
        System.out.println(localDateTime.with(TemporalAdjusters.firstDayOfNextYear()));

        // 本年最后一天
        System.out.println(localDateTime.with(TemporalAdjusters.lastDayOfYear()));

        // 下一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)));

        // 本月第一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY)));

        // 本月最后一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));

        // 下一个周五，如果当前是周五则返回当前时间
        System.out.println(localDateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)));

        // 前一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)));

        // 前一个周五，如果当前是周五则返回当前时间
        System.out.println(localDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)));

        // 当前时间+100天，plusYeas/plusMonths/plusWeeks/plusHours/plusMinutes/plusSeconds形式相同，同于System.out.println(localDateTime.plus(100, ChronoUnit.DAYS));
        System.out.println(localDateTime.plusDays(100));

        // 当前时间-100天，minusYeas/minusMonths/minusWeeks/minusHours/minusMinutes/minusSeconds形式相同，同于System.out.println(localDateTime.minus(100, ChronoUnit.DAYS));
        System.out.println(localDateTime.minusDays(100));
    }

    /**
     * 日期计算
     */
    public static void dateComputeTest() {
        // 通过Period计算年龄
        LocalDate birthDay = LocalDate.of(1994, 02, 07);
        LocalDate localDate = LocalDate.now();
        Period period = Period.between(birthDay, localDate);
        System.out.printf("%d 岁 %d 月 %d 天 %n", period.getYears(), period.getMonths(), period.getDays());

        // 计算时间差值
        Instant instant = Instant.now();
        Instant instant1 = instant.plus(Duration.ofMinutes(2));
        Duration duration = Duration.between(instant, instant1);
        System.out.println(duration.getSeconds());
        System.out.println(duration.toMillis());

        // 按某一单位维度计算差值
        LocalDateTime localDateTime = LocalDateTime.of(2019, 05, 20, 10, 10, 10);
        LocalDateTime localDateTime1 = LocalDateTime.now();
        Long dayDiff = ChronoUnit.DAYS.between(birthDay, localDate);
        System.out.println(dayDiff);
        Long miniteDiff = ChronoUnit.MINUTES.between(localDateTime, localDateTime1);
        System.out.println(miniteDiff);
    }

    /**
     * 日期格式化打印
     */
    public static void dateFormatTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2019, 06,10, 10, 10, 10);
        System.out.println(localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")));

        LocalDateTime localDateTime1 = LocalDateTime.parse("20190610 00:00:00", DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
        System.out.println(localDateTime1);
    }

    /**
     * 是否闰年
     */
    public static void isLeapYearTest() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.isLeapYear());
    }

    /**
     * 前后判断
     */
    public static void beforeAfterTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, 05, 20, 10, 10, 10);
        System.out.println(localDateTime.isAfter(localDateTime1));
        System.out.println(localDateTime.isBefore(localDateTime1));
    }

    public static void main(String[] args) {
//        instantTest();

//        localDateTimeTest();

//        zonedDateTimeTest();

//        transTest();

        dateComputeTest();
    }

}
