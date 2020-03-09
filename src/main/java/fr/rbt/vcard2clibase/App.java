package fr.rbt.vcard2clibase;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.List;

import ezvcard.VCard;
import ezvcard.io.text.VCardReader;
import ezvcard.property.Telephone;

/**
 * À utiliser en mode flux UNIX, comme par exemple :
 * cat contacts.vcf | java -DURL_START="http://192.168.1.19/listes.htm?tl=B&act=A&type=B" -DKEY_NAME=nom -DKEY_TEL=num -jar vcard2clibase.jar | xargs -L 1 curl
 */
public class App {
	public final static String URL_START;
	public final static String KEY_NAME;
	public final static String KEY_TEL;
	public final static String ENCODING;
	static {
		URL_START = System.getProperty("URL_START");
		KEY_NAME = System.getProperty("KEY_NAME");
		KEY_TEL = System.getProperty("KEY_TEL");
		ENCODING = System.getProperty("ENCODING", "ISO-8859-15");
	}
	public static void main(String[] args) throws Exception {
		vcard2clibase(System.in, System.out);
	}

	public static void vcard2clibase(InputStream in, PrintStream out) throws Exception {
		try (VCardReader vcardReader = new VCardReader(in)) {
			for (VCard vcard = vcardReader.readNext(); vcard != null; vcard = vcardReader.readNext()) {
				String fullName;
				try {
					fullName = vcard.getFormattedName().getValue();
				} catch (Exception e) {
					fullName = null;
				}
				try {
					List<Telephone> telephoneNumbers = vcard.getTelephoneNumbers();
					for (Telephone telephoneNumber : telephoneNumbers) {
						System.err.println("URL pour " + telephoneNumber.getText() + " (" + (fullName == null ? "sans nom" : fullName) + ")");
						String getRequestStr = URL_START + (fullName == null ? "" : "&" + KEY_NAME + "=" + URLEncoder.encode(fullName, ENCODING)) + "&" + KEY_TEL + "=" + URLEncoder.encode(
							telephoneNumber
							.getText()
							.replaceAll("\\s*", "")
							.replaceAll("\\(.*\\)", "")
							.replaceAll("\\+", "00")
						, ENCODING);
						out.println(getRequestStr);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
	}
}
