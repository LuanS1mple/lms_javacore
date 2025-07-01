import org.luans1mple.lmscore.controller.cli.MainCLI;

public class Main {
    private static final String mode="cli";
    public static void main(String[] args) {
        if(mode.equals("cli")){
            Runnable runnable = new MainCLI();
            runnable.run();
        }
    }
}
