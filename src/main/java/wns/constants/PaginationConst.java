package wns.constants;

import java.util.Arrays;

public enum PaginationConst {

    PROJECT,
    PROJECT_CREATE,
    TOOLS,
    TOOLS_ADD,
    CLIENT,
    STATUS,
    ESTIMATE,
    CHANGE;

    @Override
    public String toString() {
        return this.name();
    }

    public static PaginationConst getPaginationConstByString(String filter)
    {
        return Arrays.stream(PaginationConst.values()).filter(x -> x.toString().equals(filter)).findAny().get();
    }
}
