package pl.edu.agh.to2.models;

public class Call {
    private String callRaw;
    private CallType callType = CallType.COMMAND;

    public Call(String callRaw) {
        this.callRaw = callRaw;
    }

    public Call(String callRaw, CallType callType) {
        this.callRaw = callRaw;
        this.callType = callType;
    }

    public String getCallRaw() {
        return callRaw;
    }

    public CallType getCallType() {
        return callType;
    }

    public String toString() {
        return callRaw;
    }
}
