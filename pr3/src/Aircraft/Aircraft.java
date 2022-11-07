package Aircraft;
Aircraft aircraft = new Aircraft("МС-21");
        aircraft.setEngine(new Engine("/nДвигатель: Турбовентиляторный двигатель 870 км/ч"));
        aircraft.setWing(new Wing("/nКрыло: из полимерных композитных материалов, размах 36 м"));
        aircraft.setChassis(new Chassis("/nШасси: обычные!"));
        aircraft.getInfo();

public class Aircraft {
    private Engine engine = null;
    private Chassis chassis = null;
    private Wing wing = null;

    public Aircraft(String name, Wing wing, Chassis chassis, Engine engine) {
        this.wing = wing;
        this.chassis = chassis;
        this.engine = engine;
    }

    public void setWing(Wing wing){
        this.wing = wing;
    }

    public void setChassis(Chassis chassis){
        this.chassis = chassis;
    }

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public String getInfo(){
        return this.getInfo() +
                this.engine.getInfo() +
                this.chassis.getInfo() +
                this.wing.getInfo();
    }
}

