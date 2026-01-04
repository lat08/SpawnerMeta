package mc.rellox.spawnermeta.text.content;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mc.rellox.spawnermeta.prices.Price;
import mc.rellox.spawnermeta.spawner.type.SpawnerType;
import mc.rellox.spawnermeta.text.content.Colorer.Colors;

public interface Content {

	// Statics

	static Content empty() {
		return EmptyContent.empty;
	}

	static Content empty(int e) {
		String m = " ".repeat(e);
		return wrap(v -> m);
	}

	static Content of(List<Content> list) {
		return wrap(v -> list.stream()
				.map(c -> c.text(v))
				.collect(Collectors.joining()));
	}

	static Content of(Content... cs) {
		return wrap(v -> Stream.of(cs)
				.map(c -> c.text(v))
				.collect(Collectors.joining()));
	}

	static Content of(Colorer colorer, Content content) {
		return new ContentText(colorer, content);
	}

	static Content of(String text) {
		return wrap(v -> text);
	}

	static Content of(Variable variable) {
		return new ContentVariable(variable.key);
	}

	static Content of(int rgb, Object o) {
		return of(Colorer.of(rgb), of(o.toString()));
	}

	static Content of(int rgb, String text) {
		return of(Colorer.of(rgb), of(text));
	}

	static Content of(int rgb, Format format, String text) {
		return of(Colorer.of(rgb, format), of(text));
	}

	static Content of(String text, int... rgb) {
		return of(Colorer.of(Colors.of(rgb)), of(text));
	}

	static Content of(String text, Format format, int... rgb) {
		return of(Colorer.of(Colors.of(rgb), format), of(text));
	}

	static Content of(int rgb0, String t0, int rgb1, String t1) {
		return Content.of(Content.of(rgb0, t0), Content.of(rgb1, t1));
	}

	static Content of(int rgb0, String t0, int rgb1, String t1, int rgb2, String t2) {
		return Content.of(Content.of(rgb0, t0), Content.of(rgb1, t1), Content.of(rgb2, t2));
	}

	private static Content wrap(Content content) {
		return new WrappedContent(content);
	}

	class EmptyContent implements Content {
		private static final EmptyContent empty = new EmptyContent();

		@Override
		public String text(Variables variables) {
			return "";
		}

		@Override
		public String toString() {
			return "";
		}
	}

	record WrappedContent(Content content) implements Content {
		@Override
		public String text(Variables variables) {
			return content.text(variables);
		}

		@Override
		public String toString() {
			return text();
		}
	}

	// Methods

	String text(Variables variables);

	default String text() {
		return text(Variables.empty);
	}

	default Content modified(Variables variables) {
		return wrap(v -> text(variables));
	}

	// Classes

	interface Variables {

		static final Variables empty = k -> k;

		static Variables with(String k, Object o) {
			return v -> {
				if (v.equals(k))
					return convert(o);
				if (v.equals(k + "_uppercase"))
					return convert(o).toUpperCase();
				return v;
			};
		}

		static Variables with(Object... vs) {
			if (vs.length == 2)
				return with(vs[0].toString(), vs[1]);
			return v -> {
				int i = 0, m = vs.length;
				do {
					String k = vs[i].toString();
					if (v.equals(k) == true)
						return convert(vs[i + 1]);
					if (v.equals(k + "_uppercase") == true)
						return convert(vs[i + 1]).toUpperCase();
				} while ((i += 2) < m);
				return v;
			};
		}

		String get(String key);

		private static String convert(Object o) {
			if (o instanceof Content c)
				return c.text();
			if (o instanceof SpawnerType s)
				return s.formated().text();
			if (o instanceof Price p)
				return p.text().text();
			return o.toString();
		}

	}

	record Variable(String key) {
		public static Variable of(String key) {
			return new Variable(key);
		}
	}

	record ContentText(Colorer colorer, Content content) implements Content {
		@Override
		public String text(Variables variables) {
			return colorer.color(content.text(variables));
		}

		@Override
		public String toString() {
			return text();
		}
	}

	record ContentVariable(String key) implements Content {
		@Override
		public String text(Variables variables) {
			return variables.get(key);
		}

		@Override
		public String toString() {
			return text();
		}
	}

}
