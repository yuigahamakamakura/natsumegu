package jp.yuigahama.natumegu.value;

import java.util.ArrayList;

import jp.yuigahama.natumegu.R;
import jp.yuigahama.natumegu.model.LanguageModel;

/**
 * 対応をわかりやすく管理するために変なことしてます。
 *
 */
public class Strings {
	public static final ArrayList<LanguageModel> LANGUAGE_LIST = new ArrayList<LanguageModel>();
	static {
		{
			LanguageModel lt = new LanguageModel();
			lt.en = "Thank you";
			lt.es = "Gracias";
			lt.et = "Aitäh";
			lt.fi = "Kiitos";
			lt.fr = "Merci";
			lt.ja = "ありがとう";
			lt.vi = "Cảm ơn bạn";
			lt.zh = "谢谢";


			LANGUAGE_LIST.add(lt);
		}
		{
			LanguageModel lt = new LanguageModel();

			lt.en = "I've lost my key";
			lt.es = "He perdido mi clave";
			lt.et = "Olen kaotanud Minu klahv";
			lt.fi = "Nawalâ ang aking avain";
			lt.fr = "J'ai perdu ma clé";
			lt.ja = "カギをなくしました";
			lt.vi = "Tôi đã mất chìa khóa của tôi";
			lt.zh = "我的钥匙丢了。";

			LANGUAGE_LIST.add(lt);

		}
		{
			LanguageModel lt = new LanguageModel();

			lt.en = "Yeah is want to come";
			lt.es = "Sí se quiere venir";
			lt.et = "Jah on taha tulla";
			lt.fi = "Joo on tarkoitus tulla";
			lt.fr = "Ouais est veulent venir";
			lt.ja = "うんこしたいです";
			lt.vi = "Yeah muốn quay";
			lt.zh = "是啊是要来";

			LANGUAGE_LIST.add(lt);

		}
		{
			LanguageModel lt = new LanguageModel();

			lt.en = "Please buy milk";
			lt.es = "Por favor comprar leche";
			lt.et = "Palun osta piima";
			lt.fi = "Ole hyvä ostaa maito";
			lt.fr = "Veuillez acheter du lait";
			lt.ja = "牛乳買ってきてください";
			lt.vi = "Xin vui lòng mua sữa";
			lt.zh = "请买牛奶";
			LANGUAGE_LIST.add(lt);
		}
		{
			LanguageModel lt = new LanguageModel();

			lt.en = "Lonely. Call me.";
			lt.es = "Solitaria. Llámame.";
			lt.et = "Lonely. Helista mulle.";
			lt.fi = "Yksinäinen. Soita minulle.";
			lt.fr = "Solitaire. Appelle-moi.";
			lt.ja = "寂しい。電話してください。";
			lt.vi = "Cô đơn. Gọi cho tôi.";
			lt.zh = "孤独。给我打电话。";

			LANGUAGE_LIST.add(lt);
		}
		{
			LanguageModel lt = new LanguageModel();

			lt.en = "I'm hungry";
			lt.es = "Estoy hambriento de hambre";
			lt.et = "Ma olen näljane näljane";
			lt.fi = "Akó ay gutóm nälkäinen";
			lt.fr = "Je suis affamés affamés";
			lt.ja = "お腹すいた";
			lt.vi = "Tôi đang đói đói";
			lt.zh = "我饿饿";


			LANGUAGE_LIST.add(lt);

		}

		{
			LanguageModel lt = new LanguageModel();

			lt.en = "Pot and want!";
			lt.es = "Bote y queremos!";
			lt.et = "Pot ning soovite!";
			lt.fi = "Potin ja haluat!";
			lt.fr = "Pot et veulent !";
			lt.ja = "鍋したいです！";
			lt.vi = "Nồi và muốn!";
			lt.zh = "锅和想要 ！";


			LANGUAGE_LIST.add(lt);

		}
	}

}
