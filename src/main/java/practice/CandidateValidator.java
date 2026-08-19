package practice;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Candidate;

public class CandidateValidator implements Predicate<Candidate> {

    private static final Pattern PERIOD_PATTERN = Pattern.compile("(\\d{4})-(\\d{4})");

    @Override
    public boolean test(Candidate candidate) {
        if (candidate == null) {
            return false;
        }

        if (candidate.getAge() < 35) {
            return false;
        }

        if (!candidate.isAllowedToVote()) {
            return false;
        }

        if (!"Ukrainian".equals(candidate.getNationality())) {
            return false;
        }

        String periods = candidate.getPeriodsInUkr();
        if (periods == null || periods.isEmpty()) {
            return false;
        }

        int totalYears = 0;
        Matcher matcher = PERIOD_PATTERN.matcher(periods);
        while (matcher.find()) {
            try {
                int start = Integer.parseInt(matcher.group(1));
                int end = Integer.parseInt(matcher.group(2));
                if (end > start) {
                    totalYears += (end - start);
                }
            } catch (NumberFormatException e) {
                // Невірний фрагмент періоду — пропускаємо його
                continue;
            }
        }

        return totalYears >= 10;
    }
}
