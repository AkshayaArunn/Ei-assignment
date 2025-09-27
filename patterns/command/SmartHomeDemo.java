package main.java.org.example.patterns.command;

interface Command {
    void execute();
}

class Light {
    void on() { System.out.println(" Light is ON"); }
    void off() { System.out.println(" Light is OFF"); }
}

class Thermostat {
    void setTemperature(int t) { System.out.println(" Thermostat set to " + t + "°C"); }
}

class LightOnCommand implements Command {
    private Light light;
    LightOnCommand(Light l) { this.light = l; }
    public void execute() { light.on(); }
}

class LightOffCommand implements Command {
    private Light light;
    LightOffCommand(Light l) { this.light = l; }
    public void execute() { light.off(); }
}

class SetTempCommand implements Command {
    private Thermostat thermostat; int temp;
    SetTempCommand(Thermostat t, int temp) { this.thermostat = t; this.temp = temp; }
    public void execute() { thermostat.setTemperature(temp); }
}

class HomeRemote {
    private Command slot;
    void setCommand(Command c) { slot = c; }
    void pressButton() { slot.execute(); }
}

public class SmartHomeDemo {
    public static void main(String[] args) {
        Light light = new Light();
        Thermostat thermo = new Thermostat();
        HomeRemote remote = new HomeRemote();

        remote.setCommand(new LightOnCommand(light));
        remote.pressButton();

        remote.setCommand(new SetTempCommand(thermo, 24));
        remote.pressButton();
    }
}
