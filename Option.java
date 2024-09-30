import java.util.List;

public class Option {
    private String text;
    private String condition;
    private List<String> actions;
    private String nextRoomId;
    private int timeCost = 1;

    // Getters and setters
    public String getText() {
        return text;
    }

    public String getCondition() {
        return condition;
    }

    public List<String> getActions() {
        return actions;
    }

    public String getNextRoomId() {
        return nextRoomId;
    }

    public int getTimeCost() {
        return timeCost;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public void setNextRoomId(String nextRoomId) {
        this.nextRoomId = nextRoomId;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }
}
