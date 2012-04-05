package jp.yuigahama.natumegu.activity.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class TranslationLoader extends AsyncTaskLoader<String> {

	private final String sourceText;

	private String result = null;

	public TranslationLoader(Context context, String sourceText) {
		super(context);
		this.sourceText = sourceText;
	}

	@Override
	public String loadInBackground() {
		// Locale locale = Locale.getDefault();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("http://api.microsofttranslator.com/V2/Http.svc/Translate");
		stringBuilder.append("?appid=9A55B57689C6A4E164AFDF6FEDDD3ACD4ED751F7");
		stringBuilder.append("&to=ja");
		stringBuilder.append("&text=");
		stringBuilder.append(URLEncoder.encode(sourceText));

		URL url = null;
		Object content = null;
		// SAXパーサーを生成
		SAXParser parser = null;
		try {
			url = new URL(stringBuilder.toString());
			content = url.getContent();
			SAXParserFactory spfactory = SAXParserFactory.newInstance();

			parser = spfactory.newSAXParser();
			parser.parse((InputStream) content, new DefaultHandler() {
				// charactersメソッドをオーバーライド
				public void characters(char[] ch, int offset, int length) {
					result = new String(ch, offset, length);
				}
			});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return result;

	}
}