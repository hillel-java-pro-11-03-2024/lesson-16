import service.TerminalService;

public class Main {

  private static final TerminalService terminal = new TerminalService();

  public static void main(String[] args) {
    terminal.start();
  }

}
