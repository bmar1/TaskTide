package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public final class TaskDateUtils {
	private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
	private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
	private static final DateTimeFormatter LONG_FORMAT = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
	private static final DateTimeFormatter SHORT_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

	private TaskDateUtils() {
	}

	public static LocalDate parseDeadline(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		String trimmed = value.trim();
		DateTimeFormatter[] formats = { STORAGE_FORMAT, LONG_FORMAT, SHORT_FORMAT };
		for (DateTimeFormatter formatter : formats) {
			try {
				return LocalDate.parse(trimmed, formatter);
			} catch (DateTimeParseException ignored) {
			}
		}

		return null;
	}

	public static boolean isValidDeadline(String value) {
		return parseDeadline(value) != null;
	}

	public static String normalizeDeadline(String value) {
		LocalDate deadline = parseDeadline(value);
		return deadline == null ? value.trim() : STORAGE_FORMAT.format(deadline);
	}

	public static String displayDeadline(String value) {
		LocalDate deadline = parseDeadline(value);
		return deadline == null ? value : DISPLAY_FORMAT.format(deadline);
	}

	public static boolean isDueToday(String value) {
		LocalDate deadline = parseDeadline(value);
		return deadline != null && deadline.equals(LocalDate.now());
	}

	public static String relativeDeadline(String value) {
		LocalDate deadline = parseDeadline(value);
		if (deadline == null) {
			return "Date not recognized";
		}

		long days = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
		if (days == 0) {
			return "Due today";
		}
		if (days == 1) {
			return "Due tomorrow";
		}
		if (days > 1) {
			return "Due in " + days + " days";
		}
		if (days == -1) {
			return "1 day overdue";
		}
		return Math.abs(days) + " days overdue";
	}
}
