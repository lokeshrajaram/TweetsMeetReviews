package reviews.clean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RemoveTitles {

    public static List<String> blacklistTitles = new ArrayList<String>(
            Arrays.asList("the", "of", "a", "and", "in", "to", "man", "on",
                    "for", "my", "night", "love", "at", "i", "from", "big",
                    "with", "me", "you", "is", "girl", "last", "life", "lady",
                    "story", "woman", "little", "it", "mr", "one", "day",
                    "house", "men", "three", "black", "that", "city", "great",
                    "time", "two", "young", "dead", "street", "way", "all",
                    "blue", "murder", "no", "wild", "heart", "up", "white",
                    "world", "dark", "out", "red", "death", "king", "back",
                    "new", "who", "high", "home", "moon", "secret", "they",
                    "road", "war", "west", "dr", "this", "an", "bad", "star",
                    "american", "by", "kid", "sea", "go", "ii", "kiss",
                    "never", "river", "good", "lost", "over", "town", "boys",
                    "down", "hell", "when", "be", "boy", "girls", "gun",
                    "island", "long", "2", "come", "goes", "people", "son",
                    "wife", "adventures", "baby", "days", "first", "not",
                    "return", "seven", "dawn", "devil", "green", "her", "it's",
                    "movie", "meet", "again", "angels", "blood", "bride",
                    "earth", "heaven", "midnight", "private", "under", "your",
                    "broadway", "charlie", "country", "cry", "fire", "mrs",
                    "old", "summer", "sun", "sweet", "york", "angel", "are",
                    "don't", "get", "ghost", "live", "run", "song", "stranger",
                    "was", "affair", "dangerous", "doctor", "his", "johnny",
                    "only", "paradise", "party", "too", "true", "we",
                    "without", "hardcore", "johns", "always", "weekend",
                    "coach", "forever", "anima", "class", "superhero",
                    "shower", "cream", "light", "action", "the one", "haven",
                    "project", "attack", "japan", "mission", "stars",
                    "watching a movie", "friends", "marcus", "holly", "heater",
                    "crumb", "big time", "proof", "harold", "bella", "chill",
                    "sting", "mother", "unexpected", "hunter", "phone",
                    "beautiful", "hoosiers", "redhead", "lucky", "sugar",
                    "father", "grind", "ceremony", "water", "together",
                    "housewife", "happy", "destiny", "shame", "shark", "saint",
                    "wanted", "phone", "inside", "date night", "pizza",
                    "america", "the girl", "the couple", "dragon", "church",
                    "brother"));

    private RemoveTitles() {
        throw new AssertionError();
    }

}
