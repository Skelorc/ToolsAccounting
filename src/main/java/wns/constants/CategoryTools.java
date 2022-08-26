package wns.constants;

public enum CategoryTools {
    CAMERAS("010","Cameras"),
    TAPES("020","Tapes (Digital Media)"),
    LENSES("030","Lenses"),
    FILTERS_LENSES("040","Filters & Lenses"),
    HEADS("050","Heads"),
    LEGS("060","Legs"),
    MATE_BOXES("070","Mate Boxes"),
    SHOULDER_SETS("090","Shoulder Sets"),
    MONITORS_ON_CAMERA("090","Monitors On-camera"),
    CAGES("100","Cages"),
    MONITORS_PRODUCTION("110","Monitors Production"),
    RECORDERS("120","Recorders"),
    COMPUTERS("130","Computers"),
    TRANSMISSION("140"," Transmission"),
    TRANSMISSION_1("141"," Accesories Transmission"),
    SKATER_DOLLIES("160","Skater Dollies"),
    STABILISERS_VESTS("170","Stabilisers and vests"),
    CARTS("180","Carts"),
    POWER("190","Power"),
    CASES("200","Cases"),
    CABLES("210","Cables"),
    GEAR("220","Gear"),
    GRIPS_STANDS("230","Grips and Stands"),
    ELECTRONIC_STABILIZATION_SYSTEMS("240","Electronic stabilization systems"),
    COPTER("250","Copter"),
    SERVICE_TRANSPORT("800","Service and Transport"),
    SUB_RENT("900","Sub Rent"),
    OTHER("999","Other");

    private final String code;
    private final String value;

    CategoryTools(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
