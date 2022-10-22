package wns.constants;

import java.util.Arrays;

public enum PaginationConst {

    PROJECT,
    TOOLS,
    CLIENT,
    STATUS,
    ESTIMATE;

    @Override
    public String toString() {
        return this.name();
    }

    public static PaginationConst getPaginationConstByString(String filter)
    {
        return Arrays.stream(PaginationConst.values()).filter(x -> x.toString().equals(filter)).findAny().get();
    }
}
