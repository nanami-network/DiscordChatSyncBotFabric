package net.koutachan.discordchatsyncbotfabric.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    public static String translateGoogle(String message) {
        try {
            Request request = new Request.Builder()
                    .url("http://www.google.com/transliterate?langpair=ja-Hira|ja&text=" + URLEncoder.encode(translateCharacter(message), StandardCharsets.UTF_8))
                    .build();
            Response response = new OkHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                return "N/A (" + message + ")";
            }
            String text = new String(response.body().string().getBytes(), System.getProperty("os.name").toLowerCase().startsWith("windows") ? "windows-31j" : "UTF-8");
            StringBuilder newMessage = new StringBuilder();
            for (JsonElement jsonElements : new Gson().fromJson(text, JsonArray.class)) {
                newMessage.append(jsonElements.getAsJsonArray().get(1).getAsJsonArray().get(0).getAsString());
            }
            return newMessage.append(" (").append(message).append(")").toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "E/A (" + message + ")";
        }
    }

    public static String translateCharacter(String text){
        for (Character character : characters) {
            text = text.replaceAll(character.getKey(), character.getValue());
        }
        return text;
    }

    // ここから下はローマ字翻訳リスト
    public static List<Character> characters = new ArrayList<>() {{
        add(new Character("ttsu","っつ"));
        add(new Character("zzya","っじゃ"));
        add(new Character("zzyu","っじゅ"));
        add(new Character("zzye","っじぇ"));
        add(new Character("zzyo","っじょ"));
        add(new Character("ssya","っしゃ"));
        add(new Character("ssha","っしゃ"));
        add(new Character("sshi","っし"));
        add(new Character("ssyu","っしゅ"));
        add(new Character("sshu","っしゅ"));
        add(new Character("ssye","っしぇ"));
        add(new Character("sshe","っしぇ"));
        add(new Character("ssyo","っしょ"));
        add(new Character("ssho","っしょ"));
        add(new Character("ttsa", "っつぁ"));
        add(new Character("ttsi", "っつぃ"));
        add(new Character("ttse", "っつぇ"));
        add(new Character("ttso", "っつぉ"));
        add(new Character("ccha", "っちゃ"));
        add(new Character("cchi", "っち"));
        add(new Character("cchu", "っちゅ"));
        add(new Character("cche", "っちぇ"));
        add(new Character("ccho", "っちょ"));
        add(new Character("ttya", "っちゃ"));
        add(new Character("ttyi", "っちぃ"));
        add(new Character("ttyu", "っちゅ"));
        add(new Character("ttye", "っちぇ"));
        add(new Character("ttyo", "っちょ"));
        add(new Character("wyi", "ゐ"));
        add(new Character("wye", "ゑ"));
        add(new Character("tsa", "つぁ"));
        add(new Character("tsi", "つぃ"));
        add(new Character("tsu", "つ"));
        add(new Character("tse", "つぇ"));
        add(new Character("tso", "つぉ"));
        add(new Character("kka","っか"));
        add(new Character("kki","っき"));
        add(new Character("kku","っく"));
        add(new Character("kke","っけ"));
        add(new Character("kko","っこ"));
        add(new Character("ssa","っさ"));
        add(new Character("ssi","っし"));
        add(new Character("ssu","っす"));
        add(new Character("sse","っせ"));
        add(new Character("sso","っそ"));
        add(new Character("tta","った"));
        add(new Character("tti","っち"));
        add(new Character("ttu","っつ"));
        add(new Character("tte","って"));
        add(new Character("tto","っと"));
        add(new Character("hha","っは"));
        add(new Character("hhi","っひ"));
        add(new Character("hhu","っふ"));
        add(new Character("hhe","っへ"));
        add(new Character("hho","っほ"));
        add(new Character("mma","っま"));
        add(new Character("mmi","っみ"));
        add(new Character("mmu","っむ"));
        add(new Character("mme","っめ"));
        add(new Character("mmo","っも"));
        add(new Character("yya","っや"));
        add(new Character("yyu","っゆ"));
        add(new Character("yyo","っよ"));
        add(new Character("rra","っら"));
        add(new Character("rri","っり"));
        add(new Character("rru","っる"));
        add(new Character("rre","っれ"));
        add(new Character("rro","っろ"));
        add(new Character("wwa","っわ"));
        add(new Character("wwo","っを"));
        add(new Character("zza","っざ"));
        add(new Character("zzi","っじ"));
        add(new Character("zzu","っず"));
        add(new Character("zze","っぜ"));
        add(new Character("zzo","っぞ"));
        add(new Character("dda","っだ"));
        add(new Character("ddi","っぢ"));
        add(new Character("ddu","っづ"));
        add(new Character("dde","っで"));
        add(new Character("ddo","っど"));
        add(new Character("bba","っば"));
        add(new Character("bbi","っび"));
        add(new Character("bbu","っぶ"));
        add(new Character("bbe","っべ"));
        add(new Character("bbo","っぼ"));
        add(new Character("ppa","っぱ"));
        add(new Character("ppi","っぴ"));
        add(new Character("ppu","っぷ"));
        add(new Character("ppe","っぺ"));
        add(new Character("ppo","っぽ"));
        add(new Character("sha", "しゃ"));
        add(new Character("shi", "し"));
        add(new Character("shu", "しゅ"));
        add(new Character("she", "しぇ"));
        add(new Character("sho", "しょ"));
        add(new Character("sya", "しゃ"));
        add(new Character("syi", "しぃ"));
        add(new Character("syu", "しゅ"));
        add(new Character("sye", "しぇ"));
        add(new Character("syo", "しょ"));
        add(new Character("lla", "っぁ"));
        add(new Character("lli", "っぃ"));
        add(new Character("llu", "っぅ"));
        add(new Character("lle", "っぇ"));
        add(new Character("llo", "っぉ"));
        add(new Character("ltu", "っ"));
        add(new Character("bya", "びゃ"));
        add(new Character("byu", "びゅ"));
        add(new Character("byo", "びょ"));
        add(new Character("zya", "じゃ"));
        add(new Character("zyu", "じゅ"));
        add(new Character("zyo", "じょ"));
        add(new Character("ja", "じゃ"));
        add(new Character("kya", "きゃ"));
        add(new Character("kyi", "きぃ"));
        add(new Character("kyu", "きゅ"));
        add(new Character("kye", "きぇ"));
        add(new Character("kyo", "きょ"));
        add(new Character("tya", "ちゃ"));
        add(new Character("tyu", "ちゅ"));
        add(new Character("tyo", "ちょ"));
        add(new Character("cha", "ちゃ"));
        add(new Character("chu", "ちゅ"));
        add(new Character("cho", "ちょ"));
        add(new Character("hya", "ひゃ"));
        add(new Character("hyi", "ひぃ"));
        add(new Character("hyu", "ひゅ"));
        add(new Character("hye", "ひぇ"));
        add(new Character("hyo", "ひょ"));
        add(new Character("pya", "ぴゃ"));
        add(new Character("pyi", "ぴぃ"));
        add(new Character("pyu", "ぴゅ"));
        add(new Character("pye", "ぴぇ"));
        add(new Character("pyo", "ぴょ"));
        add(new Character("lya", "ゃ"));
        add(new Character("lyi", "ぃ"));
        add(new Character("lyu", "ゅ"));
        add(new Character("lye", "ぇ"));
        add(new Character("lyo", "ょ"));
        add(new Character("xtu", "っ"));
        add(new Character("nn", "ん"));
        add(new Character("ka", "か"));
        add(new Character("ki", "き"));
        add(new Character("ku", "く"));
        add(new Character("ke", "け"));
        add(new Character("ko", "こ"));
        add(new Character("sa", "さ"));
        add(new Character("si", "し"));
        add(new Character("su", "す"));
        add(new Character("se", "せ"));
        add(new Character("so", "そ"));
        add(new Character("ta", "た"));
        add(new Character("ti", "ち"));
        add(new Character("tu", "つ"));
        add(new Character("te", "て"));
        add(new Character("to", "と"));
        add(new Character("na", "な"));
        add(new Character("ni", "に"));
        add(new Character("nu", "ぬ"));
        add(new Character("ne", "ね"));
        add(new Character("no", "の"));
        add(new Character("ha", "は"));
        add(new Character("hi", "ひ"));
        add(new Character("hu", "ふ"));
        add(new Character("he", "へ"));
        add(new Character("ho", "ほ"));
        add(new Character("ma", "ま"));
        add(new Character("mi", "み"));
        add(new Character("mu", "む"));
        add(new Character("me", "め"));
        add(new Character("mo", "も"));
        add(new Character("ya", "や"));
        add(new Character("yu", "ゆ"));
        add(new Character("ye", "いぇ"));
        add(new Character("yo", "よ"));
        add(new Character("ra", "ら"));
        add(new Character("ri", "り"));
        add(new Character("ru", "る"));
        add(new Character("re", "れ"));
        add(new Character("ro", "ろ"));
        add(new Character("wa", "わ"));
        add(new Character("wi", "うぃ"));
        add(new Character("wu", "う"));
        add(new Character("we", "うぇ"));
        add(new Character("wo", "を"));
        add(new Character("la", "ぁ"));
        add(new Character("li", "ぃ"));
        add(new Character("lu", "ぅ"));
        add(new Character("le", "ぇ"));
        add(new Character("lo", "ぉ"));
        add(new Character("ga", "が"));
        add(new Character("gi", "ぎ"));
        add(new Character("gu", "ぐ"));
        add(new Character("ge", "げ"));
        add(new Character("go", "ご"));
        add(new Character("za", "ざ"));
        add(new Character("zi", "じ"));
        add(new Character("zu", "ず"));
        add(new Character("ze", "ぜ"));
        add(new Character("zo", "ぞ"));
        add(new Character("da", "だ"));
        add(new Character("di", "ぢ"));
        add(new Character("du", "づ"));
        add(new Character("de", "で"));
        add(new Character("do", "ど"));
        add(new Character("ba", "ば"));
        add(new Character("bi", "び"));
        add(new Character("bu", "ぶ"));
        add(new Character("be", "べ"));
        add(new Character("bo", "ぼ"));
        add(new Character("pa", "ぱ"));
        add(new Character("pi", "ぴ"));
        add(new Character("pu", "ぷ"));
        add(new Character("pe", "ぺ"));
        add(new Character("po", "ぽ"));
        add(new Character("ji","じ"));
        add(new Character("ju", "じゅ"));
        add(new Character("je", "じぇ"));
        add(new Character("jo", "じょ"));
        add(new Character("a", "あ"));
        add(new Character("i", "い"));
        add(new Character("u", "う"));
        add(new Character("e", "え"));
        add(new Character("o", "お"));
        add(new Character("n", "ん"));
        add(new Character("-", "ー"));
    }};

    public static class Character {
        private final String key;
        private final String value;

        public Character(String key, String value){
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}