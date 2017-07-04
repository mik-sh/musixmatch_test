package sh.mik.musixmatchtest.models.res;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mik.sh on 01/07/2017.
 */

public class BaseResponse<T> {

    private
    @SerializedName("message")
    Message<T> message;

    public BaseResponse() {
        this.message = new Message<>();
    }

    public int getCode() {
        return message.getHeader().getCode();
    }

    public T getBody() {
        return message.getBody();
    }

    public void setResCode(int code) {
        message.setResCode(code);
    }

    public class Message<N> {

        private
        @SerializedName("header")
        Header header;

        private
        @SerializedName("body")
        N body;

        public Message() {
            this.header = new Header();
        }

        public Header getHeader() {
            return header;
        }

        public N getBody() {
            return body;
        }

        public void setResCode(int code) {
            header.setCode(code);
        }

        public class Header {

            private
            @SerializedName("status_code")
            int code;

            public Header() {
                this.code = 0;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }
    }
}
