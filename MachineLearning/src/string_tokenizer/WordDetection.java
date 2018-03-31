package string_tokenizer;

import java.text.BreakIterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordDetection {

	public static void main(String[] args) {
		String input = "\"Let's get this vis-a-vis\", he said, \"this boy's marks are really that well?\"";
		WordDetection wordDetection = new WordDetection();
		// wordDetection.useTokenizer(input);
		// wordDetection.useBreakIterator(input);
		wordDetection.useRegEx(input);
	}

	private void useTokenizer(String input) {
		System.out.println("Tokenizer");
		StringTokenizer tokenizer = new StringTokenizer(input);
		String word = "";
		while (tokenizer.hasMoreTokens()) {
			word = tokenizer.nextToken();
			System.out.println(word);
		}
	}

	private void useBreakIterator(String input) {
		BreakIterator tokenizer = BreakIterator.getWordInstance();
		tokenizer.setText(input);
		int start = tokenizer.first();
		for (int end = tokenizer.next(); end != BreakIterator.DONE; start = end, end = tokenizer.next()) {
			System.out.println(input.substring(start, end));
		}
	}

	private void useRegEx(String input) {
		Pattern pattern = Pattern.compile("\\w[\\w-]+('\\w*)?");
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			System.out.println(input.substring(matcher.start(), matcher.end()));
		}
	}
}
