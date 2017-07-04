package sh.mik.musixmatchtest.models.internal;

import java.io.Serializable;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class Card implements Serializable {

    private CardType type;

    private String name;

    private String picture;

    public Card(CardType type, String name, String picture) {
        this.type = type;
        this.name = name;
        this.picture = picture;
    }

    public CardType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public enum CardType {
        ARTIST, TRACK
    }
}
