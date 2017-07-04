package sh.mik.musixmatchtest.models.req;

/**
 * Created by mik.sh on 01/07/2017.
 */

public enum RequestField {

    API_KEY, COUNTRY, PAGE_SIZE;

    @Override
    public String toString() {
        switch (this) {
            case API_KEY:
                return "apikey";
            case COUNTRY:
                return "country";
            case PAGE_SIZE:
                return "page_size";
            default:
                throw new IllegalArgumentException();
        }
    }
}
