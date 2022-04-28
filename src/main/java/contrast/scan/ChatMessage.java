package contrast.scan;

import java.io.Serializable;
import java.util.Objects;

public class ChatMessage implements Serializable {

    public String help;

    public String room;

    public String msgs;

//    public ChatMessage(String help, String room, String msgs) {
//        this.help = help;
//        this.room = room;
//        this.msgs = msgs;
//    }
//
//    public ChatMessage() {
//
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(help, that.help) && Objects.equals(room, that.room) && Objects.equals(msgs, that.msgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(help, room, msgs);
    }
}
